import * as Api from '@/api/classroom'

const classroom = {
  state: {
  },
  mutations: {
  },
  actions: {
    GetClassroomPage ({ commit }, param) {
      return new Promise((resolve, reject) => {
        Api.getClassroomPage(param)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetClassroomRefList ({ commit }) {
      return new Promise((resolve, reject) => {
        Api.getClassroomRefList()
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    GetClassroomById ({ commit }, id) {
      return new Promise((resolve, reject) => {
        Api.getClassroomById(id)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    SaveClassroom ({ commit }, data) {
      return new Promise((resolve, reject) => {
        Api.saveClassroom(data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateClassroomById ({ commit }, { id, data }) {
      return new Promise((resolve, reject) => {
        Api.updateClassroomById(id, data)
          .then(res => {
            resolve(res.data)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    UpdateClassroomEnableStateById ({ commit }, { id, enableState }) {
      return new Promise((resolve, reject) => {
        Api.updateClassroomEnableStateById(id, enableState)
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

export default classroom
