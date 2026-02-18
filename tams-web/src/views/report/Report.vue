<template>
  <el-card shadow="never">
    <template #header>
      <div class="clearfix"><span>报表</span></div>
    </template>
    <el-tabs v-model="currentTab">
      <el-tab-pane label="课时数量" name="teacherCourseCount">
        <TeacherCourseCountReport ref="teacherCourseCountRef" :start-date="startDate" :end-date="endDate" />
      </el-tab-pane>
      <el-tab-pane label="课程数量" name="courseCount">
        <CourseCountReport ref="courseCountRef" :start-date="startDate" :end-date="endDate" />
      </el-tab-pane>
    </el-tabs>
    <el-date-picker
      :clearable="false"
      v-model="dates"
      type="daterange"
      range-separator="至"
      :shortcuts="shortcuts"
      style="position: fixed; top: 55px; right: 23px; width: 280px;"
      @change="handleDateChange"
    />
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import dayjs from 'dayjs'
import TeacherCourseCountReport from '@/views/report/TeacherCourseCountReport.vue'
import CourseCountReport from '@/views/report/CourseCountReport.vue'

const currentTab = ref('teacherCourseCount')
const dates = ref<[Date, Date]>([
  dayjs().startOf('month').toDate(),
  dayjs().endOf('month').toDate()
])
const startDate = ref(dayjs().startOf('month').format('YYYY-MM-DD'))
const endDate = ref(dayjs().endOf('month').format('YYYY-MM-DD'))
const teacherCourseCountRef = ref<InstanceType<typeof TeacherCourseCountReport>>()
const courseCountRef = ref<InstanceType<typeof CourseCountReport>>()

const shortcuts = [
  {
    text: '本月',
    value: () => [dayjs().startOf('month').toDate(), dayjs().endOf('month').toDate()]
  },
  {
    text: '上月',
    value: () => [dayjs().subtract(1, 'month').startOf('month').toDate(), dayjs().subtract(1, 'month').endOf('month').toDate()]
  },
  {
    text: '近3个月',
    value: () => [dayjs().subtract(2, 'month').startOf('month').toDate(), dayjs().endOf('month').toDate()]
  },
  {
    text: '近6个月',
    value: () => [dayjs().subtract(5, 'month').startOf('month').toDate(), dayjs().endOf('month').toDate()]
  },
  {
    text: '今年',
    value: () => [dayjs().startOf('year').toDate(), dayjs().endOf('year').toDate()]
  }
]

const handleDateChange = (val: [Date, Date]) => {
  if (val) {
    startDate.value = dayjs(val[0]).format('YYYY-MM-DD')
    endDate.value = dayjs(val[1]).format('YYYY-MM-DD')
  }
}

onMounted(() => {
  nextTick(() => {
    teacherCourseCountRef.value?.search()
    courseCountRef.value?.search()
  })
})
</script>
