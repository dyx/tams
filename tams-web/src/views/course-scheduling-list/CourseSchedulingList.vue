<template>
  <el-card shadow="never">
    <template #header>
      <div class="clearfix"><span>排课列表</span></div>
    </template>
    <div id="course-scheduling-list">
      <el-form inline>
        <el-form-item label="日期">
          <el-date-picker v-model="params.dates" size="small" type="daterange" value-format="YYYY-MM-DD" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" style="width: 240px;" />
        </el-form-item>
        <el-form-item label="上课时间">
          <el-time-select size="small" class="form-item" v-model="params.attendTime" :picker-options="timePickerOptions" />
        </el-form-item>
        <el-form-item label="下课时间">
          <el-time-select size="small" class="form-item" v-model="params.finishTime" :picker-options="timePickerOptions" />
        </el-form-item>
        <br />
        <el-form-item label="教室">
          <el-select clearable multiple size="small" class="form-item" v-model="params.classroomIdList">
            <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="——课程">
          <el-select clearable multiple size="small" class="form-item" v-model="params.courseIdList">
            <el-option v-for="item in courseData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="——老师">
          <el-select clearable multiple size="small" class="form-item" v-model="params.teacherIdList">
            <el-option v-for="item in teacherData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
      </el-form>
    </div>
    <div style="float: right; margin: 10px 0;">
      <el-button type="primary" size="small" @click="search">查询</el-button>
      <el-button type="primary" size="small" @click="reset">重置</el-button>
      <el-button type="danger" size="small" :disabled="!(idList.length > 0)" @click="remove">删除</el-button>
    </div>
    <el-table stripe border :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" />
      <el-table-column type="index" label="序号" width="50" />
      <el-table-column prop="date" label="日期" width="110" />
      <el-table-column prop="attendTime" label="上课时间" width="90" />
      <el-table-column prop="finishTime" label="下课时间" width="90" />
      <el-table-column prop="classroomName" label="教室" width="120" />
      <el-table-column prop="courseName" label="课程" width="120" />
      <el-table-column prop="teacherName" label="老师" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessageBox } from 'element-plus'
import dayjs from 'dayjs'
import { useClassroomStore } from '@/stores/classroom'
import { useCourseStore } from '@/stores/course'
import { useTeacherStore } from '@/stores/teacher'
import { useCourseSchedulingStore } from '@/stores/courseScheduling'
import { CONSTS } from '@/utils/consts'

const classroomStore = useClassroomStore()
const courseStore = useCourseStore()
const teacherStore = useTeacherStore()
const csStore = useCourseSchedulingStore()

const data = ref<any[]>([])
const classroomData = ref<any[]>([])
const courseData = ref<any[]>([])
const teacherData = ref<any[]>([])
const params = reactive<any>({})
const idList = ref<number[]>([])
const timePickerOptions = { start: '07:00', step: '00:' + CONSTS.COURSE_DURATION_STEP_MINUTE, end: '20:00' }

const init = () => {
  classroomStore.getClassroomRefList().then((res: any) => { if (res) classroomData.value = res }).catch(() => {})
  courseStore.getCourseRefList().then((res: any) => { if (res) courseData.value = res }).catch(() => {})
  teacherStore.getTeacherRefList().then((res: any) => { if (res) teacherData.value = res }).catch(() => {})
  params.startDate = dayjs().startOf('month').format('YYYY-MM-DD')
  params.endDate = dayjs().endOf('month').format('YYYY-MM-DD')
  params.dates = [params.startDate, params.endDate]
  search()
}

const search = () => {
  if (params.dates && params.dates.length > 0) {
    params.startDate = params.dates[0]
    params.endDate = params.dates[1]
  }
  csStore.getCourseSchedulingList(params).then((res: any) => { if (res) data.value = res }).catch(() => {})
}

const reset = () => {
  Object.keys(params).forEach(key => delete params[key])
  params.startDate = dayjs().startOf('month').format('YYYY-MM-DD')
  params.endDate = dayjs().endOf('month').format('YYYY-MM-DD')
  params.dates = [params.startDate, params.endDate]
  search()
}

const remove = () => {
  if (idList.value.length > 0) {
    ElMessageBox.confirm('确定要删除这 ' + idList.value.length + ' 条数据么', '提示', {
      confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
    }).then(() => {
      csStore.removeCourseSchedulingByIdList(idList.value).then(() => search()).catch(() => {})
    }).catch(() => {})
  }
}

const handleSelectionChange = (val: any[]) => {
  idList.value = val.map(item => item.id)
}

onMounted(() => init())
</script>

<style>
#course-scheduling-list .el-form-item { margin-bottom: 0; }
#course-scheduling-list .form-item { width: 240px; }
</style>
