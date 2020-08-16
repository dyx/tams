<template>
  <el-dialog title="修改" width="790px"
             :close-on-click-modal="false"
             :close-on-press-escape="false"
             :visible.sync="dialogVisible"
             :before-close="handleClose" >
    <el-form ref="form" :model="form" :rules="rules" inline label-width="80px" class="tams-form-container">
      <el-form-item label="日期" prop="date">
        <el-date-picker v-model="form.date"
                        type="date"
                        value-format="yyyy-MM-dd"
                        :clearable="false"
                        :picker-options="datesPickerOptions"
                        class="tams-form-item"></el-date-picker>
      </el-form-item>
      <br/>
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
      <el-form-item label="时间" prop="attendTime">
        <el-time-select :clearable="false"
                        style="width: 210px;"
                        v-model="form.attendTime"
                        :picker-options="pickerOptions"
                        :key="attendTimeSelectKey"
                        @change="calcFinishTime">
        </el-time-select>
      </el-form-item>
      <el-form-item>
        <el-input-number v-model="courseDuration"
                         style="width: 150px;"
                         :step="$consts.COURSE_DURATION_STEP_MINUTE"
                         :min="0"
                         :max="360"
                         @change="calcFinishTime"></el-input-number>
      </el-form-item>
      <el-form-item prop="finishTime">
        <el-input v-model="form.finishTime"
                  readonly
                  style="width: 210px;"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取消</el-button>
      <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { mapActions } from 'vuex'
import moment from 'moment'

export default {
  name: 'UpdateCourseScheduling',
  props: {
    visible: {
      type: Boolean
    },
    id: [String, Number]
  },
  data () {
    return {
      dialogVisible: false,
      classroomData: [],
      courseData: [],
      teacherData: [],
      form: {},
      currentCourse: {},
      courseDuration: 0,
      attendTimeSelectKey: new Date().getTime(),
      datesPickerOptions: {
        firstDayOfWeek: 1
      },
      pickerOptions: {
        start: '07:00',
        step: '00:' + this.$consts.COURSE_DURATION_STEP_MINUTE,
        end: '20:00'
      },
      rules: {
        date: [
          {
            required: true,
            message: '日期不能为空',
            trigger: 'blur'
          }
        ],
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
    ...mapActions(['GetClassroomRefList', 'GetCourseRefList', 'GetTeacherRefList', 'GetCourseSchedulingById', 'UpdateCourseSchedulingById']),
    init () {
      this.attendTimeSelectKey = new Date().getTime()
      Promise.all([
        this.GetClassroomRefList(),
        this.GetCourseRefList(),
        this.GetTeacherRefList()
      ]).then((res) => {
        console.log(res)
        this.classroomData = res[0]
        this.courseData = res[1]
        console.log(this.courseData)
        this.teacherData = res[2]
        this.search()
      }).catch()
    },
    search () {
      this.GetCourseSchedulingById(this.id).then(res => {
        if (res) {
          this.form = res
          this.form.attendTime = moment(this.form.attendTime, 'HH:mm:ss').format('HH:mm')
          this.form.finishTime = moment(this.form.finishTime, 'HH:mm:ss').format('HH:mm')
          this.courseDuration = this.form.duration
          this.currentCourse = {
            id: res.courseId,
            name: res.courseName
          }
        }
      }).catch(() => {
      })
    },
    resetData () {
      this.$refs.form.resetFields()
      this.form = {}
      this.courseDuration = 0
      this.currentCourse = {}
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
          this.form.attendTime = moment(this.form.attendTime, 'HH:mm').format('HH:mm:ss')
          this.form.finishTime = moment(this.form.finishTime, 'HH:mm').format('HH:mm:ss')
          this.UpdateCourseSchedulingById({
            id: this.id,
            data: this.form
          }).then(() => {
            this.submitBtnLoading = false
            this.$refs.form.resetFields()
            this.$emit('on-success')
            this.dialogVisible = false
          }).catch(() => {
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
