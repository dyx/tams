<template>
  <div id="course-scheduling">
    <div>
      <el-row>
        <el-col :span="20">
          <el-form inline>
            <el-form-item label="教室：">
              <el-select ref="classroomSelect" clearable multiple size="mini" style="width: 250px;" v-model="params.classroomIdList" @change="search">
                <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="课程：">
              <el-select ref="courseSelect" clearable multiple size="mini" style="width: 250px;" v-model="params.courseIdList" @change="search">
                <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="老师：">
              <el-select ref="teacherSelect" clearable multiple size="mini" style="width: 250px;" v-model="params.teacherIdList" @change="search">
                <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" size="small" style="float: right" @click="exportCourseSchedulingVisible=true">导出</el-button>
          <el-button type="primary" size="small" style="float: right; margin-right: 10px;" @click="saveBatchCourseSchedulingVisible=true">排课</el-button>
        </el-col>
      </el-row>
      <FullCalendar ref="fullCalendar" :options="calendarOptions"></FullCalendar>
    </div>
    <SaveCourseScheduling :visible="saveCourseSchedulingVisible"
                          :date="currentDate"
                          :attendTime="currentAttendTime"
                          @on-close="saveCourseSchedulingVisible=false"
                          @on-success="saveSuccess">
    </SaveCourseScheduling>
    <ViewCourseScheduling :visible="viewCourseSchedulingVisible"
                          :id="id"
                          @on-success="updateSuccess"
                          @on-close="viewCourseSchedulingVisible=false"></ViewCourseScheduling>
    <BatchSaveCourseScheduling :visible="saveBatchCourseSchedulingVisible"
                               @on-close="saveBatchCourseSchedulingVisible=false"
                               @on-success="saveBatchSuccess">
    </BatchSaveCourseScheduling>
    <ExportCourseScheduling :visible="exportCourseSchedulingVisible"
                            @on-close="exportCourseSchedulingVisible=false"
                            @on-success="exportCourseSchedulingVisible=false">
    </ExportCourseScheduling>
  </div>
</template>
<script>
import FullCalendar from '@fullcalendar/vue'
import dayGridPlugin from '@fullcalendar/daygrid'
import listPlugin from '@fullcalendar/list'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import moment from 'moment'
import { mapActions } from 'vuex'
import SaveCourseScheduling from '@/views/course-scheduling/SaveCourseScheduling'
import ViewCourseScheduling from '@/views/course-scheduling/ViewCourseScheduling'
import BatchSaveCourseScheduling from '@/views/course-scheduling/BatchSaveCourseScheduling'
import ExportCourseScheduling from '@/views/course-scheduling/ExportCourseScheduling'

export default {
  name: 'CourseScheduling',
  components: {
    ExportCourseScheduling,
    BatchSaveCourseScheduling,
    ViewCourseScheduling,
    SaveCourseScheduling,
    FullCalendar
  },
  data () {
    return {
      courseCountObj: {},
      weekList: [],
      classroomData: [],
      courseData: [],
      teacherData: [],
      params: {},
      saveCourseSchedulingVisible: false,
      viewCourseSchedulingVisible: false,
      saveBatchCourseSchedulingVisible: false,
      exportCourseSchedulingVisible: false,
      id: 0,
      currentDate: '',
      currentAttendTime: '',
      calendarOptions: {
        plugins: [dayGridPlugin, listPlugin, timeGridPlugin, interactionPlugin],
        initialView: 'timeGridWeek',
        locale: 'zh',
        // 每周开始是周几，Sunday=0, Monday=1
        firstDay: '1',
        // 时间轴间距
        slotMinTime: '07:00',
        slotMaxTime: '21:00',
        slotDuration: '00:' + this.$consts.COURSE_DURATION_STEP_MINUTE + ':00',
        // 是否显示第几周
        // weekNumbers: true,
        // weekText: '周',
        // 是否显示当前时间标记
        nowIndicator: true,
        // 是否显示全天插槽
        allDaySlot: false,
        // 日期否可点击
        navLinks: true,
        // 月视图，是否为指定周数高度，true 6周高度
        fixedWeekCount: false,
        // 月视图，是否显示非本月日期
        showNonCurrentDates: false,
        // 月视图，限制每天显示最大事件数，不包括+more链接，false 全部显示，true 限制为日单元格的高度，number 限制为指定行数高度
        dayMaxEvents: true,
        // 与dayMaxEvents类似，区别为包括+more链接
        // dayMaxEventRows: true,
        // 是否可拖拽
        editable: true,
        events: [],
        headerToolbar: {
          start: 'prev,next today',
          center: 'title',
          end: 'timeGridWeek,dayGridMonth'
        },
        buttonText: {
          today: '今天',
          month: '月',
          week: '周',
          day: '日',
          list: '周列表'
        },
        slotLabelFormat: {
          hour: 'numeric',
          minute: '2-digit',
          hour12: false
        },
        eventTimeFormat: {
          hour: 'numeric',
          minute: '2-digit',
          hour12: false
        },
        datesSet: this.datesSet,
        dayHeaderContent: this.dayHeaderContent,
        eventClick: this.handleEventClick,
        dateClick: this.handleDateClick,
        // 当事件拖动时触发
        eventDragStart: this.eventDragStart,
        // 当事件拖动停止时触发
        eventDragStop: this.eventDragStop,
        // 当拖动停止并且事件移动到不同的日子/时间时触发
        eventDrop: this.eventDrop,
        // 当外部可拖动元素或来自另一个日历的事件被拖放到该日历中时调用
        drop: this.drop,
        // 当带有关联事件数据的外部可拖动元素拖放到日历中时调用，或来自另一个日历的事件
        eventReceive: this.eventReceive,
        // 当一个日历上的事件即将拖放到另一个日历上时触发
        eventLeave: this.eventLeave,
        // 开始缩放时触发
        eventResizeStart: this.eventResizeStart,
        // 停止缩放时触发
        eventResizeStop: this.eventResizeStop,
        // 当缩放停止且事件持续时间发生更改时触发
        eventResize: this.eventResize
      }
    }
  },
  methods: {
    ...mapActions(['GetClassroomRefList', 'GetCourseRefList', 'GetTeacherRefList', 'GetCourseSchedulingList', 'UpdateCourseSchedulingTimeById', 'GetCourseSchedulingCourseCount']),
    init () {
      this.GetClassroomRefList().then(res => {
        if (res) {
          this.classroomData = res
        }
      }).catch(() => {
      })
      this.GetCourseRefList().then(res => {
        if (res) {
          this.courseData = res
        }
      }).catch(() => {
      })
      this.GetTeacherRefList().then(res => {
        if (res) {
          this.teacherData = res
        }
      }).catch(() => {
      })
      this.search()
    },
    search () {
      this.params.startDate = moment(this.$refs.fullCalendar.getApi().view.currentStart).format('YYYY-MM-DD')
      this.params.endDate = moment(this.$refs.fullCalendar.getApi().view.currentEnd).endOf('month').format('YYYY-MM-DD')
      this.GetCourseSchedulingList(this.params).then(res => {
        if (res) {
          this.calendarOptions.events = []
          res.forEach(item => {
            this.calendarOptions.events.push({
              id: item.id,
              title: item.courseName + ' ' + item.classroomName + ' ' + item.teacherName,
              start: item.date + ' ' + item.attendTime,
              end: item.date + ' ' + item.finishTime,
              extendedProps: item,
              backgroundColor: item.backgroundColor
            })
          })
        }
      }).catch(() => {
      })
      this.GetCourseSchedulingCourseCount(this.params).then(res => {
        if (res) {
          this.courseCountObj = res
        }
      }).catch(() => {
      })
    },
    saveSuccess () {
      this.search()
      this.saveCourseSchedulingVisible = false
    },
    saveBatchSuccess () {
      this.search()
      this.saveBatchCourseSchedulingVisible = false
    },
    updateSuccess () {
      this.search()
      this.viewCourseSchedulingVisible = false
    },
    datesSet (info) {
      this.search()
    },
    dayHeaderContent (info) {
      if (info.view.type === 'dayGridMonth') {
        return { html: `<div class="fc-scrollgrid-sync-inner"><a class="fc-col-header-cell-cushion">${info.text}</a></div>` }
      } else if (info.view.type === 'timeGridWeek') {
        return {
          html: `<div class="fc-scrollgrid-sync-inner">
                    <a class="fc-col-header-cell-cushion"
                        data-navlink="{&quot;date&quot;:&quot;${moment(info.date).format('YYYY-MM-DD')}&quot;,&quot;type&quot;:&quot;day&quot;}" tabindex="0">${info.text}</a>
                    <span id="tams-course-count-${moment(info.date).format('YYYY-MM-DD')}" style="cursor: default;">${this.getCourseCount(info.date)}</span>节课
                </div>`
        }
      } else if (info.view.type === 'timeGridDay') {
        return {
          html: `<div class="fc-scrollgrid-sync-inner">
                     <a class="fc-col-header-cell-cushion">${info.text}</a>
                     <span id="tams-course-count-${moment(info.date).format('YYYY-MM-DD')}" style="cursor: default;">${this.getCourseCount(info.date)}</span>节课
                 </div>`
        }
      } else if (info.view.type === 'listWeek') {
        return {
          html: `<a class="fc-list-day-text"
                      data-navlink="${info.navLinkData.replace(/"/g, '&quot;')}" tabindex="0">${info.text}</a>
                 <a class="fc-list-day-side-text"
                      data-navlink="${info.navLinkData.replace(/"/g, '&quot;')}" tabindex="0">${info.sideText}</a>`
        }
      }
      return 'unknown view type'
    },
    getCourseCount (date) {
      const count = this.courseCountObj[moment(date).format('YYYY-MM-DD')]
      return count || 0
    },
    handleEventClick (info) {
      this.id = info.event.id
      this.viewCourseSchedulingVisible = true
    },
    handleDateClick (info) {
      this.currentDate = moment(info.date).format('YYYY-MM-DD')
      const infoDate = moment(info.date)
      if (infoDate.hour() > 0) {
        this.currentAttendTime = infoDate.format('HH:mm')
      }
      this.saveCourseSchedulingVisible = true
    },
    eventDragStart (info) {
      console.log('eventDragStart', info)
    },
    eventDragStop (info) {
      console.log('eventDragStop', info)
    },
    eventDrop (info) {
      console.log('eventDrop', info)
      this.UpdateCourseSchedulingTimeById({
        id: info.event.id,
        data: {
          date: moment(info.event.start).format('YYYY-MM-DD'),
          attendTime: moment(info.event.start).format('HH:mm:ss'),
          finishTime: moment(info.event.end).format('HH:mm:ss')
        }
      }).then(res => {
        this.search()
      }).catch(() => {
        this.search()
      })
    },
    drop (info) {
      console.log('drop', info)
    },
    eventReceive (info) {
      console.log('eventReceive', info)
    },
    eventLeave (info) {
      console.log('eventLeave', info)
    },
    eventResizeStart (info) {
      console.log('eventResizeStart', info)
    },
    eventResizeStop (info) {
      console.log('eventResizeStop', info)
    },
    eventResize (info) {
      console.log('eventResize', info)
      this.UpdateCourseSchedulingTimeById({
        id: info.event.id,
        data: {
          date: moment(info.event.start).format('YYYY-MM-DD'),
          attendTime: moment(info.event.start).format('HH:mm:ss'),
          finishTime: moment(info.event.end).format('HH:mm:ss')
        }
      }).then(res => {
        this.search()
      }).catch(() => {
        this.search()
      })
    }
  },
  mounted () {
    this.init()
  }
}
</script>
