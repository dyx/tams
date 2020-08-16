<template>
  <el-container>
    <el-aside width="100px">
      <el-menu
        router
        @select="handleSelect">
        <el-menu-item v-for="item in navData"
                      :key="item.code"
                      :index="item.code" :route="item.path">{{item.name}}</el-menu-item>
      </el-menu>
    </el-aside>
    <el-main style="padding: 10px 20px;">
      <router-view/>
    </el-main>
  </el-container>
</template>

<script>

export default {
  name: 'Main',
  components: {
  },
  data () {
    return {
      navData: [
        {
          code: '01',
          path: '/course-scheduling',
          name: '排课日历'
        },
        {
          code: '02',
          path: '/course-scheduling-list',
          name: '排课列表'
        },
        {
          code: '03',
          path: '/classroom',
          name: '教室'
        },
        {
          code: '04',
          path: '/course',
          name: '课程'
        },
        {
          code: '05',
          path: '/teacher',
          name: '老师'
        },
        {
          code: '06',
          path: '/report',
          name: '报表'
        }
      ],
      currentNavName: ''
    }
  },
  methods: {
    handleSelect (key, keyPath) {
    }
  }
}
</script>

<style scoped>

</style>
