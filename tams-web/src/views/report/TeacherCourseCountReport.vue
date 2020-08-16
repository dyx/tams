<template>
  <el-row>
    <el-col :style="{width: 'calc(100% - '+width+')'}">
      <el-table stripe border :data="data">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="name" label="老师姓名" width="200"></el-table-column>
        <el-table-column prop="count" label="课程数量"></el-table-column>
      </el-table>
    </el-col>
    <el-col :style="{width: width}">
      <div id="teacherCourseCountChart" :style="{width: width, height: '600px'}"></div>
    </el-col>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import echarts from 'echarts'

export default {
  name: 'TeacherCourseCountReport',
  components: { },
  props: {
    startDate: String,
    endDate: String
  },
  data () {
    return {
      data: [],
      teacherData: [],
      courseCountData: [],
      width: '600px'
    }
  },
  methods: {
    ...mapActions(['GetReportTeacherCount']),
    search () {
      this.GetReportTeacherCount({
        startDate: this.startDate,
        endDate: this.endDate
      }).then((res) => {
        if (res) {
          this.data = res
          this.teacherData = []
          this.courseCountData = []
          for (let i = 0; i < this.data.length; i++) {
            if (i < 10) {
              const item = this.data[i]
              this.teacherData.push(item.name)
              this.courseCountData.push(item.count)
            }
          }
          this.teacherData.reverse()
          this.courseCountData.reverse()
          this.draw()
        }
      }).catch(() => {
      })
    },
    draw () {
      const chart = echarts.init(document.getElementById('teacherCourseCountChart'))
      chart.setOption({
        title: {
          text: 'Top10'
        },
        grid: {
          left: '15%'
        },
        xAxis: {
          type: 'value',
          minInterval: 1
        },
        yAxis: {
          type: 'category',
          data: this.teacherData
        },
        series: [
          {
            type: 'bar',
            data: this.courseCountData,
            label: {
              show: true
            },
            itemStyle: {
              color: '#409EFF'
            }
          }
        ]
      })
    }
  },
  watch: {
    startDate (val) {
      this.search()
    }
  }
}
</script>

<style scoped>

</style>
