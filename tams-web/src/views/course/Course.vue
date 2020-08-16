<template>
  <el-card shadow="never">
    <div slot="header" class="clearfix">
      <span>课程</span>
    </div>
    <el-row class="tams-search-container">
      <el-col :span="18">
        <el-radio-group v-model="params.enableState" size="small" @change="search">
          <el-radio-button :label="1">启用</el-radio-button>
          <el-radio-button :label="2">停用</el-radio-button>
        </el-radio-group>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" size="small" class="tams-operate-item" @click="saveCourseVisible=true">新增</el-button>
      </el-col>
    </el-row>

    <div v-loading="loading">
      <el-table stripe border :data="data.records">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="200"></el-table-column>
        <el-table-column prop="duration" label="时长" width="100"></el-table-column>
        <el-table-column label="背景颜色">
          <template slot-scope="scope">
            <div v-if="scope.row.backgroundColor" :style="{height: '23px', width: '100%', borderRadius: '5px', backgroundColor: scope.row.backgroundColor}"></div>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="130">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="showUpdate(scope.row.id)">编辑</el-button>
            <el-switch v-model="scope.row.enableState"
                       :active-value="1" :inactive-value="2"
                       active-color="#13ce66" inactive-color="#ff4949"
                       style="margin-left: 10px;"
                       @change="updateEnableState($event, scope.row.id)"></el-switch>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="current"
          :page-sizes="$consts.PAGE_SIZES"
          :page-size="size"
          style="margin-top: 10px;"
          layout="total, sizes, prev, pager, next"
          :total="data.total">
      </el-pagination>
    </div>
    <SaveCourse :visible="saveCourseVisible"
                 @on-close="saveCourseVisible=false"
                 @on-success="saveSuccess">
    </SaveCourse>
    <UpdateCourse :visible="updateCourseVisible"
                   :id="id"
                   @on-close="updateCourseVisible=false"
                   @on-success="updateSuccess">
    </UpdateCourse>
  </el-card>
</template>
<script>
import { mapActions } from 'vuex'
import SaveCourse from '@/views/course/SaveCourse'
import UpdateCourse from '@/views/course/UpdateCourse'

export default {
  name: 'Course',
  components: { UpdateCourse, SaveCourse },
  data () {
    return {
      data: [],
      params: {},
      form: {},
      current: 1,
      size: 10,
      saveCourseVisible: false,
      updateCourseVisible: false,
      id: '',
      loading: false
    }
  },
  methods: {
    ...mapActions(['GetCoursePage', 'UpdateCourseEnableStateById']),
    init () {
      this.params.enableState = 1
    },
    search () {
      this.params.current = this.current
      this.params.size = this.size
      this.loading = true
      this.GetCoursePage(this.params).then(res => {
        this.loading = false
        if (res) {
          this.data = res
        }
      }).catch(() => {
        this.loading = false
      })
    },
    handleSizeChange (val) {
      this.size = val
      this.search()
    },
    handleCurrentChange (val) {
      this.current = val
      this.search()
    },
    saveSuccess () {
      this.search()
      this.saveCourseVisible = false
    },
    showUpdate (id) {
      this.id = id
      this.updateCourseVisible = true
    },
    updateSuccess () {
      this.search()
      this.updateCourseVisible = false
    },
    updateEnableState (enableState, id) {
      this.UpdateCourseEnableStateById({ id, enableState }).then(res => {
        this.search()
      })
    }
  },
  mounted () {
    this.init()
    this.search()
  }
}
</script>

<style scoped>

</style>
