<template>
  <el-dialog title="导出课表" width="520px" :close-on-click-modal="false" :close-on-press-escape="false" v-model="dialogVisible" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="90px" class="tams-form-container">
      <el-form-item label="日期范围" prop="dates">
        <el-date-picker :clearable="false" v-model="form.dates" type="daterange" range-separator="至" class="form-item" />
      </el-form-item>
      <el-form-item label="工作表名称" prop="sheetNamingType">
        <el-radio-group v-model="form.sheetNamingType">
          <el-radio :value="1">时间段</el-radio>
          <el-radio :value="2">第几周</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="显示周末" prop="isShowWeek">
        <el-radio-group v-model="form.isShowWeek">
          <el-radio :value="1">显示</el-radio>
          <el-radio :value="0">不显示</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="指定教室" prop="classroomId">
        <el-select :clearable="true" v-model="currentClassroom" value-key="id" class="form-item">
          <el-option v-for="item in classroomData" :key="item.id" :label="item.name" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" class="form-item" />
      </el-form-item>
      <el-form-item label="文件名" prop="filename">
        <el-input v-model="form.filename" class="form-item" />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" :loading="submitBtnLoading" @click="submit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import dayjs from 'dayjs'
import { useClassroomStore } from '@/stores/classroom'

const props = defineProps<{ visible: boolean }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const classroomStore = useClassroomStore()
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})
const classroomData = ref<any[]>([])
const currentClassroom = ref<any>({})
const submitBtnLoading = ref(false)

const rules: FormRules = {
  dates: [{ required: true, type: 'array', min: 2, message: '日期范围不能为空', trigger: 'blur' }]
}

const init = () => {
  form.value.sheetNamingType = 1
  form.value.isShowWeek = 1
  classroomStore.getClassroomRefList().then((res: any) => { if (res) classroomData.value = res }).catch(() => {})
}
const resetData = () => { formRef.value?.resetFields(); form.value = {}; classroomData.value = []; currentClassroom.value = {} }
const handleClose = (done: () => void) => { resetData(); emit('on-close'); done() }
const close = () => { resetData(); emit('on-close'); dialogVisible.value = false }

const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      const baseUrl = (import.meta.env.VITE_API_BASE_URL as string || '').replace(/\/$/, '')
      let url = baseUrl + '/course-scheduling/export/excel'
        + '?startDate=' + dayjs(form.value.dates[0]).format('YYYY-MM-DD')
        + '&endDate=' + dayjs(form.value.dates[1]).format('YYYY-MM-DD')
        + '&sheetNamingType=' + form.value.sheetNamingType
        + '&isShowWeek=' + form.value.isShowWeek

      if (currentClassroom.value && currentClassroom.value.id) {
        url += '&classroomId=' + currentClassroom.value.id
        url += '&classroomName=' + currentClassroom.value.name
      }
      if (form.value.title) url += '&title=' + form.value.title
      if (form.value.filename) url += '&filename=' + form.value.filename

      window.location.href = url
      emit('on-success')
      dialogVisible.value = false
      resetData()
    }
  })
}

watch(() => props.visible, (val) => { if (val) { init(); dialogVisible.value = val } })
</script>

<style scoped>
.form-item { width: 300px; }
</style>
