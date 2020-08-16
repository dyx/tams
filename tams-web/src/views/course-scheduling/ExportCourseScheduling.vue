<template>
  <el-dialog title="导出课表" width="520px"
             :close-on-click-modal="false"
             :close-on-press-escape="false"
             :visible.sync="dialogVisible"
             :before-close="handleClose">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px" class="tams-form-container">
      <el-form-item label="日期范围" prop="dates">
        <el-date-picker :clearable="false"
                        v-model="form.dates"
                        :picker-options="pickerOptions"
                        type="daterange"
                        range-separator="至"
                        class="form-item">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="工作表名称" prop="sheetNamingType">
        <el-radio v-model="form.sheetNamingType" :label="1">时间段</el-radio>
        <el-radio v-model="form.sheetNamingType" :label="2">第几周</el-radio>
      </el-form-item>
      <el-form-item label="显示周末" prop="isShowWeek">
        <el-radio v-model="form.isShowWeek" :label="1">显示</el-radio>
        <el-radio v-model="form.isShowWeek" :label="0">不显示</el-radio>
      </el-form-item>
      <el-form-item label="指定教室" prop="classroomId">
        <el-select :clearable="true" v-model="currentClassroom" value-key="id" class="form-item">
          <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" class="form-item"></el-input>
      </el-form-item>
      <el-form-item label="文件名" prop="filename">
        <el-input v-model="form.filename" class="form-item"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取消</el-button>
      <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import moment from 'moment'
import { mapActions } from 'vuex'

export default {
  name: 'ExportCourseScheduling',
  props: {
    visible: {
      type: Boolean
    }
  },
  data () {
    return {
      dialogVisible: false,
      form: {},
      classroomData: [],
      currentClassroom: {},
      pickerOptions: {
        firstDayOfWeek: 1
      },
      rules: {
        dates: [
          {
            required: true,
            type: 'array',
            min: 2,
            message: '日期范围不能为空',
            trigger: 'blur'
          }
        ]
      },
      submitBtnLoading: false
    }
  },
  methods: {
    ...mapActions(['GetClassroomRefList']),
    init () {
      this.$set(this.form, 'sheetNamingType', 1)
      this.$set(this.form, 'isShowWeek', 1)
      this.GetClassroomRefList().then(res => {
        if (res) {
          this.classroomData = res
        }
      }).catch(() => {
      })
    },
    resetData () {
      this.$refs.form.resetFields()
      this.form = {}
      this.classroomData = []
      this.currentClassroom = {}
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
          let url = 'course-scheduling/export/excel' +
            '?startDate=' + moment(this.form.dates[0]).format('YYYY-MM-DD') +
            '&endDate=' + moment(this.form.dates[1]).format('YYYY-MM-DD') +
            '&sheetNamingType=' + this.form.sheetNamingType +
            '&isShowWeek=' + this.form.isShowWeek

          if (this.currentClassroom && this.currentClassroom.id) {
            url += '&classroomId=' + this.currentClassroom.id
            url += '&classroomName=' + this.currentClassroom.name
          }
          if (this.form.title) {
            url += '&title=' + this.form.title
          }
          if (this.form.filename) {
            url += '&filename=' + this.form.filename
          }

          window.location.href = url

          this.$emit('on-success')
          this.dialogVisible = false
          this.resetData()
        }
      })
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

<style scoped>
  .form-item {
    width: 300px;
  }
</style>
