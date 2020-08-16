import * as Api from '@/api/teacher'

const teacher = {
  state: {
  },
  mutations: {
  },
  actions: {
    GetTeacherPage ({ commit }, param) {
      return new Promise((resolve, reject) => {
        Api.getTeacherPage(param)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetTeacherRefList ({ commit }) {
      return new Promise((resolve, reject) => {
        Api.getTeacherRefList()
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetTeacherById ({ commit }, id) {
      return new Promise((resolve, reject) => {
        Api.getTeacherById(id)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    SaveTeacher ({ commit }, data) {
      return new Promise((resolve, reject) => {
        Api.saveTeacher(data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateTeacherById ({ commit }, { id, data }) {
      return new Promise((resolve, reject) => {
        Api.updateTeacherById(id, data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateTeacherEnableStateById ({ commit }, { id, enableState }) {
      return new Promise((resolve, reject) => {
        Api.updateTeacherEnableStateById(id, enableState)
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

export default teacher
