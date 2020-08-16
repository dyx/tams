<template>
  <div>
    <el-dialog title="详情" width="790px"
               :visible.sync="dialogVisible"
               :before-close="handleClose" >
      <el-form ref="form" :model="form" inline label-width="80px" class="tams-form-container">
        <el-form-item label="日期">
          <el-input v-model="form.date" class="tams-form-item" readonly></el-input>
        </el-form-item>
        <el-form-item label="教室">
          <el-input v-model="form.classroomName" class="tams-form-item" readonly></el-input>
        </el-form-item>
        <el-form-item label="课程">
          <el-input v-model="form.courseName" class="tams-form-item" readonly></el-input>
        </el-form-item>
        <el-form-item label="老师">
          <el-input v-model="form.teacherName" class="tams-form-item" readonly></el-input>
        </el-form-item>
        <el-form-item label="上课时间">
          <el-input v-model="form.attendTime" class="tams-form-item" readonly></el-input>
        </el-form-item>
        <el-form-item label="下课时间">
          <el-input v-model="form.finishTime" class="tams-form-item" readonly></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-popconfirm title="确定要删除吗？" @onConfirm="remove">
          <el-button type="danger" slot="reference" style="margin-right: 10px;">删除</el-button>
        </el-popconfirm>
        <el-button type="primary" @click="updateCourseSchedulingVisible=true">修改</el-button>
      </div>
    </el-dialog>
    <UpdateCourseScheduling :visible="updateCourseSchedulingVisible"
                            :id="id"
                            @on-success="updateSuccess"
                            @on-close="updateCourseSchedulingVisible=false"></UpdateCourseScheduling>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions } from 'vuex'
import UpdateCourseScheduling from '@/views/course-scheduling/UpdateCourseScheduling'

export default {
  name: 'ViewCourseScheduling',
  components: { UpdateCourseScheduling },
  props: {
    visible: {
      type: Boolean
    },
    id: [Number, String]
  },
  data () {
    return {
      dialogVisible: false,
      updateCourseSchedulingVisible: false,
      form: {}
    }
  },
  methods: {
    ...mapActions(['GetCourseSchedulingById', 'RemoveCourseSchedulingById']),
    search () {
      this.GetCourseSchedulingById(this.id).then((res) => {
        this.form = res
        this.form.attendTime = moment(res.attendTime, 'HH:mm:ss').format('HH:mm')
        this.form.finishTime = moment(res.finishTime, 'HH:mm:ss').format('HH:mm')
      }).catch(() => {
      })
    },
    remove () {
      this.RemoveCourseSchedulingById(this.id).then((res) => {
        this.$emit('on-success')
        this.dialogVisible = false
      }).catch(() => {
      })
    },
    handleClose (done) {
      this.$refs.form.resetFields()
      this.$emit('on-close')
      done()
    },
    close () {
      this.$refs.form.resetFields()
      this.$emit('on-close')
      this.dialogVisible = false
    },
    updateSuccess () {
      this.$emit('on-success')
      this.search()
      this.updateCourseSchedulingVisible = false
    }
  },
  watch: {
    visible (val) {
      if (val) {
        this.search()
        this.dialogVisible = val
      }
    }
  }
}
</script>
