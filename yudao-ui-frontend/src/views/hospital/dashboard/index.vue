<template>
  <div class="dashboard-container">
    <!-- 欢迎标题 -->
    <div class="welcome-header">
      <h2>院长驾驶舱</h2>
      <p>医院运营数据实时监控</p>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stat-cards">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon :size="40"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.todayVisits }}</div>
              <div class="stat-label">今日门诊量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon :size="40"><FirstAidKit /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.totalBeds }}</div>
              <div class="stat-label">总床位数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon :size="40"><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.usedBeds }}</div>
              <div class="stat-label">已用床位</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon :size="40"><Money /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">¥{{ stats.todayIncome }}</div>
              <div class="stat-label">今日收入</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>各科室接诊量占比</span>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <span>近7天门诊趋势</span>
          </template>
          <div ref="lineChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="charts-row">
      <el-col :span="24">
        <el-card class="chart-card">
          <template #header>
            <span>病房床位使用率</span>
          </template>
          <div ref="barChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import { User, FirstAidKit, OfficeBuilding, Money } from '@element-plus/icons-vue'

// 统计数据
const stats = ref({
  todayVisits: 156,
  totalBeds: 300,
  usedBeds: 218,
  todayIncome: 48500
})

// 图表容器
const pieChartRef = ref(null)
const lineChartRef = ref(null)
const barChartRef = ref(null)

// Mock 数据 - 各科室接诊量
const deptVisitData = [
  { name: '内科', value: 320 },
  { name: '外科', value: 280 },
  { name: '儿科', value: 180 },
  { name: '妇产科', value: 220 },
  { name: '皮肤科', value: 120 },
  { name: '眼科', value: 90 }
]

// Mock 数据 - 近7天门诊趋势
const weeklyTrendData = {
  dates: ['04-07', '04-08', '04-09', '04-10', '04-11', '04-12', '04-13'],
  visits: [145, 132, 168, 189, 174, 210, 198]
}

// Mock 数据 - 病房床位使用率
const bedUsageData = [
  { name: '内科病房', rate: 85 },
  { name: '外科病房', rate: 92 },
  { name: '儿科病房', rate: 68 },
  { name: '妇产科病房', rate: 78 },
  { name: 'ICU', rate: 95 },
  { name: '康复科病房', rate: 55 }
]

// 初始化饼图（科室接诊量）
const initPieChart = () => {
  if (!pieChartRef.value) return

  const chart = echarts.init(pieChartRef.value)
  chart.setOption({
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {d}% ({c}人)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      textStyle: { fontSize: 12 }
    },
    series: [
      {
        type: 'pie',
        radius: '55%',
        center: ['50%', '50%'],
        data: deptVisitData,
        emphasis: {
          scale: true,
          label: { show: true, fontWeight: 'bold' }
        },
        label: { show: true, formatter: '{b}: {d}%' },
        itemStyle: {
          borderRadius: 8,
          borderColor: '#fff',
          borderWidth: 2
        }
      }
    ],
    color: ['#5470c6', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452']
  })

  // 窗口大小变化时自适应
  window.addEventListener('resize', () => chart.resize())
}

// 初始化折线图（门诊趋势）
const initLineChart = () => {
  if (!lineChartRef.value) return

  const chart = echarts.init(lineChartRef.value)
  chart.setOption({
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: weeklyTrendData.dates,
      axisLabel: { rotate: 0 }
    },
    yAxis: {
      type: 'value',
      name: '门诊量（人次）'
    },
    series: [
      {
        name: '门诊量',
        type: 'line',
        data: weeklyTrendData.visits,
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: { width: 3, color: '#5470c6' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(84, 112, 198, 0.5)' },
            { offset: 1, color: 'rgba(84, 112, 198, 0.05)' }
          ])
        },
        itemStyle: { color: '#5470c6' },
        label: { show: true, position: 'top' }
      }
    ]
  })

  window.addEventListener('resize', () => chart.resize())
}

// 初始化柱状图（床位使用率）
const initBarChart = () => {
  if (!barChartRef.value) return

  const chart = echarts.init(barChartRef.value)
  chart.setOption({
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' },
      formatter: '{b}: {c}%'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: bedUsageData.map((item) => item.name),
      axisLabel: { rotate: 15, fontSize: 11 }
    },
    yAxis: {
      type: 'value',
      name: '使用率（%）',
      max: 100,
      axisLabel: { formatter: '{value}%' }
    },
    series: [
      {
        name: '床位使用率',
        type: 'bar',
        data: bedUsageData.map((item) => item.rate),
        barWidth: '50%',
        itemStyle: {
          borderRadius: [8, 8, 0, 0],
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: '#73c0de' },
              { offset: 1, color: '#5470c6' }
            ]
          }
        },
        label: {
          show: true,
          position: 'top',
          formatter: '{c}%'
        }
      }
    ]
  })

  window.addEventListener('resize', () => chart.resize())
}

// 加载统计数据（等后端接口好了替换）
const loadStats = async () => {
  // TODO: 调用后端接口
  // const res = await getStatistics()
  // stats.value = res.data

  // 暂时使用 Mock 数据
  console.log('加载统计数据中...')
}

// 页面初始化
onMounted(() => {
  // 延迟一下，确保 DOM 渲染完成
  setTimeout(() => {
    initPieChart()
    initLineChart()
    initBarChart()
  }, 100)
  loadStats()
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 100px);
}

.welcome-header {
  margin-bottom: 24px;
}

.welcome-header h2 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 8px;
}

.welcome-header p {
  color: #909399;
  font-size: 14px;
}

.stat-cards {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.stat-icon {
  color: #409eff;
  background-color: #ecf5ff;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
}

.stat-info {
  text-align: right;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 4px;
}

.charts-row {
  margin-bottom: 20px;
}

.chart-card {
  height: 400px;
}

.chart-container {
  width: 100%;
  height: 320px;
}
</style>
