<template>
  <el-card shadow="never">
    <div slot="header" class="clearfix">
      <span>排课列表</span>
    </div>
    <div id="course-scheduling-list">
      <el-form inline>
        <el-form-item label="日期">
          <el-date-picker v-model="params.dates"
                          size="mini"
                          :picker-options="datePickerOptions"
                          type="daterange"
                          value-format="yyyy-MM-dd"
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"
                          style="width: 240px;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-time-select size="mini" class="form-item" v-model="params.attendTime" :picker-options="timePickerOptions">
          </el-time-select>
        </el-form-item>
        <el-form-item label="下课时间">
          <el-time-select size="mini" class="form-item" v-model="params.finishTime" :picker-options="timePickerOptions">
          </el-time-select>
        </el-form-item>
        <br/>
        <el-form-item label="教室">
          <el-select ref="classroomSelect" clearable multiple size="mini" class="form-item" v-model="params.classroomIdList">
            <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="——课程">
          <el-select ref="courseSelect" clearable multiple size="mini" class="form-item" v-model="params.courseIdList">
            <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="——老师">
          <el-select ref="teacherSelect" clearable multiple size="mini" class="form-item" v-model="params.teacherIdList">
            <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div style="float: right; margin: 10px 0;">
      <el-button type="primary" size="small" @click="search">查询</el-button>
      <el-button type="primary" size="small" @click="reset">重置</el-button>
      <el-button slot="reference" type="danger" size="small" :disabled="!(idList && idList.length > 0)" @click="remove">删除</el-button>
    </div>
    <el-table stripe border :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column type="index" label="序号" width="50"></el-table-column>
      <el-table-column prop="date" label="日期" width="110"></el-table-column>
      <el-table-column prop="attendTime" label="上课时间" width="90"></el-table-column>
      <el-table-column prop="finishTime" label="下课时间" width="90"></el-table-column>
      <el-table-column prop="classroomName" label="教室" width="120"></el-table-column>
      <el-table-column prop="courseName" label="课程" width="120"></el-table-column>
      <el-table-column prop="teacherName" label="老师"></el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import { mapActions } from 'vuex'
import moment from 'moment'

export default {
  name: 'CourseSchedulingList',
  data () {
    return {
      currentVisible: false,
      data: [],
      classroomData: [],
      courseData: [],
      teacherData: [],
      params: {},
      idList: [],
      datePickerOptions: {
        firstDayOfWeek: 1
      },
      timePickerOptions: {
        start: '07:00',
        step: '00:' + this.$consts.COURSE_DURATION_STEP_MINUTE,
        end: '20:00'
      }
    }
  },
  methods: {
    ...mapActions(['GetClassroomRefList', 'GetCourseRefList', 'GetTeacherRefList', 'GetCourseSchedulingList', 'RemoveCourseSchedulingByIdList']),
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

      this.params.startDate = moment().startOf('month').format('YYYY-MM-DD')
      this.params.endDate = moment().endOf('month').format('YYYY-MM-DD')
      this.$set(this.params, 'dates', [this.params.startDate, this.params.endDate])
      this.search()
    },
    search () {
      if (this.params.dates && this.params.dates.length > 0) {
        this.params.startDate = this.params.dates[0]
        this.params.endDate = this.params.dates[1]
      }
      this.GetCourseSchedulingList(this.params).then(res => {
        if (res) {
          this.data = res
        }
      }).catch(() => {
      })
    },
    reset () {
      this.params = {}
      this.params.startDate = moment().startOf('month').format('YYYY-MM-DD')
      this.params.endDate = moment().endOf('month').format('YYYY-MM-DD')
      this.$set(this.params, 'dates', [this.params.startDate, this.params.endDate])
      this.search()
    },
    remove () {
      if (this.idList && this.idList.length > 0) {
        this.$confirm('确定要删除这 ' + this.idList.length + ' 条数据么', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.RemoveCourseSchedulingByIdList(this.idList).then(res => {
            this.search()
          }).catch()
        }).catch(() => {})
      }
    },
    handleSelectionChange (val) {
      this.idList = []
      if (val && val.length > 0) {
        val.forEach(item => {
          this.idList.push(item.id)
        })
      }
    }
  },
  mounted () {
    this.init()
  }
}
</script>

<style>
#course-scheduling-list .el-form-item {
  margin-bottom: 0;
}
#course-scheduling-list .form-item {
  width: 240px;
}
</style>
