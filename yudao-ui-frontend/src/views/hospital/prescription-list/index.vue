<template>
  <div class="prescription-list-container">
    <el-card>
      <template #header>
        <span>处方列表</span>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="患者姓名">
          <el-input v-model="searchForm.patientName" placeholder="请输入患者姓名" clearable />
        </el-form-item>
        <el-form-item label="就诊卡号">
          <el-input v-model="searchForm.medicalCardNo" placeholder="请输入就诊卡号" clearable />
        </el-form-item>
        <el-form-item label="日期范围">
          <el-date-picker
            v-model="searchForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchPrescriptions">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 处方表格 -->
      <el-table :data="prescriptionList" border stripe v-loading="loading">
        <el-table-column prop="prescriptionNo" label="处方编号" width="180" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="medicalCardNo" label="就诊卡号" width="120" />
        <el-table-column prop="diagnosis" label="诊断" min-width="150" show-overflow-tooltip />
        <el-table-column prop="totalAmount" label="总金额" width="100">
          <template #default="{ row }">
            <span style="color: #f56c6c; font-weight: bold">¥{{ row.totalAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="doctorName" label="开方医生" width="100" />
        <el-table-column prop="createTime" label="开方时间" width="160" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" link @click="viewDetail(row)"
              >查看详情</el-button
            >
            <el-button type="danger" size="small" link @click="cancelPrescription(row)"
              >作废</el-button
            >
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
          @size-change="loadPrescriptionList"
          @current-change="loadPrescriptionList"
        />
      </div>
    </el-card>

    <!-- 处方详情弹窗 -->
    <el-dialog v-model="detailVisible" title="处方详情" width="600px">
      <div v-if="currentPrescription">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="处方编号">{{
            currentPrescription.prescriptionNo
          }}</el-descriptions-item>
          <el-descriptions-item label="开方医生">{{
            currentPrescription.doctorName
          }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{
            currentPrescription.patientName
          }}</el-descriptions-item>
          <el-descriptions-item label="就诊卡号">{{
            currentPrescription.medicalCardNo
          }}</el-descriptions-item>
          <el-descriptions-item label="诊断结果" :span="2">{{
            currentPrescription.diagnosis
          }}</el-descriptions-item>
          <el-descriptions-item label="开方时间">{{
            currentPrescription.createTime
          }}</el-descriptions-item>
          <el-descriptions-item label="处方状态">
            <el-tag :type="getStatusType(currentPrescription.status)">{{
              getStatusText(currentPrescription.status)
            }}</el-tag>
          </el-descriptions-item>
        </el-descriptions>

        <el-divider>药品明细</el-divider>

        <el-table :data="currentPrescription.items" border size="small">
          <el-table-column prop="medicineName" label="药品名称" />
          <el-table-column prop="specification" label="规格" />
          <el-table-column prop="quantity" label="数量" width="80" />
          <el-table-column prop="price" label="单价" width="100">
            <template #default="{ row }">¥{{ row.price }}</template>
          </el-table-column>
          <el-table-column prop="subtotal" label="小计" width="100">
            <template #default="{ row }">¥{{ row.subtotal }}</template>
          </el-table-column>
          <el-table-column prop="usage" label="用法" width="80" />
        </el-table>

        <div class="detail-total"> 总金额：¥{{ currentPrescription.totalAmount }} </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  patientName: '',
  medicalCardNo: '',
  dateRange: []
})

// 分页参数
const pageParams = reactive({
  pageNo: 1,
  pageSize: 10
})

const total = ref(0)
const loading = ref(false)
const prescriptionList = ref([])
const detailVisible = ref(false)
const currentPrescription = ref(null)

// Mock 处方数据（等后端接口好了替换）
const mockPrescriptionList = [
  {
    id: 1,
    prescriptionNo: 'P202604140001',
    patientName: '张三',
    medicalCardNo: 'M20240001',
    diagnosis: '上呼吸道感染',
    totalAmount: 48.5,
    doctorName: '张为民',
    createTime: '2026-04-14 09:30:00',
    status: '1', // 1:正常 2:已作废
    items: [
      {
        medicineName: '阿莫西林胶囊',
        specification: '0.25g*20粒',
        quantity: 2,
        price: 25.5,
        subtotal: 51.0,
        usage: '口服'
      },
      {
        medicineName: '布洛芬缓释胶囊',
        specification: '0.3g*10粒',
        quantity: 1,
        price: 15.0,
        subtotal: 15.0,
        usage: '口服'
      }
    ]
  },
  {
    id: 2,
    prescriptionNo: 'P202604140002',
    patientName: '李四',
    medicalCardNo: 'M20240002',
    diagnosis: '高血压',
    totalAmount: 89.0,
    doctorName: '李芳芳',
    createTime: '2026-04-14 10:15:00',
    status: '1',
    items: [
      {
        medicineName: '硝苯地平片',
        specification: '10mg*30片',
        quantity: 1,
        price: 35.0,
        subtotal: 35.0,
        usage: '口服'
      },
      {
        medicineName: '厄贝沙坦片',
        specification: '75mg*14片',
        quantity: 2,
        price: 27.0,
        subtotal: 54.0,
        usage: '口服'
      }
    ]
  },
  {
    id: 3,
    prescriptionNo: 'P202604140003',
    patientName: '王五',
    medicalCardNo: 'M20240003',
    diagnosis: '皮肤过敏',
    totalAmount: 32.0,
    doctorName: '陈冠希',
    createTime: '2026-04-14 14:20:00',
    status: '2', // 已作废
    items: [
      {
        medicineName: '氯雷他定片',
        specification: '10mg*6片',
        quantity: 1,
        price: 22.0,
        subtotal: 22.0,
        usage: '口服'
      },
      {
        medicineName: '维生素C片',
        specification: '100mg*100片',
        quantity: 1,
        price: 8.0,
        subtotal: 8.0,
        usage: '口服'
      }
    ]
  }
]

// 获取状态文本
const getStatusText = (status) => {
  const map = { 1: '正常', 2: '已作废' }
  return map[status] || '未知'
}

// 获取状态标签类型
const getStatusType = (status) => {
  const map = { 1: 'success', 2: 'danger' }
  return map[status] || 'info'
}

// 加载处方列表
const loadPrescriptionList = async () => {
  loading.value = true
  try {
    // TODO: 调用后端接口
    // const res = await getPrescriptionList({ ...searchForm, ...pageParams })
    // prescriptionList.value = res.data.rows
    // total.value = res.data.total

    // 使用 Mock 数据
    await new Promise((resolve) => setTimeout(resolve, 300))
    prescriptionList.value = mockPrescriptionList
    total.value = mockPrescriptionList.length
  } finally {
    loading.value = false
  }
}

// 搜索
const searchPrescriptions = () => {
  pageParams.pageNo = 1
  loadPrescriptionList()
}

// 重置搜索
const resetSearch = () => {
  searchForm.patientName = ''
  searchForm.medicalCardNo = ''
  searchForm.dateRange = []
  searchPrescriptions()
}

// 查看详情
const viewDetail = (row) => {
  currentPrescription.value = row
  detailVisible.value = true
}

// 作废处方
const cancelPrescription = async (row) => {
  if (row.status === '2') {
    ElMessage.warning('处方已作废，不能重复操作')
    return
  }

  try {
    await ElMessageBox.confirm(`确定要作废处方 ${row.prescriptionNo} 吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    // TODO: 调用后端接口
    // await cancelPrescriptionAPI(row.id)

    ElMessage.success('处方已作废')
    loadPrescriptionList() // 刷新列表
  } catch {
    // 取消操作
  }
}

// 初始化
onMounted(() => {
  loadPrescriptionList()
})
</script>

<style scoped>
.prescription-list-container {
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

.detail-total {
  margin-top: 16px;
  text-align: right;
  font-size: 16px;
  font-weight: bold;
  color: #f56c6c;
}
</style>
