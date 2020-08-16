<template>
  <div>
    <div style="margin-bottom: 10px;">
      <el-date-picker :clearable="false"
                      :picker-options="pickerOptions"
                      v-model="dates"
                      type="daterange"
                      range-separator="至">
      </el-date-picker>
    </div>
    <el-tabs active-name="teacherCourse" type="card">
      <el-tab-pane label="老师上课数" name="teacherCourse">
        <TeacherCourseCountReport :startDate="startDate" :endDate="endDate"></TeacherCourseCountReport>
      </el-tab-pane>
      <el-tab-pane label="课程数" name="course">
        <CourseCountReport :startDate="startDate" :endDate="endDate"></CourseCountReport>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import moment from 'moment'
import TeacherCourseCountReport from '@/views/report/TeacherCourseCountReport'
import CourseCountReport from '@/views/report/CourseCountReport'

export default {
  name: 'Report',
  components: { CourseCountReport, TeacherCourseCountReport },
  computed: {
    startDate () {
      return moment(this.dates[0]).format('YYYY-MM-DD')
    },
    endDate () {
      return moment(this.dates[1]).format('YYYY-MM-DD')
    }
  },
  data () {
    return {
      pickerOptions: {
        firstDayOfWeek: 1,
        shortcuts: [
          {
            text: '本周',
            onClick (picker) {
              picker.$emit('pick', [moment().week(moment().week()).startOf('isoweek'), moment().week(moment().week()).endOf('isoweek')])
            }
          },
          {
            text: '本月',
            onClick (picker) {
              picker.$emit('pick', [moment().month(moment().month()).startOf('month'), moment().month(moment().month()).endOf('month')])
            }
          },
          {
            text: '本年',
            onClick (picker) {
              picker.$emit('pick', [moment().year(moment().year()).startOf('year'), moment().year(moment().year()).endOf('year')])
            }
          }
        ]
      },
      data: [],
      dates: [],
      loading: false
    }
  },
  methods: {
    ...mapActions([]),
    init () {
      this.$set(this, 'dates', [moment().month(moment().month()).startOf('month'), moment().month(moment().month()).endOf('month')])
    },
    search () {

    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style scoped>

</style>
