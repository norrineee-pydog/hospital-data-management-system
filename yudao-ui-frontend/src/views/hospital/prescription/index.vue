<template>
  <div class="prescription-container">
    <el-card>
      <template #header>
        <span>开具处方</span>
      </template>

      <!-- 患者信息 -->
      <el-form :model="prescriptionForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="患者姓名" required>
              <el-input v-model="prescriptionForm.patientName" placeholder="请输入患者姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="就诊卡号" required>
              <el-input v-model="prescriptionForm.medicalCardNo" placeholder="请输入就诊卡号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="诊断结果" required>
              <el-input
                v-model="prescriptionForm.diagnosis"
                type="textarea"
                :rows="3"
                placeholder="请输入诊断结果"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <!-- 处方明细表格 -->
      <div class="prescription-items">
        <div class="items-header">
          <span>处方明细</span>
          <el-button type="primary" size="small" @click="addMedicine">+ 添加药品</el-button>
        </div>

        <el-table :data="prescriptionForm.items" border stripe>
          <el-table-column label="药品名称" width="250">
            <template #default="{ row }">
              <el-select v-model="row.medicineId" placeholder="请选择药品" filterable>
                <el-option
                  v-for="medicine in medicineList"
                  :key="medicine.id"
                  :label="medicine.name"
                  :value="medicine.id"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="规格" width="120">
            <template #default="{ row }">
              {{ getMedicineSpec(row.medicineId) }}
            </template>
          </el-table-column>
          <el-table-column label="单价" width="100">
            <template #default="{ row }"> ¥{{ getMedicinePrice(row.medicineId) }} </template>
          </el-table-column>
          <el-table-column label="数量" width="120">
            <template #default="{ row }">
              <el-input-number v-model="row.quantity" :min="1" :max="99" size="small" />
            </template>
          </el-table-column>
          <el-table-column label="小计" width="120">
            <template #default="{ row }"> ¥{{ calculateSubtotal(row) }} </template>
          </el-table-column>
          <el-table-column label="用法" width="150">
            <template #default="{ row }">
              <el-select v-model="row.usage" placeholder="请选择用法" size="small">
                <el-option label="口服" value="口服" />
                <el-option label="外用" value="外用" />
                <el-option label="注射" value="注射" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template #default="{ $index }">
              <el-button type="danger" size="small" link @click="removeMedicine($index)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 合计和提交按钮 -->
      <div class="total-area">
        <div class="total-amount"> 总金额：¥{{ totalAmount }} </div>
        <el-button type="primary" size="large" @click="submitPrescription">保存处方</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { ElMessage } from 'element-plus'

// 处方表单数据
const prescriptionForm = reactive({
  patientName: '',
  medicalCardNo: '',
  diagnosis: '',
  items: [{ medicineId: null, quantity: 1, usage: '口服' }]
})

// Mock 药品列表
const medicineList = ref([
  { id: 1, name: '阿莫西林胶囊', specification: '0.25g*20粒', price: 25.5 },
  { id: 2, name: '布洛芬缓释胶囊', specification: '0.3g*10粒', price: 15.0 },
  { id: 3, name: '维生素C片', specification: '100mg*100片', price: 8.0 },
  { id: 4, name: '头孢克肟分散片', specification: '50mg*12片', price: 32.0 },
  { id: 5, name: '复方甘草口服液', specification: '100ml', price: 18.5 }
])

// 获取药品信息
const getMedicineById = (id) => {
  return medicineList.value.find((m) => m.id === id)
}

// 获取药品价格
const getMedicinePrice = (id) => {
  const medicine = getMedicineById(id)
  return medicine ? medicine.price : 0
}

// 获取药品规格
const getMedicineSpec = (id) => {
  const medicine = getMedicineById(id)
  return medicine ? medicine.specification : ''
}

// 计算单行小计
const calculateSubtotal = (row) => {
  const price = getMedicinePrice(row.medicineId)
  return (price * (row.quantity || 0)).toFixed(2)
}

// 计算总金额
const totalAmount = computed(() => {
  let total = 0
  prescriptionForm.items.forEach((item) => {
    if (item.medicineId) {
      const price = getMedicinePrice(item.medicineId)
      total += price * (item.quantity || 0)
    }
  })
  return total.toFixed(2)
})

// 添加药品
const addMedicine = () => {
  prescriptionForm.items.push({
    medicineId: null,
    quantity: 1,
    usage: '口服'
  })
}

// 删除药品
const removeMedicine = (index) => {
  if (prescriptionForm.items.length === 1) {
    ElMessage.warning('至少需要有一种药品')
    return
  }
  prescriptionForm.items.splice(index, 1)
}

// 提交处方
const submitPrescription = async () => {
  // 表单验证
  if (!prescriptionForm.patientName) {
    ElMessage.warning('请填写患者姓名')
    return
  }
  if (!prescriptionForm.medicalCardNo) {
    ElMessage.warning('请填写就诊卡号')
    return
  }
  if (!prescriptionForm.diagnosis) {
    ElMessage.warning('请填写诊断结果')
    return
  }

  // 检查药品是否都选择了
  const hasEmptyMedicine = prescriptionForm.items.some((item) => !item.medicineId)
  if (hasEmptyMedicine) {
    ElMessage.warning('请选择所有药品')
    return
  }

  try {
    // TODO: 调用后端接口
    await new Promise((resolve) => setTimeout(resolve, 500))

    ElMessage.success({
      message: '处方保存成功！',
      duration: 2000
    })

    // 重置表单
    prescriptionForm.patientName = ''
    prescriptionForm.medicalCardNo = ''
    prescriptionForm.diagnosis = ''
    prescriptionForm.items = [{ medicineId: null, quantity: 1, usage: '口服' }]

    // 可选：询问是否跳转到处方列表
    const { confirm } = await ElMessageBox.confirm('是否前往查看处方列表？', '提示', {
      confirmButtonText: '前往',
      cancelButtonText: '留在本页'
    })
    if (confirm) {
      router.push('/hospital/prescription-list')
    }
  } catch (error) {
    ElMessage.error('处方保存失败，请重试')
  }
}
</script>

<style scoped>
.prescription-container {
  padding: 20px;
}

.prescription-items {
  margin-top: 20px;
}

.items-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  font-weight: bold;
}

.total-area {
  margin-top: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 16px;
  border-top: 1px solid #ebeef5;
}

.total-amount {
  font-size: 18px;
  font-weight: bold;
  color: #f56c6c;
}
</style>
