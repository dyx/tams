<template>
  <el-card shadow="never">
    <template #header>
      <div class="clearfix">
        <span>教室</span>
      </div>
    </template>
    <el-row class="tams-search-container">
      <el-col :span="18">
        <el-radio-group v-model="params.enableState" size="small" @change="search">
          <el-radio-button :value="1">启用</el-radio-button>
          <el-radio-button :value="2">停用</el-radio-button>
        </el-radio-group>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" size="small" class="tams-operate-item" @click="saveClassroomVisible = true">新增</el-button>
      </el-col>
    </el-row>

    <div v-loading="loading">
      <el-table stripe border :data="data.records">
        <el-table-column type="index" label="序号" width="50" />
        <el-table-column prop="name" label="名称" />
        <el-table-column fixed="right" label="操作" width="130">
          <template #default="scope">
            <el-button type="primary" size="small" @click="showUpdate(scope.row.id)">编辑</el-button>
            <el-switch
              v-model="scope.row.enableState"
              :active-value="1"
              :inactive-value="2"
              active-color="#13ce66"
              inactive-color="#ff4949"
              style="margin-left: 10px;"
              @change="updateEnableState($event as number, scope.row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="CONSTS.PAGE_SIZES as any"
        :page-size="size"
        style="margin-top: 10px;"
        layout="total, sizes, prev, pager, next"
        :total="data.total"
      />
    </div>
    <SaveClassroom :visible="saveClassroomVisible" @on-close="saveClassroomVisible = false" @on-success="saveSuccess" />
    <UpdateClassroom :visible="updateClassroomVisible" :id="id" @on-close="updateClassroomVisible = false" @on-success="updateSuccess" />
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useClassroomStore } from '@/stores/classroom'
import { CONSTS } from '@/utils/consts'
import SaveClassroom from '@/views/classroom/SaveClassroom.vue'
import UpdateClassroom from '@/views/classroom/UpdateClassroom.vue'

const classroomStore = useClassroomStore()

const data = ref<any>([])
const params = reactive<any>({})
const current = ref(1)
const size = ref(10)
const saveClassroomVisible = ref(false)
const updateClassroomVisible = ref(false)
const id = ref<number | string>('')
const loading = ref(false)

const init = () => {
  params.enableState = 1
}

const search = () => {
  params.current = current.value
  params.size = size.value
  loading.value = true
  classroomStore.getClassroomPage(params).then((res: any) => {
    loading.value = false
    if (res) data.value = res
  }).catch(() => {
    loading.value = false
  })
}

const handleSizeChange = (val: number) => {
  size.value = val
  search()
}

const handleCurrentChange = (val: number) => {
  current.value = val
  search()
}

const saveSuccess = () => {
  search()
  saveClassroomVisible.value = false
}

const showUpdate = (rowId: number) => {
  id.value = rowId
  updateClassroomVisible.value = true
}

const updateSuccess = () => {
  search()
  updateClassroomVisible.value = false
}

const updateEnableState = (enableState: number, rowId: number) => {
  classroomStore.updateClassroomEnableStateById(rowId, enableState).then(() => {
    search()
  })
}

onMounted(() => {
  init()
  search()
})
</script>
