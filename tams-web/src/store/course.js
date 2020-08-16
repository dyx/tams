import * as Api from '@/api/course'

const course = {
  state: {
  },
  mutations: {
  },
  actions: {
    GetCoursePage ({ commit }, param) {
      return new Promise((resolve, reject) => {
        Api.getCoursePage(param)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetCourseRefList ({ commit }) {
      return new Promise((resolve, reject) => {
        Api.getCourseRefList()
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetCourseById ({ commit }, id) {
      return new Promise((resolve, reject) => {
        Api.getCourseById(id)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    SaveCourse ({ commit }, data) {
      return new Promise((resolve, reject) => {
        Api.saveCourse(data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateCourseById ({ commit }, { id, data }) {
      return new Promise((resolve, reject) => {
        Api.updateCourseById(id, data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateCourseEnableStateById ({ commit }, { id, enableState }) {
      return new Promise((resolve, reject) => {
        Api.updateCourseEnableStateById(id, enableState)
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

export default course
