<template>
  <el-card shadow="never">
    <template #header>
      <div class="clearfix">
        <span>排课日历</span>
      </div>
    </template>
    <el-row class="tams-search-container">
      <el-col :span="18">
        <el-select clearable v-model="params.classroomId" size="small" class="tams-search-item" placeholder="教室" @change="search">
          <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select clearable v-model="params.courseId" size="small" class="tams-search-item" placeholder="课程" @change="search">
          <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
        <el-select clearable v-model="params.teacherId" size="small" class="tams-search-item" placeholder="老师" @change="search">
          <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-col>
      <el-col :span="6" style="text-align: right;">
        <el-button type="primary" size="small" @click="saveCourseSchedulingVisible = true">新增</el-button>
        <el-button type="primary" size="small" @click="batchSaveCourseSchedulingVisible = true">批量新增</el-button>
        <el-button type="success" size="small" @click="exportCourseSchedulingVisible = true">导出</el-button>
      </el-col>
    </el-row>
    <FullCalendar ref="calendarRef" :options="calendarOptions" />
    <SaveCourseScheduling :visible="saveCourseSchedulingVisible" :date="currentDate" :classroomId="currentClassroomId" @on-close="saveCourseSchedulingVisible = false" @on-success="saveSuccess" />
    <BatchSaveCourseScheduling :visible="batchSaveCourseSchedulingVisible" @on-close="batchSaveCourseSchedulingVisible = false" @on-success="batchSaveSuccess" />
    <ExportCourseScheduling :visible="exportCourseSchedulingVisible" @on-close="exportCourseSchedulingVisible = false" @on-success="exportCourseSchedulingVisible = false" />
    <ViewCourseScheduling :visible="viewCourseSchedulingVisible" :id="currentId" @on-close="viewCourseSchedulingVisible = false" @on-success="viewSuccess" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import listPlugin from '@fullcalendar/list'
import interactionPlugin from '@fullcalendar/interaction'
import dayjs from 'dayjs'
import { useClassroomStore } from '@/stores/classroom'
import { useCourseStore } from '@/stores/course'
import { useTeacherStore } from '@/stores/teacher'
import { useCourseSchedulingStore } from '@/stores/courseScheduling'
import SaveCourseScheduling from '@/views/course-scheduling/SaveCourseScheduling.vue'
import BatchSaveCourseScheduling from '@/views/course-scheduling/BatchSaveCourseScheduling.vue'
import ExportCourseScheduling from '@/views/course-scheduling/ExportCourseScheduling.vue'
import ViewCourseScheduling from '@/views/course-scheduling/ViewCourseScheduling.vue'

const classroomStore = useClassroomStore()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const csStore = useCourseSchedulingStore()

const calendarRef = ref<InstanceType<typeof FullCalendar>>()
const classroomData = ref<any[]>([])
const courseData = ref<any[]>([])
const teacherData = ref<any[]>([])
const params = reactive<any>({})

const saveCourseSchedulingVisible = ref(false)
const batchSaveCourseSchedulingVisible = ref(false)
const exportCourseSchedulingVisible = ref(false)
const viewCourseSchedulingVisible = ref(false)
const currentDate = ref('')
const currentClassroomId = ref<number | string>('')
const currentId = ref<number | string>('')

const calendarOptions = reactive({
  plugins: [dayGridPlugin, listPlugin, timeGridPlugin, interactionPlugin],
  initialView: 'dayGridMonth',
  locale: 'zh-cn',
  firstDay: 1,
  headerToolbar: {
    left: 'prev,today,next',
    center: 'title',
    right: 'dayGridMonth,timeGridWeek,listWeek'
  },
  buttonText: {
    today: '今天',
    month: '月',
    week: '周',
    list: '列表'
  },
  allDayText: '全天',
  editable: true,
  events: [] as any[],
  eventClick: handleEventClick,
  dateClick: handleDateClick,
  eventDrop: handleEventDrop,
  eventResize: handleEventResize,
  datesSet: handleDatesSet,
  dayHeaderContent: (arg: any) => {
    const weekdays = ['日', '一', '二', '三', '四', '五', '六']
    return weekdays[arg.dow]
  }
})

function handleEventClick(info: any) {
  currentId.value = info.event.id
  viewCourseSchedulingVisible.value = true
}

function handleDateClick(info: any) {
  currentDate.value = info.dateStr
  currentClassroomId.value = ''
  saveCourseSchedulingVisible.value = true
}

function handleEventDrop(info: any) {
  const event = info.event
  const data: any = {
    date: dayjs(event.start).format('YYYY-MM-DD'),
    attendTime: dayjs(event.start).format('HH:mm:ss'),
    finishTime: dayjs(event.end).format('HH:mm:ss')
  }
  csStore.updateCourseSchedulingTimeById(event.id, data).catch(() => {
    info.revert()
  })
}

function handleEventResize(info: any) {
  const event = info.event
  const data: any = {
    date: dayjs(event.start).format('YYYY-MM-DD'),
    attendTime: dayjs(event.start).format('HH:mm:ss'),
    finishTime: dayjs(event.end).format('HH:mm:ss')
  }
  csStore.updateCourseSchedulingTimeById(event.id, data).catch(() => {
    info.revert()
  })
}

function handleDatesSet(info: any) {
  params.startDate = dayjs(info.start).format('YYYY-MM-DD')
  params.endDate = dayjs(info.end).format('YYYY-MM-DD')
  search()
}

const search = () => {
  csStore.getCourseSchedulingList(params).then((res: any) => {
    if (res) {
      const events = res.map((item: any) => ({
        id: item.id,
        title: (item.classroomName ? item.classroomName + ' ' : '') + item.courseName + ' ' + item.teacherName,
        start: item.date + 'T' + item.attendTime,
        end: item.date + 'T' + item.finishTime,
        backgroundColor: item.backgroundColor || '#409EFF',
        borderColor: item.backgroundColor || '#409EFF'
      }))
      calendarOptions.events = events
    }
  }).catch(() => {})
}

const init = () => {
  classroomStore.getClassroomRefList().then((res: any) => { if (res) classroomData.value = res }).catch(() => {})
  courseStore.getCourseRefList().then((res: any) => { if (res) courseData.value = res }).catch(() => {})
  teacherStore.getTeacherRefList().then((res: any) => { if (res) teacherData.value = res }).catch(() => {})
}

const saveSuccess = () => { search(); saveCourseSchedulingVisible.value = false }
const batchSaveSuccess = () => { search(); batchSaveCourseSchedulingVisible.value = false }
const viewSuccess = () => { search(); viewCourseSchedulingVisible.value = false }

onMounted(() => { init() })
</script>
