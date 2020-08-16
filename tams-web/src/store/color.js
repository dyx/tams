import * as Api from '@/api/color'

const color = {
  state: {
  },
  mutations: {
  },
  actions: {
    GetEffectiveList ({ commit }) {
      return new Promise((resolve, reject) => {
        Api.getEffectiveList()
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

export default color
