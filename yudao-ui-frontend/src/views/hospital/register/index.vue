<template>
  <div class="register-container">
    <el-card>
      <template #header>
        <span>医生排班 - 选择日期挂号</span>
      </template>

      <!-- 日历组件 -->
      <el-calendar v-model="selectedDate">
        <template #date-cell="{ data }">
          <div
            class="calendar-cell"
            :class="{ 'is-selected': isSelectedDate(data) }"
            @click="selectDate(data)"
          >
            <span class="date-num">{{ data.day.split('-')[2] }}</span>
            <div class="schedule-info" v-if="getScheduleByDate(data.day)">
              <el-tag size="small" type="success">
                {{ getScheduleByDate(data.day).length }}位医生
              </el-tag>
            </div>
          </div>
        </template>
      </el-calendar>
    </el-card>

    <!-- 排班详情弹窗 -->
    <el-dialog v-model="dialogVisible" :title="`${selectedDateStr} 排班医生`" width="500px">
      <el-table :data="currentSchedules" stripe>
        <el-table-column prop="doctorName" label="医生姓名" />
        <el-table-column prop="deptName" label="科室" />
        <el-table-column prop="timeSlot" label="时段" />
        <el-table-column prop="remainingNum" label="剩余号源" width="100">
          <template #default="{ row }">
            <el-tag :type="row.remainingNum > 0 ? 'success' : 'danger'">
              {{ row.remainingNum }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              :disabled="row.remainingNum === 0"
              @click="handleRegister(row)"
            >
              挂号
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'

// 当前选中的日期
const selectedDate = ref(new Date())
const dialogVisible = ref(false)

// 已挂号的医生记录 (格式: "医生ID_日期")
const registeredDoctorIds = ref([])

// Mock 排班数据
const schedulesData = ref([
  {
    id: 1,
    doctorName: '张为民',
    deptName: '内科',
    timeSlot: '上午',
    date: '2026-04-14',
    remainingNum: 5
  },
  {
    id: 2,
    doctorName: '李芳芳',
    deptName: '内科',
    timeSlot: '下午',
    date: '2026-04-14',
    remainingNum: 3
  },
  {
    id: 3,
    doctorName: '王建国',
    deptName: '外科',
    timeSlot: '上午',
    date: '2026-04-15',
    remainingNum: 2
  },
  {
    id: 4,
    doctorName: '赵丽颖',
    deptName: '儿科',
    timeSlot: '上午',
    date: '2026-04-15',
    remainingNum: 4
  },
  {
    id: 5,
    doctorName: '陈冠希',
    deptName: '皮肤科',
    timeSlot: '下午',
    date: '2026-04-16',
    remainingNum: 0
  }
])

// 根据日期获取排班
const getScheduleByDate = (date) => {
  return schedulesData.value.filter((item) => item.date === date)
}

// 当前选中日期的排班列表
const currentSchedules = computed(() => {
  return getScheduleByDate(selectedDateStr.value)
})

// 格式化日期为 YYYY-MM-DD
const formatDate = (date) => {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

// 选中的日期字符串
const selectedDateStr = computed(() => {
  return formatDate(selectedDate.value)
})

// 判断是否是选中的日期
const isSelectedDate = (data) => {
  return data.day === selectedDateStr.value
}

// 判断是否已挂过该医生的号
const isDoctorRegistered = (doctorId) => {
  const key = `${doctorId}_${selectedDateStr.value}`
  return registeredDoctorIds.value.includes(key)
}

// 点击日期格子
const selectDate = (data) => {
  const dateStr = data.day
  selectedDate.value = new Date(dateStr)

  const schedules = getScheduleByDate(dateStr)
  if (schedules.length > 0) {
    dialogVisible.value = true
  } else {
    ElMessage.info('该日期暂无排班')
  }
}

// 挂号操作
const handleRegister = async (doctor) => {
  const key = `${doctor.id}_${selectedDateStr.value}`

  // 检查是否已挂号
  if (isDoctorRegistered(doctor.id)) {
    ElMessage.warning(`您今天已经挂过 ${doctor.doctorName} 医生的号了`)
    return
  }

  // 检查号源
  if (doctor.remainingNum <= 0) {
    ElMessage.warning(`${doctor.doctorName} 医生的号已满`)
    return
  }

  try {
    // TODO: 调用后端接口
    // await submitRegister({ doctorId: doctor.id, date: selectedDateStr.value })

    // 模拟接口调用
    await new Promise((resolve) => setTimeout(resolve, 500))

    // 挂号成功
    ElMessage.success(`成功挂上 ${doctor.doctorName} 医生的号`)

    // 记录已挂号
    registeredDoctorIds.value.push(key)

    // 减少剩余号源
    doctor.remainingNum--

    // 如果号源变为0，刷新弹窗显示
    if (doctor.remainingNum === 0) {
      // 触发界面更新
      schedulesData.value = [...schedulesData.value]
    }
  } catch (error) {
    ElMessage.error('挂号失败，请重试')
  }
}
</script>

<style scoped>
.register-container {
  padding: 20px;
}

.calendar-cell {
  min-height: 80px;
  padding: 4px;
  cursor: pointer;
}

.calendar-cell .date-num {
  font-size: 14px;
  font-weight: bold;
}

.calendar-cell.is-selected {
  background-color: #ecf5ff;
  border-radius: 4px;
}

.calendar-cell .schedule-info {
  margin-top: 8px;
}

:deep(.el-calendar-table .el-calendar-day) {
  height: auto;
  min-height: 80px;
}
</style>
