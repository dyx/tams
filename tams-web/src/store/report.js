import * as Api from '@/api/report'

const report = {
  state: {
  },
  mutations: {
  },
  actions: {
    GetReportTeacherCount ({ commit }, param) {
      return new Promise((resolve, reject) => {
        Api.getReportTeacherCount(param)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetReportCourseCount ({ commit }, param) {
      return new Promise((resolve, reject) => {
        Api.getReportCourseCount(param)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    }
  }
}

export default report
