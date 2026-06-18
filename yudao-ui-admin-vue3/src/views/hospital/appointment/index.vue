<template>
  <div class="appointment-container">
    <el-card class="appointment-card">
      <template #header>
        <span class="card-title">预约挂号</span>
      </template>

      <!-- 步骤条 -->
      <el-steps :active="activeStep" align-center>
        <el-step title="选择科室" />
        <el-step title="选择医生" />
        <el-step title="选择时段" />
        <el-step title="确认挂号" />
      </el-steps>

      <!-- 步骤1：选择科室 -->
      <div v-show="activeStep === 0" class="step-content">
        <div class="step-title">请选择科室</div>
        <div v-loading="loadingDepartments" class="card-grid">
          <div
            v-for="dept in departmentList"
            :key="dept.deptId"
            class="selector-card"
            :class="{ active: selectedDept?.deptId === dept.deptId }"
            @click="selectDepartment(dept)"
          >
            <div class="card-name">{{ dept.deptName }}</div>
            <div class="card-desc">{{ dept.location || '暂无位置信息' }}</div>
          </div>
          <div v-if="departmentList.length === 0 && !loadingDepartments" class="empty-tip">
            暂无科室数据
          </div>
        </div>
      </div>

      <!-- 步骤2：选择医生 -->
      <div v-show="activeStep === 1" class="step-content">
        <div class="step-title">
          请选择医生
          <span class="sub-title">（科室：{{ selectedDept?.deptName }}）</span>
        </div>
        <div v-loading="loadingDoctors" class="card-grid">
          <div
            v-for="doctor in doctorList"
            :key="doctor.doctorId"
            class="selector-card"
            :class="{ active: selectedDoctor?.doctorId === doctor.doctorId }"
            @click="selectDoctor(doctor)"
          >
            <div class="card-name">{{ doctor.name }}</div>
            <div class="card-desc"
              >{{ doctor.title }} | {{ doctor.gender }} | {{ doctor.age }}岁</div
            >
          </div>
          <div v-if="doctorList.length === 0 && !loadingDoctors" class="empty-tip">
            该科室暂无医生
          </div>
        </div>
        <div class="step-actions">
          <el-button @click="prevStep">上一步</el-button>
        </div>
      </div>

      <!-- 步骤3：选择时段 -->
      <div v-show="activeStep === 2" class="step-content">
        <div class="step-title">
          请选择就诊日期和时段
          <span class="sub-title">（医生：{{ selectedDoctor?.name }}）</span>
        </div>

        <!-- 日期选择 -->
        <div class="date-group">
          <span class="label">选择日期：</span>
          <el-radio-group v-model="selectedDate" @change="onDateChange">
            <el-radio-button v-for="date in scheduleDates" :key="date" :value="date">
              {{ date }}
            </el-radio-button>
          </el-radio-group>
          <div v-if="scheduleDates.length === 0 && !loadingDates" class="empty-tip-inline">
            暂无排班日期
          </div>
        </div>

        <!-- 时段选择 -->
        <div v-if="scheduleList.length > 0" class="time-group">
          <span class="label">选择时段：</span>
          <div class="card-grid-small">
            <div
              v-for="schedule in scheduleList"
              :key="schedule.scheduleId"
              class="time-card"
              :class="{
                active: selectedSchedule?.scheduleId === schedule.scheduleId,
                low: schedule.availableNum <= 3
              }"
              @click="selectSchedule(schedule)"
            >
              <div class="time">{{ schedule.timeSlot }}</div>
              <div class="remain">剩余 {{ schedule.availableNum }}/{{ schedule.totalNum }}</div>
            </div>
          </div>
        </div>
        <div v-else-if="selectedDate && !loadingSchedules" class="empty-tip">
          该日期暂无可用号源
        </div>

        <div class="step-actions">
          <el-button @click="prevStep">上一步</el-button>
          <el-button type="primary" :disabled="!selectedSchedule" @click="nextStep">
            下一步
          </el-button>
        </div>
      </div>

      <!-- 步骤4：确认挂号 -->
      <div v-show="activeStep === 3" class="step-content">
        <div class="step-title">确认挂号信息</div>

        <el-descriptions :column="1" border>
          <el-descriptions-item label="科室">{{ selectedDept?.deptName }}</el-descriptions-item>
          <el-descriptions-item label="医生"
            >{{ selectedDoctor?.name }}（{{ selectedDoctor?.title }}）</el-descriptions-item
          >
          <el-descriptions-item label="就诊日期">{{ selectedDate }}</el-descriptions-item>
          <el-descriptions-item label="就诊时段">{{
            selectedSchedule?.timeSlot
          }}</el-descriptions-item>
          <el-descriptions-item label="患者ID">
            <el-input
              v-model="patientId"
              placeholder="请输入患者ID"
              style="width: 200px"
              type="number"
            />
          </el-descriptions-item>
          <el-descriptions-item label="就诊原因">
            <el-input
              v-model="reason"
              placeholder="请输入就诊原因（可选）"
              maxlength="200"
              show-word-limit
            />
          </el-descriptions-item>
        </el-descriptions>

        <div class="step-actions">
          <el-button @click="prevStep">上一步</el-button>
          <el-button type="primary" :loading="submitting" @click="submitAppointment">
            确认挂号
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  getDepartmentListForAppointment,
  getDoctorListForAppointment,
  getScheduleDatesForAppointment,
  getScheduleListForAppointment,
  createAppointment
} from '@/api/hospital/appointment'

// 步骤
const activeStep = ref(0)

// 加载状态
const loadingDepartments = ref(false)
const loadingDoctors = ref(false)
const loadingDates = ref(false)
const loadingSchedules = ref(false)
const submitting = ref(false)

// 数据
const departmentList = ref<any[]>([])
const doctorList = ref<any[]>([])
const scheduleDates = ref<string[]>([])
const scheduleList = ref<any[]>([])

// 选中项
const selectedDept = ref<any>(null)
const selectedDoctor = ref<any>(null)
const selectedDate = ref('')
const selectedSchedule = ref<any>(null)

// 表单
const patientId = ref('')
const reason = ref('')

// 获取科室列表
const loadDepartments = async () => {
  loadingDepartments.value = true
  try {
    const res = await getDepartmentListForAppointment()
    departmentList.value = res.data || []
  } catch (error) {
    ElMessage.error('获取科室列表失败')
  } finally {
    loadingDepartments.value = false
  }
}

// 选择科室
const selectDepartment = (dept: any) => {
  selectedDept.value = dept
  selectedDoctor.value = null
  loadDoctors(dept.deptId)
  activeStep.value = 1
}

// 获取医生列表
const loadDoctors = async (deptId: number) => {
  loadingDoctors.value = true
  try {
    const res = await getDoctorListForAppointment(deptId)
    doctorList.value = res.data || []
  } catch (error) {
    ElMessage.error('获取医生列表失败')
  } finally {
    loadingDoctors.value = false
  }
}

// 选择医生
const selectDoctor = (doctor: any) => {
  selectedDoctor.value = doctor
  selectedDate.value = ''
  selectedSchedule.value = null
  scheduleDates.value = []
  scheduleList.value = []
  loadScheduleDates(doctor.doctorId)
  activeStep.value = 2
}

// 获取排班日期
const loadScheduleDates = async (doctorId: number) => {
  loadingDates.value = true
  try {
    const res = await getScheduleDatesForAppointment(doctorId)
    scheduleDates.value = res.data || []
    // 如果只有一个日期，自动选中
    if (scheduleDates.value.length === 1) {
      selectedDate.value = scheduleDates.value[0]
      await onDateChange(selectedDate.value)
    }
  } catch (error) {
    ElMessage.error('获取排班日期失败')
  } finally {
    loadingDates.value = false
  }
}

// 日期变化时加载时段
const onDateChange = async (date: string) => {
  if (!date || !selectedDoctor.value) return

  selectedSchedule.value = null
  loadingSchedules.value = true
  try {
    const res = await getScheduleListForAppointment(selectedDoctor.value.doctorId, date)
    scheduleList.value = res.data || []
  } catch (error) {
    ElMessage.error('获取排班时段失败')
  } finally {
    loadingSchedules.value = false
  }
}

// 选择时段
const selectSchedule = (schedule: any) => {
  selectedSchedule.value = schedule
}

// 上一步
const prevStep = () => {
  if (activeStep.value > 0) {
    activeStep.value--
  }
}

// 下一步
const nextStep = () => {
  if (activeStep.value < 3) {
    activeStep.value++
  }
}

// 提交挂号
const submitAppointment = async () => {
  if (!patientId.value) {
    ElMessage.warning('请输入患者ID')
    return
  }
  if (Number(patientId.value) <= 0) {
    ElMessage.warning('请输入有效的患者ID')
    return
  }

  submitting.value = true
  try {
    await createAppointment({
      patientId: Number(patientId.value),
      doctorId: selectedDoctor.value?.doctorId,
      deptId: selectedDept.value?.deptId,
      scheduleId: selectedSchedule.value?.scheduleId,
      reason: reason.value
    })
    ElMessage.success('挂号成功！')
    // 重置表单
    activeStep.value = 0
    selectedDept.value = null
    selectedDoctor.value = null
    selectedDate.value = ''
    selectedSchedule.value = null
    patientId.value = ''
    reason.value = ''
    // 重新加载科室列表
    loadDepartments()
  } catch (error: any) {
    ElMessage.error(error.message || '挂号失败')
  } finally {
    submitting.value = false
  }
}

onMounted(() => {
  loadDepartments()
})
</script>

<style scoped>
.appointment-container {
  padding: 20px;
  min-height: calc(100vh - 120px);
  background-color: #f5f7fa;
}
.appointment-card {
  max-width: 1000px;
  margin: 0 auto;
}
.card-title {
  font-size: 18px;
  font-weight: bold;
}
.step-content {
  min-height: 420px;
  padding: 20px 0;
}
.step-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 20px;
}
.sub-title {
  font-size: 14px;
  font-weight: normal;
  color: #909399;
  margin-left: 10px;
}
.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  gap: 16px;
}
.card-grid-small {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.selector-card {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 16px;
  cursor: pointer;
  transition: all 0.3s;
  background: #fff;
}
.selector-card:hover {
  border-color: #409eff;
  transform: translateY(-2px);
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}
.selector-card.active {
  border-color: #409eff;
  background: #ecf5ff;
}
.card-name {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 8px;
}
.card-desc {
  font-size: 12px;
  color: #909399;
}
.time-card {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 10px 16px;
  cursor: pointer;
  transition: all 0.3s;
  text-align: center;
  min-width: 90px;
  background: #fff;
}
.time-card:hover {
  border-color: #409eff;
}
.time-card.active {
  border-color: #409eff;
  background: #ecf5ff;
}
.time-card.low .remain {
  color: #f56c6c;
}
.time {
  font-size: 14px;
  font-weight: bold;
}
.remain {
  font-size: 11px;
  color: #67c23a;
  margin-top: 4px;
}
.date-group,
.time-group {
  margin-bottom: 24px;
  display: flex;
  align-items: flex-start;
  flex-wrap: wrap;
  gap: 12px;
}
.label {
  font-weight: bold;
  width: 80px;
  line-height: 32px;
}
.step-actions {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  gap: 20px;
}
.empty-tip {
  text-align: center;
  color: #909399;
  padding: 40px;
  grid-column: 1 / -1;
}
.empty-tip-inline {
  color: #909399;
  font-size: 13px;
  line-height: 32px;
}
</style>
