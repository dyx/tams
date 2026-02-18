<template>
  <el-card shadow="never">
    <template #header>
      <div class="clearfix"><span>课程</span></div>
    </template>
    <el-row class="tams-search-container">
      <el-col :span="18">
        <el-radio-group v-model="params.enableState" size="small" @change="search">
          <el-radio-button :value="1">启用</el-radio-button>
          <el-radio-button :value="2">停用</el-radio-button>
        </el-radio-group>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" size="small" class="tams-operate-item" @click="saveCourseVisible = true">新增</el-button>
      </el-col>
    </el-row>
    <div v-loading="loading">
      <el-table stripe border :data="data.records">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="name" label="名称" width="200" />
        <el-table-column prop="duration" label="时长" width="100" />
        <el-table-column label="背景颜色">
          <template #default="scope">
            <div v-if="scope.row.backgroundColor" :style="{ height: '23px', width: '100%', borderRadius: '5px', backgroundColor: scope.row.backgroundColor }" />
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="130">
          <template #default="scope">
            <el-button type="primary" size="small" @click="showUpdate(scope.row.id)">编辑</el-button>
            <el-switch
              v-model="scope.row.enableState"
              :active-value="1" :inactive-value="2"
              active-color="#13ce66" inactive-color="#ff4949"
              style="margin-left: 10px;"
              @change="updateEnableState($event as number, scope.row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="current" :page-sizes="CONSTS.PAGE_SIZES as any" :page-size="size"
        style="margin-top: 10px;" layout="total, sizes, prev, pager, next" :total="data.total"
      />
    </div>
    <SaveCourse :visible="saveCourseVisible" @on-close="saveCourseVisible = false" @on-success="saveSuccess" />
    <UpdateCourse :visible="updateCourseVisible" :id="id" @on-close="updateCourseVisible = false" @on-success="updateSuccess" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useCourseStore } from '@/stores/course'
import { CONSTS } from '@/utils/consts'
import SaveCourse from '@/views/course/SaveCourse.vue'
import UpdateCourse from '@/views/course/UpdateCourse.vue'

const courseStore = useCourseStore()
const data = ref<any>([])
const params = reactive<any>({})
const current = ref(1)
const size = ref(10)
const saveCourseVisible = ref(false)
const updateCourseVisible = ref(false)
const id = ref<number | string>('')
const loading = ref(false)

const search = () => {
  params.current = current.value; params.size = size.value; loading.value = true
  courseStore.getCoursePage(params).then((res: any) => { loading.value = false; if (res) data.value = res }).catch(() => { loading.value = false })
}
const handleSizeChange = (val: number) => { size.value = val; search() }
const handleCurrentChange = (val: number) => { current.value = val; search() }
const saveSuccess = () => { search(); saveCourseVisible.value = false }
const showUpdate = (rowId: number) => { id.value = rowId; updateCourseVisible.value = true }
const updateSuccess = () => { search(); updateCourseVisible.value = false }
const updateEnableState = (enableState: number, rowId: number) => { courseStore.updateCourseEnableStateById(rowId, enableState).then(() => search()) }

onMounted(() => { params.enableState = 1; search() })
</script>
