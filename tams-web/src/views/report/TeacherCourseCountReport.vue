<template>
  <el-row>
    <el-col :style="{ width: 'calc(100% - ' + width + ')' }">
      <el-table stripe border :data="data">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="name" label="老师姓名" width="200" />
        <el-table-column prop="count" label="课时数量" />
      </el-table>
    </el-col>
    <el-col :style="{ width: width }">
      <div ref="chartRef" :style="{ width: width, height: '600px' }" />
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import * as echarts from 'echarts'
import { useReportStore } from '@/stores/report'

const props = defineProps<{ startDate: string; endDate: string }>()

const reportStore = useReportStore()
const data = ref<any[]>([])
const teacherData = ref<string[]>([])
const countData = ref<number[]>([])
const colorData = ref<string[]>([])
const width = ref('600px')
const chartRef = ref<HTMLDivElement>()

const search = () => {
  reportStore.getReportTeacherCount({
    startDate: props.startDate,
    endDate: props.endDate
  }).then((res: any) => {
    if (res) {
      data.value = res
      teacherData.value = []
      countData.value = []
      colorData.value = []
      for (let i = 0; i < data.value.length; i++) {
        if (i < 10) {
          const item = data.value[i]
          teacherData.value.push(item.name)
          countData.value.push(item.count)
          colorData.value.push(item.color)
        }
      }
      teacherData.value.reverse()
      countData.value.reverse()
      colorData.value.reverse()
      draw()
    }
  }).catch(() => {})
}

const draw = () => {
  if (!chartRef.value) return
  const chart = echarts.init(chartRef.value)
  chart.setOption({
    title: { text: 'Top10' },
    grid: { left: '15%' },
    xAxis: { type: 'value', minInterval: 1 },
    yAxis: { type: 'category', data: teacherData.value },
    series: [{
      type: 'bar',
      data: countData.value,
      label: { show: true },
      itemStyle: {
        color: (params: any) => colorData.value[params.dataIndex] || '#409EFF'
      }
    }]
  })
}

watch(() => props.startDate, () => search())

defineExpose({ search })
</script>
