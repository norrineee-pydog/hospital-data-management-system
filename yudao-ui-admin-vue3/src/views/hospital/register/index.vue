<template>
  <div class="register-container">
    <el-card>
      <template #header>
        <span>预约挂号</span>
      </template>

      <!-- 步骤条 -->
      <el-steps
        :active="activeStep"
        finish-status="success"
        align-center
        style="margin-bottom: 40px"
      >
        <el-step title="选择科室" />
        <el-step title="选择医生" />
        <el-step title="选择时段" />
        <el-step title="确认挂号" />
      </el-steps>

      <!-- ========== Step 1: 选择科室 ========== -->
      <div v-show="activeStep === 0" class="step-content">
        <h3 class="step-title">请选择就诊科室</h3>
        <el-row :gutter="20" v-loading="loadingDepartments">
          <el-col
            v-for="dept in departmentList"
            :key="dept.id"
            :span="6"
            :xs="12"
            :sm="8"
            :md="6"
            style="margin-bottom: 16px"
          >
            <el-card
              :class="['dept-card', { selected: selectedDept?.id === dept.id }]"
              shadow="hover"
              @click="selectDepartment(dept)"
            >
              <div class="dept-name">{{ dept.deptName }}</div>
              <div class="dept-info">{{ dept.manager ? '主任: ' + dept.manager : '' }}</div>
              <div class="dept-info">{{ dept.location || '' }}</div>
            </el-card>
          </el-col>
        </el-row>
        <div v-if="!loadingDepartments && departmentList.length === 0" class="empty-tip">
          暂无可用科室
        </div>
      </div>

      <!-- ========== Step 2: 选择医生 ========== -->
      <div v-show="activeStep === 1" class="step-content">
        <h3 class="step-title">
          <el-button type="text" @click="goBack(0)">&lt; 返回</el-button>
          选择医生 - {{ selectedDept?.deptName }}
        </h3>
        <el-row :gutter="20" v-loading="loadingDoctors">
          <el-col
            v-for="doctor in doctorList"
            :key="doctor.id"
            :span="8"
            :xs="24"
            :sm="12"
            :md="8"
            style="margin-bottom: 16px"
          >
            <el-card
              :class="['doctor-card', { selected: selectedDoctor?.id === doctor.id }]"
              shadow="hover"
              @click="selectDoctor(doctor)"
            >
              <div class="doctor-avatar">
                <el-avatar :size="60" icon="UserFilled" />
              </div>
              <div class="doctor-name">{{ doctor.name }}</div>
              <div class="doctor-info">职称: {{ doctor.title || '未知' }}</div>
              <div class="doctor-info"
                >{{ doctor.gender || '' }} {{ doctor.age ? doctor.age + '岁' : '' }}</div
              >
            </el-card>
          </el-col>
        </el-row>
        <div v-if="!loadingDoctors && doctorList.length === 0" class="empty-tip">
          该科室暂无医生
        </div>
      </div>

      <!-- ========== Step 3: 选择日期和时段 ========== -->
      <div v-show="activeStep === 2" class="step-content">
        <h3 class="step-title">
          <el-button type="text" @click="goBack(1)">&lt; 返回</el-button>
          选择就诊时段 - {{ selectedDoctor?.name }}
        </h3>

        <!-- 日期选择 -->
        <div class="date-section" v-loading="loadingDates">
          <h4>选择日期</h4>
          <el-radio-group v-model="selectedDate" @change="onDateChange">
            <el-radio-button v-for="d in scheduleDates" :key="d" :value="d">
              {{ formatDate(d) }}
            </el-radio-button>
          </el-radio-group>
        </div>

        <!-- 时段选择 -->
        <div v-if="selectedDate" class="schedule-section" v-loading="loadingSchedules">
          <h4>选择时段</h4>
          <el-row :gutter="16">
            <el-col
              v-for="sched in scheduleList"
              :key="sched.id"
              :span="8"
              :xs="12"
              :sm="8"
              :md="6"
              style="margin-bottom: 12px"
            >
              <el-card
                :class="['schedule-card', { selected: selectedSchedule?.id === sched.id }]"
                shadow="hover"
                @click="selectSchedule(sched)"
              >
                <div class="schedule-period">{{ sched.period }}</div>
                <div class="schedule-num">
                  剩余
                  <span :class="sched.availableNum <= 3 ? 'num-warning' : ''">{{
                    sched.availableNum
                  }}</span>
                  / {{ sched.totalNum }}
                </div>
              </el-card>
            </el-col>
          </el-row>
          <div v-if="!loadingSchedules && scheduleList.length === 0" class="empty-tip">
            该日期无可用号源
          </div>
        </div>
      </div>

      <!-- ========== Step 4: 确认挂号 ========== -->
      <div v-show="activeStep === 3" class="step-content">
        <h3 class="step-title">
          <el-button type="text" @click="goBack(2)">&lt; 返回</el-button>
          确认挂号信息
        </h3>

        <el-form
          ref="confirmFormRef"
          :model="confirmForm"
          :rules="formRules"
          label-width="120px"
          class="confirm-form"
        >
          <el-form-item label="就诊科室">
            <el-input :model-value="selectedDept?.deptName" disabled />
          </el-form-item>
          <el-form-item label="就诊医生">
            <el-input :model-value="selectedDoctor?.name" disabled />
          </el-form-item>
          <el-form-item label="医生职称">
            <el-input :model-value="selectedDoctor?.title || '未知'" disabled />
          </el-form-item>
          <el-form-item label="就诊日期">
            <el-input :model-value="formatDate(selectedDate)" disabled />
          </el-form-item>
          <el-form-item label="就诊时段">
            <el-input :model-value="selectedSchedule?.period" disabled />
          </el-form-item>
          <el-form-item label="患者ID" prop="patientId">
            <el-input v-model.number="confirmForm.patientId" placeholder="请输入患者ID" />
          </el-form-item>
          <el-form-item label="就诊原因">
            <el-input
              v-model="confirmForm.reason"
              type="textarea"
              :rows="2"
              placeholder="可选，描述就诊原因"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              @click="submitAppointment"
              :loading="submitting"
              style="width: 200px"
            >
              确认挂号
            </el-button>
          </el-form-item>
        </el-form>
      </div>

      <!-- 底部操作按钮 -->
      <div class="step-actions" v-if="activeStep < 3">
        <el-button v-if="activeStep > 0" @click="goBack(activeStep - 1)">上一步</el-button>
        <el-button v-if="activeStep < 2" type="primary" :disabled="!canNext" @click="nextStep"
          >下一步</el-button
        >
        <el-button
          v-if="activeStep === 2"
          type="primary"
          :disabled="!selectedSchedule"
          @click="nextStep"
          >下一步</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  createAppointment,
  getDepartmentListForAppointment,
  getDoctorListForAppointment,
  getScheduleListForAppointment,
  getScheduleDatesForAppointment
} from '@/api/hospital/appointment'

// 步骤状态
const activeStep = ref(0)

// 科室列表
const departmentList = ref([])
const loadingDepartments = ref(false)
const selectedDept = ref(null)

// 医生列表
const doctorList = ref([])
const loadingDoctors = ref(false)
const selectedDoctor = ref(null)

// 排班数据
const scheduleDates = ref([])
const loadingDates = ref(false)
const selectedDate = ref('')
const scheduleList = ref([])
const loadingSchedules = ref(false)
const selectedSchedule = ref(null)

// 确认表单
const confirmForm = reactive({
  patientId: undefined,
  reason: ''
})

// 表单引用和校验规则
const confirmFormRef = ref() // 用于绑定 el-form

const formRules = reactive({
  patientId: [
    { required: true, message: '请输入患者ID', trigger: 'blur' },
    { type: 'number', message: '患者ID必须为数字', trigger: 'change' }
  ]
})
// ===========================================

const submitting = ref(false)

// 是否可以进入下一步
const canNext = computed(() => {
  if (activeStep.value === 0) return selectedDept.value !== null
  if (activeStep.value === 1) return selectedDoctor.value !== null
  if (activeStep.value === 2) return selectedSchedule.value !== null
  return false
})

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  const month = String(d.getMonth() + 1).padStart(2, '0')
  const day = String(d.getDate()).padStart(2, '0')
  const weekdays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
  const weekday = weekdays[d.getDay()]
  return `${month}月${day}日 ${weekday}`
}

// 加载科室列表
const loadDepartments = async () => {
  loadingDepartments.value = true
  try {
    const res = await getDepartmentListForAppointment()
    departmentList.value = res || []
  } catch (e) {
    console.error('加载科室列表失败', e)
    ElMessage.error('加载科室列表失败')
  } finally {
    loadingDepartments.value = false
  }
}

// 选择科室
const selectDepartment = (dept) => {
  selectedDept.value = dept
  selectedDoctor.value = null
  selectedSchedule.value = null
  selectedDate.value = ''
  scheduleList.value = []
  nextStep()
}

// 加载医生列表
const loadDoctors = async () => {
  if (!selectedDept.value) return
  loadingDoctors.value = true
  try {
    const res = await getDoctorListForAppointment(selectedDept.value.id)
    doctorList.value = res || []
  } catch (e) {
    console.error('加载医生列表失败', e)
    ElMessage.error('加载医生列表失败')
  } finally {
    loadingDoctors.value = false
  }
}

// 选择医生
const selectDoctor = (doctor) => {
  selectedDoctor.value = doctor
  selectedSchedule.value = null
  selectedDate.value = ''
  scheduleList.value = []
  scheduleDates.value = []
  loadScheduleDates()
  nextStep()
}

// 加载排班日期
const loadScheduleDates = async () => {
  if (!selectedDoctor.value) return
  loadingDates.value = true
  try {
    const res = await getScheduleDatesForAppointment(selectedDoctor.value.id)
    scheduleDates.value = res || []
    if (scheduleDates.value.length > 0) {
      selectedDate.value = scheduleDates.value[0]
      loadSchedules()
    }
  } catch (e) {
    console.error('加载排班日期失败', e)
    ElMessage.error('加载排班日期失败')
  } finally {
    loadingDates.value = false
  }
}

// 日期切换时加载排班
const onDateChange = (date) => {
  selectedSchedule.value = null
  loadSchedules()
}

// 加载排班列表
const loadSchedules = async () => {
  if (!selectedDoctor.value || !selectedDate.value) return
  loadingSchedules.value = true
  try {
    const res = await getScheduleListForAppointment(selectedDoctor.value.id, selectedDate.value)
    scheduleList.value = res || []
  } catch (e) {
    console.error('加载排班列表失败', e)
    ElMessage.error('加载排班列表失败')
  } finally {
    loadingSchedules.value = false
  }
}

// 选择排班时段
const selectSchedule = (sched) => {
  selectedSchedule.value = sched
}

// 下一步
const nextStep = () => {
  if (activeStep.value === 0 && !selectedDept.value) return
  if (activeStep.value === 1 && !selectedDoctor.value) return
  if (activeStep.value === 2 && !selectedSchedule.value) return

  activeStep.value++

  // 加载对应步骤的数据
  if (activeStep.value === 1) {
    loadDoctors()
  }
}

// 返回上一步
const goBack = (step) => {
  activeStep.value = step
}

// 提交挂号
const submitAppointment = async () => {
  // 前置表单合法性校验拦截
  if (!confirmFormRef.value) return

  await confirmFormRef.value.validate(async (valid) => {
    if (!valid) {
      ElMessage.warning('请先正确填写必填项（患者ID需为纯数字）')
      return
    }

    submitting.value = true
    try {
      await createAppointment({
        patientId: confirmForm.patientId,
        doctorId: selectedDoctor.value.id,
        deptId: selectedDept.value.id,
        scheduleId: selectedSchedule.value.id,
        reason: confirmForm.reason
      })
      ElMessage.success('挂号成功！')

      // 重置表单
      resetAll()
    } catch (e) {
      console.error('挂号失败', e)
      // 精准捕获并弹出后端拦截器抛出的友好提示
      const errorMsg = e?.response?.data?.msg || e?.response?.data?.message || '挂号失败，请重试'
      ElMessage.error(errorMsg)
      // =========================================================
    } finally {
      submitting.value = false
    }
  })
}

// 重置所有数据
const resetAll = () => {
  activeStep.value = 0
  selectedDept.value = null
  selectedDoctor.value = null
  selectedDate.value = ''
  selectedSchedule.value = null
  scheduleDates.value = []
  scheduleList.value = []
  confirmForm.patientId = undefined
  confirmForm.reason = ''
}

// 初始化
onMounted(() => {
  loadDepartments()
})
</script>

<style scoped>
.register-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.step-content {
  min-height: 300px;
  padding: 10px 0;
}

.step-title {
  margin-bottom: 20px;
  font-size: 16px;
  color: #333;
}

.empty-tip {
  text-align: center;
  padding: 60px 0;
  color: #999;
  font-size: 14px;
}

/* 科室卡片 */
.dept-card {
  cursor: pointer;
  text-align: center;
  transition: all 0.2s;
  border: 2px solid transparent;
}

.dept-card:hover {
  transform: translateY(-2px);
}

.dept-card.selected {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.dept-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.dept-info {
  font-size: 12px;
  color: #999;
  line-height: 1.6;
}

/* 医生卡片 */
.doctor-card {
  cursor: pointer;
  text-align: center;
  transition: all 0.2s;
  border: 2px solid transparent;
}

.doctor-card:hover {
  transform: translateY(-2px);
}

.doctor-card.selected {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.doctor-avatar {
  margin-bottom: 10px;
}

.doctor-name {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 6px;
}

.doctor-info {
  font-size: 12px;
  color: #999;
  line-height: 1.6;
}

/* 日期选择 */
.date-section {
  margin-bottom: 24px;
}

.date-section h4,
.schedule-section h4 {
  font-size: 14px;
  color: #666;
  margin-bottom: 12px;
}

/* 时段卡片 */
.schedule-card {
  cursor: pointer;
  text-align: center;
  transition: all 0.2s;
  border: 2px solid transparent;
}

.schedule-card:hover {
  transform: translateY(-2px);
}

.schedule-card.selected {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.schedule-period {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 6px;
}

.schedule-num {
  font-size: 12px;
  color: #999;
}

.num-warning {
  color: #f56c6c;
  font-weight: 600;
}

/* 确认表单 */
.confirm-form {
  max-width: 500px;
  margin: 0 auto;
}

/* 底部按钮 */
.step-actions {
  margin-top: 30px;
  text-align: center;
  padding: 20px 0;
}
</style>
