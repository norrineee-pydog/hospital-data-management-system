<template>
  <div class="my-registration-container">
    <el-card>
      <template #header>
        <span>我的挂号记录</span>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="医生姓名">
          <el-input v-model="searchForm.doctorName" placeholder="请输入医生姓名" clearable />
        </el-form-item>
        <el-form-item label="挂号日期">
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="待就诊" value="1" />
            <el-option label="已完成" value="2" />
            <el-option label="已取消" value="3" />
            <el-option label="已过期" value="4" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchRegistrations">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 挂号记录表格 -->
      <el-table :data="registrationList" border stripe v-loading="loading">
        <el-table-column prop="registrationNo" label="挂号单号" width="180" />
        <el-table-column prop="doctorName" label="医生姓名" width="100" />
        <el-table-column prop="deptName" label="科室" width="100" />
        <el-table-column prop="registrationDate" label="挂号日期" width="120" />
        <el-table-column prop="timeSlot" label="时段" width="80" />
        <el-table-column prop="queueNumber" label="排队号" width="80" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="挂号时间" width="160" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" link @click="viewDetail(row)">
              查看详情
            </el-button>
            <el-button
              v-if="row.status === '1'"
              type="danger"
              size="small"
              link
              @click="cancelRegistration(row)"
            >
              取消
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pageParams.pageNo"
          v-model:page-size="pageParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="loadRegistrationList"
          @current-change="loadRegistrationList"
        />
      </div>
    </el-card>

    <!-- 挂号详情弹窗 -->
    <el-dialog v-model="detailVisible" title="挂号详情" width="500px">
      <div v-if="currentRegistration">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="挂号单号">{{
            currentRegistration.registrationNo
          }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusType(currentRegistration.status)">
              {{ getStatusText(currentRegistration.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="医生姓名">{{
            currentRegistration.doctorName
          }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{
            currentRegistration.deptName
          }}</el-descriptions-item>
          <el-descriptions-item label="挂号日期">{{
            currentRegistration.registrationDate
          }}</el-descriptions-item>
          <el-descriptions-item label="时段">{{
            currentRegistration.timeSlot
          }}</el-descriptions-item>
          <el-descriptions-item label="排队号">{{
            currentRegistration.queueNumber
          }}</el-descriptions-item>
          <el-descriptions-item label="挂号时间">{{
            currentRegistration.createTime
          }}</el-descriptions-item>
          <el-descriptions-item label="就诊状态">{{
            currentRegistration.visitStatus || '待就诊'
          }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  doctorName: '',
  dateRange: [],
  status: ''
})

// 分页参数
const pageParams = reactive({
  pageNo: 1,
  pageSize: 10
})

const total = ref(0)
const loading = ref(false)
const registrationList = ref([])
const detailVisible = ref(false)
const currentRegistration = ref(null)

// Mock 挂号记录数据（等后端接口好了替换）
const mockRegistrationList = [
  {
    id: 1,
    registrationNo: 'R202604140001',
    doctorName: '张为民',
    deptName: '内科',
    registrationDate: '2026-04-14',
    timeSlot: '上午',
    queueNumber: '5',
    status: '1', // 1:待就诊 2:已完成 3:已取消 4:已过期
    createTime: '2026-04-13 09:30:00',
    visitStatus: '待就诊'
  },
  {
    id: 2,
    registrationNo: 'R202604140002',
    doctorName: '李芳芳',
    deptName: '内科',
    registrationDate: '2026-04-15',
    timeSlot: '下午',
    queueNumber: '3',
    status: '1',
    createTime: '2026-04-13 10:15:00',
    visitStatus: '待就诊'
  },
  {
    id: 3,
    registrationNo: 'R202604130001',
    doctorName: '王建国',
    deptName: '外科',
    registrationDate: '2026-04-13',
    timeSlot: '上午',
    queueNumber: '2',
    status: '2', // 已完成
    createTime: '2026-04-12 08:20:00',
    visitStatus: '已就诊'
  },
  {
    id: 4,
    registrationNo: 'R202604120001',
    doctorName: '赵丽颖',
    deptName: '儿科',
    registrationDate: '2026-04-12',
    timeSlot: '上午',
    queueNumber: '8',
    status: '3', // 已取消
    createTime: '2026-04-11 14:30:00',
    visitStatus: '已取消'
  }
]

// 获取状态文本
const getStatusText = (status) => {
  const map = {
    1: '待就诊',
    2: '已完成',
    3: '已取消',
    4: '已过期'
  }
  return map[status] || '未知'
}

// 获取状态标签类型
const getStatusType = (status) => {
  const map = {
    1: 'warning',
    2: 'success',
    3: 'danger',
    4: 'info'
  }
  return map[status] || 'info'
}

// 加载挂号记录列表
const loadRegistrationList = async () => {
  loading.value = true
  try {
    // TODO: 调用后端接口
    // const res = await getMyRegistrations({ ...searchForm, ...pageParams })
    // registrationList.value = res.data.rows
    // total.value = res.data.total

    // 使用 Mock 数据
    await new Promise((resolve) => setTimeout(resolve, 300))
    registrationList.value = mockRegistrationList
    total.value = mockRegistrationList.length
  } finally {
    loading.value = false
  }
}

// 搜索
const searchRegistrations = () => {
  pageParams.pageNo = 1
  loadRegistrationList()
}

// 重置搜索
const resetSearch = () => {
  searchForm.doctorName = ''
  searchForm.dateRange = []
  searchForm.status = ''
  searchRegistrations()
}

// 查看详情
const viewDetail = (row) => {
  currentRegistration.value = row
  detailVisible.value = true
}

// 取消挂号
const cancelRegistration = async (row) => {
  try {
    await ElMessageBox.confirm(`确定要取消 ${row.doctorName} 医生的挂号吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    // TODO: 调用后端接口
    // await cancelRegistrationAPI(row.id)

    ElMessage.success('挂号已取消')
    loadRegistrationList() // 刷新列表
  } catch {
    // 取消操作
  }
}

// 初始化
onMounted(() => {
  loadRegistrationList()
})
</script>

<style scoped>
.my-registration-container {
  padding: 20px;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
