<template>
  <div>
    <el-dialog title="批量新增" width="850px"
               :close-on-click-modal="false"
               :close-on-press-escape="false"
               :visible.sync="dialogVisible"
               :before-close="handleClose" >
      <el-form id="batch-save-course-scheduling-form" ref="form" :model="form" :rules="rules" label-width="100px" inline class="tams-form-container">
        <el-form-item label="教室" prop="classroomId">
          <el-select v-model="form.classroomId" class="tams-form-item">
            <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <br/>
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="currentCourse" value-key="id" class="tams-form-item" @change="courseChange">
            <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="老师" prop="teacherId">
          <el-select v-model="form.teacherId" class="tams-form-item">
            <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="开结课日期" prop="dates">
          <el-date-picker :clearable="false"
                          v-model="form.dates"
                          :picker-options="datesPickerOptions"
                          type="daterange"
                          range-separator="至" style="width: 610px;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="周几上课" prop="weekList">
          <el-checkbox-group v-model="weekList" :min="1" size="small">
            <el-checkbox border v-for="item in weekOptions" :key="item.label" :label="item.label" :checked="item.checked">{{item.name}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <br/>
        <el-form-item label="上课时间" prop="attendTime">
          <el-time-select :clearable="false"
                          style="width: 130px"
                          v-model="form.attendTime"
                          :disabled="!(form.courseId&&form.courseId>0)"
                          :picker-options="timePickerOptions"
                          @change="calcFinishTime">
          </el-time-select>
        </el-form-item>
        <el-form-item label="课程时长">
          <el-input-number style="width: 130px"
                           v-model="courseDuration"
                           :step="$consts.COURSE_DURATION_STEP_MINUTE"
                           :min="0"
                           :max="360"
                           :disabled="!(form.courseId&&form.courseId>0)"
                           @change="calcFinishTime"></el-input-number>
        </el-form-item>
        <el-form-item label="下课时间" prop="finishTime">
          <el-input style="width: 130px" v-model="form.finishTime" :disabled="!(form.courseId&&form.courseId>0)" readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog width="450px" :title="errMsg" :visible.sync="tipDialogVisible">
      <el-alert v-for="(item, index) in errData" :key="index"
                :title="item" type="error" :closable="false"
                style="margin-bottom: 5px;">
      </el-alert>
    </el-dialog>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions } from 'vuex'

export default {
  name: 'BatchSaveCourseScheduling',
  props: {
    visible: {
      type: Boolean
    }
  },
  data () {
    return {
      dialogVisible: false,
      tipDialogVisible: false,
      errMsg: '',
      errData: [],
      classroomData: [],
      courseData: [],
      teacherData: [],
      weekList: [],
      form: {},
      currentCourse: {},
      courseDuration: 0,
      weekOptions: [
        { label: 1, name: '周一' },
        { label: 2, name: '周二' },
        { label: 3, name: '周三' },
        { label: 4, name: '周四' },
        { label: 5, name: '周五' },
        { label: 6, name: '周六' },
        { label: 7, name: '周七' }
      ],
      datesPickerOptions: {
        firstDayOfWeek: 1
      },
      timePickerOptions: {
        start: '07:00',
        step: '00:' + this.$consts.COURSE_DURATION_STEP_MINUTE,
        end: '20:00'
      },
      rules: {
        classroomId: [
          {
            required: true,
            message: '教室不能为空',
            trigger: 'blur'
          }
        ],
        courseId: [
          {
            required: true,
            message: '课程不能为空',
            trigger: 'blur'
          }
        ],
        teacherId: [
          {
            required: true,
            message: '老师不能为空',
            trigger: 'blur'
          }
        ],
        dates: [
          {
            required: true,
            type: 'array',
            min: 2,
            message: '开结课日期不能为空',
            trigger: 'blur'
          }
        ],
        attendTime: [
          {
            required: true,
            message: '上课时间不能为空',
            trigger: 'blur'
          }
        ],
        finishTime: [
          {
            required: true,
            message: '下课时间不能为空',
            trigger: 'blur'
          }
        ]
      },
      submitBtnLoading: false
    }
  },
  methods: {
    ...mapActions(['GetClassroomRefList', 'GetCourseRefList', 'GetTeacherRefList', 'BatchSaveCourseScheduling']),
    init () {
      this.weekList = [1, 2, 3, 4, 5]
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
    },
    resetData () {
      this.$refs.form.resetFields()
      this.form = {}
      this.courseDuration = 0
      this.currentCourse = {}
      this.weekList = [1, 2, 3, 4, 5]
      this.errMsg = ''
      this.errData = []
      this.classroomData = []
      this.courseData = []
      this.teacherData = []
    },
    handleClose (done) {
      this.resetData()
      this.$emit('on-close')
      done()
    },
    close () {
      this.resetData()
      this.$emit('on-close')
      this.dialogVisible = false
    },
    submit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.submitBtnLoading = true
          this.form.startDate = moment(this.form.dates[0]).format('YYYY-MM-DD')
          this.form.endDate = moment(this.form.dates[1]).format('YYYY-MM-DD')
          this.form.weekList = this.weekList
          this.form.attendTime = moment(this.form.attendTime, 'HH:mm').format('HH:mm:ss')
          this.form.finishTime = moment(this.form.finishTime, 'HH:mm').format('HH:mm:ss')
          this.BatchSaveCourseScheduling(this.form).then(() => {
            this.submitBtnLoading = false
            this.resetData()
            this.$emit('on-success')
            this.dialogVisible = false
          }).catch((err) => {
            if (err.code === 100001) {
              this.errMsg = err.msg
              this.errData = err.data
              this.tipDialogVisible = true
            }
            this.form.attendTime = moment(this.form.attendTime, 'HH:mm:ss').format('HH:mm')
            this.form.finishTime = moment(this.form.finishTime, 'HH:mm:ss').format('HH:mm')
            this.submitBtnLoading = false
          })
        }
      })
    },
    courseChange (val) {
      this.form.courseId = val.id
      if (val.duration && val.duration > 0) {
        this.courseDuration = val.duration
        if (this.form.attendTime) {
          this.$set(this.form, 'finishTime', moment(this.form.attendTime, 'HH:mm').add(this.courseDuration, 'm').format('HH:mm'))
        }
      }
    },
    calcFinishTime () {
      if (this.form.attendTime) {
        this.$set(this.form, 'finishTime', moment(this.form.attendTime, 'HH:mm').add(this.courseDuration, 'm').format('HH:mm'))
      }
    }
  },
  watch: {
    visible (val) {
      if (val) {
        this.init()
        this.dialogVisible = val
      }
    }
  }
}
</script>

<style>
#batch-save-course-scheduling-form .el-checkbox {
  color: #606266;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  margin-right: 6px;
  line-height: 28px;
}

#batch-save-course-scheduling-form .el-checkbox.is-bordered.el-checkbox--small {
  padding: 5px 15px 5px 10px;
  border-radius: 3px;
  height: 40px;
}
</style>
