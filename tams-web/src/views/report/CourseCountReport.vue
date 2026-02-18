<template>
  <el-row>
    <el-col :style="{ width: 'calc(100% - ' + width + ')' }">
      <el-table stripe border :data="data">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="name" label="课程名称" width="200" />
        <el-table-column prop="count" label="课程数量" />
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
const courseData = ref<string[]>([])
const countData = ref<number[]>([])
const width = ref('600px')
const chartRef = ref<HTMLDivElement>()

const search = () => {
  reportStore.getReportCourseCount({
    startDate: props.startDate,
    endDate: props.endDate
  }).then((res: any) => {
    if (res) {
      data.value = res
      courseData.value = []
      countData.value = []
      for (let i = 0; i < data.value.length; i++) {
        if (i < 10) {
          const item = data.value[i]
          courseData.value.push(item.name)
          countData.value.push(item.count)
        }
      }
      courseData.value.reverse()
      countData.value.reverse()
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
    yAxis: { type: 'category', data: courseData.value },
    series: [{
      type: 'bar',
      data: countData.value,
      label: { show: true },
      itemStyle: { color: () => '#409EFF' }
    }]
  })
}

watch(() => props.startDate, () => search())

defineExpose({ search })
</script>
