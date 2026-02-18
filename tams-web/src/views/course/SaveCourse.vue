<template>
  <el-dialog title="新增" width="400px" :close-on-click-modal="false" :close-on-press-escape="false" v-model="dialogVisible" :before-close="handleClose">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" class="tams-form-container">
      <el-form-item label="名称" prop="name">
        <el-input v-model="form.name" class="tams-form-item" />
      </el-form-item>
      <el-form-item label="时长(分钟)" prop="duration">
        <el-input-number v-model="form.duration" class="tams-form-item" :step="CONSTS.COURSE_DURATION_STEP_MINUTE" :min="0" :max="360" />
      </el-form-item>
      <el-form-item label="背景颜色" prop="backgroundColor">
        <el-select v-model="form.backgroundColor" class="tams-form-item">
          <el-option v-for="item in colorData" :key="item.color" :label="item.name" :value="item.color">
            <span :style="{ float: 'left', backgroundColor: item.color, width: '20px', height: '20px', borderRadius: '3px', marginRight: '8px', verticalAlign: 'middle' }" />
            <span>{{ item.name }}</span>
          </el-option>
        </el-select>
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
import { useCourseStore } from '@/stores/course'
import { useColorStore } from '@/stores/color'
import { CONSTS } from '@/utils/consts'

const props = defineProps<{ visible: boolean }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const courseStore = useCourseStore()
const colorStore = useColorStore()
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})
const colorData = ref<any[]>([])
const submitBtnLoading = ref(false)

const rules: FormRules = {
  name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
  duration: [{ required: true, message: '时长不能为空', trigger: 'blur' }]
}

const init = () => {
  colorStore.getColorList().then((res: any) => { if (res) colorData.value = res }).catch(() => {})
}
const resetData = () => { formRef.value?.resetFields(); form.value = {}; colorData.value = [] }
const handleClose = (done: () => void) => { resetData(); emit('on-close'); done() }
const close = () => { resetData(); emit('on-close'); dialogVisible.value = false }
const submit = () => {
  formRef.value?.validate((valid) => {
    if (valid) {
      submitBtnLoading.value = true
      courseStore.saveCourse(form.value).then(() => {
        submitBtnLoading.value = false; resetData(); emit('on-success'); dialogVisible.value = false
      }).catch(() => { submitBtnLoading.value = false })
    }
  })
}
watch(() => props.visible, (val) => { if (val) { init(); dialogVisible.value = val } })
</script>
