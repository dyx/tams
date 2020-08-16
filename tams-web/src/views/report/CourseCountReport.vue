<template>
  <el-row>
    <el-col :style="{width: 'calc(100% - '+width+')'}">
      <el-table stripe border :data="data">
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="name" label="课程名称" width="200"></el-table-column>
        <el-table-column prop="count" label="课程数量"></el-table-column>
      </el-table>
    </el-col>
    <el-col :style="{width: width}">
      <div id="courseCountChart" :style="{width: width, height: '600px'}"></div>
    </el-col>
  </el-row>
</template>
<script>
import { mapActions } from 'vuex'
import echarts from 'echarts'

export default {
  name: 'CourseCountReport',
  components: { },
  props: {
    startDate: String,
    endDate: String
  },
  data () {
    return {
      data: [],
      courseData: [],
      countData: [],
      colorData: [],
      width: '600px'
    }
  },
  methods: {
    ...mapActions(['GetReportCourseCount']),
    search () {
      this.GetReportCourseCount({
        startDate: this.startDate,
        endDate: this.endDate
      }).then((res) => {
        if (res) {
          this.data = res
          this.courseData = []
          this.countData = []
          for (let i = 0; i < this.data.length; i++) {
            if (i < 10) {
              const item = this.data[i]
              this.courseData.push(item.name)
              this.countData.push(item.count)
              this.colorData.push(item.color)
            }
          }
          this.courseData.reverse()
          this.countData.reverse()
          this.colorData.reverse()
          this.draw()
        }
      }).catch(() => {
      })
    },
    draw () {
      const chart = echarts.init(document.getElementById('courseCountChart'))
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
          data: this.courseData
        },
        series: [
          {
            type: 'bar',
            data: this.countData,
            label: {
              show: true
            },
            itemStyle: {
              color: function (params) {
                return '#409EFF'
              }
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
