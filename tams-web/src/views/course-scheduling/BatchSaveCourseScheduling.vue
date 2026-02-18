<template>
  <div>
    <el-dialog title="批量新增" width="850px" :close-on-click-modal="false" :close-on-press-escape="false" v-model="dialogVisible" :before-close="handleClose">
      <el-form id="batch-save-course-scheduling-form" ref="formRef" :model="form" :rules="rules" label-width="100px" inline class="tams-form-container">
        <el-form-item label="教室" prop="classroomId">
          <el-select v-model="form.classroomId" class="tams-form-item">
            <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <br />
        <el-form-item label="课程" prop="courseId">
          <el-select v-model="currentCourse" value-key="id" class="tams-form-item" @change="courseChange">
            <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="老师" prop="teacherId">
          <el-select v-model="form.teacherId" class="tams-form-item">
            <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="开结课日期" prop="dates">
          <el-date-picker :clearable="false" v-model="form.dates" type="daterange" range-separator="至" style="width: 610px;" />
        </el-form-item>
        <el-form-item label="周几上课" prop="weekList">
          <el-checkbox-group v-model="weekList" :min="1" size="small">
            <el-checkbox border v-for="item in weekOptions" :key="item.label" :value="item.label">{{ item.name }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <br />
        <el-form-item label="上课时间" prop="attendTime">
          <el-time-select :clearable="false" style="width: 130px" v-model="form.attendTime" :disabled="!(form.courseId && form.courseId > 0)" :picker-options="timePickerOptions" @change="calcFinishTime" />
        </el-form-item>
        <el-form-item label="课程时长">
          <el-input-number style="width: 130px" v-model="courseDuration" :step="CONSTS.COURSE_DURATION_STEP_MINUTE" :min="0" :max="360" :disabled="!(form.courseId && form.courseId > 0)" @change="calcFinishTime" />
        </el-form-item>
        <el-form-item label="下课时间" prop="finishTime">
          <el-input style="width: 130px" v-model="form.finishTime" :disabled="!(form.courseId && form.courseId > 0)" readonly />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="close">取消</el-button>
          <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
        </div>
      </template>
    </el-dialog>
    <el-dialog width="450px" :title="errMsg" v-model="tipDialogVisible">
      <el-alert v-for="(item, index) in errData" :key="index" :title="item" type="error" :closable="false" style="margin-bottom: 5px;" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import dayjs from 'dayjs'
import { useClassroomStore } from '@/stores/classroom'
import { useCourseStore } from '@/stores/course'
import { useTeacherStore } from '@/stores/teacher'
import { useCourseSchedulingStore } from '@/stores/courseScheduling'
import { CONSTS } from '@/utils/consts'

const props = defineProps<{ visible: boolean }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const classroomStore = useClassroomStore()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const csStore = useCourseSchedulingStore()

const dialogVisible = ref(false)
const tipDialogVisible = ref(false)
const errMsg = ref('')
const errData = ref<string[]>([])
const formRef = ref<FormInstance>()
const form = ref<any>({})
const classroomData = ref<any[]>([])
const courseData = ref<any[]>([])
const teacherData = ref<any[]>([])
const weekList = ref<number[]>([])
const currentCourse = ref<any>({})
const courseDuration = ref(0)
const submitBtnLoading = ref(false)

const weekOptions = [
  { label: 1, name: '周一' }, { label: 2, name: '周二' }, { label: 3, name: '周三' },
  { label: 4, name: '周四' }, { label: 5, name: '周五' }, { label: 6, name: '周六' }, { label: 7, name: '周七' }
]

const timePickerOptions = { start: '07:00', step: '00:' + CONSTS.COURSE_DURATION_STEP_MINUTE, end: '20:00' }
const rules: FormRules = {
  classroomId: [{ required: true, message: '教室不能为空', trigger: 'blur' }],
  courseId: [{ required: true, message: '课程不能为空', trigger: 'blur' }],
  teacherId: [{ required: true, message: '老师不能为空', trigger: 'blur' }],
  dates: [{ required: true, type: 'array', min: 2, message: '开结课日期不能为空', trigger: 'blur' }],
  attendTime: [{ required: true, message: '上课时间不能为空', trigger: 'blur' }],
  finishTime: [{ required: true, message: '下课时间不能为空', trigger: 'blur' }]
}

const init = () => {
  weekList.value = [1, 2, 3, 4, 5]
  classroomStore.getClassroomRefList().then((res: any) => { if (res) classroomData.value = res }).catch(() => {})
  courseStore.getCourseRefList().then((res: any) => { if (res) courseData.value = res }).catch(() => {})
  teacherStore.getTeacherRefList().then((res: any) => { if (res) teacherData.value = res }).catch(() => {})
}

const resetData = () => {
  formRef.value?.resetFields(); form.value = {}
  courseDuration.value = 0; currentCourse.value = {}
  weekList.value = [1, 2, 3, 4, 5]; errMsg.value = ''; errData.value = []
  classroomData.value = []; courseData.value = []; teacherData.value = []
}
const handleClose = (done: () => void) => { resetData(); emit('on-close'); done() }
const close = () => { resetData(); emit('on-close'); dialogVisible.value = false }

const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      submitBtnLoading.value = true
      form.value.startDate = dayjs(form.value.dates[0]).format('YYYY-MM-DD')
      form.value.endDate = dayjs(form.value.dates[1]).format('YYYY-MM-DD')
      form.value.weekList = weekList.value
      form.value.attendTime = dayjs(form.value.attendTime, 'HH:mm').format('HH:mm:ss')
      form.value.finishTime = dayjs(form.value.finishTime, 'HH:mm').format('HH:mm:ss')
      csStore.batchSaveCourseScheduling(form.value).then(() => {
        submitBtnLoading.value = false; resetData(); emit('on-success'); dialogVisible.value = false
      }).catch((err: any) => {
        if (err.code === 100001) { errMsg.value = err.msg; errData.value = err.data; tipDialogVisible.value = true }
        form.value.attendTime = dayjs(form.value.attendTime, 'HH:mm:ss').format('HH:mm')
        form.value.finishTime = dayjs(form.value.finishTime, 'HH:mm:ss').format('HH:mm')
        submitBtnLoading.value = false
      })
    }
  })
}

const courseChange = (val: any) => {
  form.value.courseId = val.id
  if (val.duration && val.duration > 0) {
    courseDuration.value = val.duration
    if (form.value.attendTime) {
      form.value.finishTime = dayjs(form.value.attendTime, 'HH:mm').add(courseDuration.value, 'minute').format('HH:mm')
    }
  }
}

const calcFinishTime = () => {
  if (form.value.attendTime) {
    form.value.finishTime = dayjs(form.value.attendTime, 'HH:mm').add(courseDuration.value, 'minute').format('HH:mm')
  }
}

watch(() => props.visible, (val) => { if (val) { init(); dialogVisible.value = val } })
</script>

<style>
#batch-save-course-scheduling-form .el-checkbox {
  color: #606266; font-weight: 500; font-size: 14px; cursor: pointer;
  -webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; user-select: none;
  margin-right: 6px; line-height: 28px;
}
#batch-save-course-scheduling-form .el-checkbox.is-bordered.el-checkbox--small {
  padding: 5px 15px 5px 10px; border-radius: 3px; height: 40px;
}
</style>
