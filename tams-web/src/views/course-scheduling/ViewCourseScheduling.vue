<template>
  <div>
    <el-dialog title="详情" width="790px" v-model="dialogVisible" :before-close="handleClose">
      <el-form ref="formRef" :model="form" inline label-width="80px" class="tams-form-container">
        <el-form-item label="日期">
          <el-input v-model="form.date" class="tams-form-item" readonly />
        </el-form-item>
        <el-form-item label="教室">
          <el-input v-model="form.classroomName" class="tams-form-item" readonly />
        </el-form-item>
        <el-form-item label="课程">
          <el-input v-model="form.courseName" class="tams-form-item" readonly />
        </el-form-item>
        <el-form-item label="老师">
          <el-input v-model="form.teacherName" class="tams-form-item" readonly />
        </el-form-item>
        <el-form-item label="上课时间">
          <el-input v-model="form.attendTime" class="tams-form-item" readonly />
        </el-form-item>
        <el-form-item label="下课时间">
          <el-input v-model="form.finishTime" class="tams-form-item" readonly />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-popconfirm title="确定要删除吗？" @confirm="remove">
            <template #reference>
              <el-button type="danger" style="margin-right: 10px;">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button type="primary" @click="updateCourseSchedulingVisible = true">修改</el-button>
        </div>
      </template>
    </el-dialog>
    <UpdateCourseScheduling
      :visible="updateCourseSchedulingVisible"
      :id="props.id"
      @on-success="updateSuccess"
      @on-close="updateCourseSchedulingVisible = false"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { FormInstance } from 'element-plus'
import dayjs from 'dayjs'
import { useCourseSchedulingStore } from '@/stores/courseScheduling'
import UpdateCourseScheduling from '@/views/course-scheduling/UpdateCourseScheduling.vue'

const props = defineProps<{ visible: boolean; id: number | string }>()
const emit = defineEmits<{ 'on-close': []; 'on-success': [] }>()

const csStore = useCourseSchedulingStore()
const dialogVisible = ref(false)
const updateCourseSchedulingVisible = ref(false)
const formRef = ref<FormInstance>()
const form = ref<any>({})

const search = () => {
  csStore.getCourseSchedulingById(props.id).then((res: any) => {
    form.value = res
    form.value.attendTime = dayjs(res.attendTime, 'HH:mm:ss').format('HH:mm')
    form.value.finishTime = dayjs(res.finishTime, 'HH:mm:ss').format('HH:mm')
  }).catch(() => {})
}

const remove = () => {
  csStore.removeCourseSchedulingById(props.id).then(() => {
    emit('on-success')
    dialogVisible.value = false
  }).catch(() => {})
}

const handleClose = (done: () => void) => {
  formRef.value?.resetFields()
  emit('on-close')
  done()
}

const updateSuccess = () => {
  emit('on-success')
  search()
  updateCourseSchedulingVisible.value = false
}

watch(() => props.visible, (val) => {
  if (val) { search(); dialogVisible.value = val }
})
</script>
