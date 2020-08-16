import axios from 'axios'
import { Notification } from 'element-ui'
import consts from '@/util/consts'

const msgSet = new Set()
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_URL,
  timeout: 30000
})

const err = error => {
  if (error.response) {
    const data = error.response.data
    const status = error.response.status
    if (consts.SELF_HANDLE_ERROR_CODE.indexOf(data.code) >= 0) {
      return Promise.reject(data)
    }
    if (status === 400 || status === 403 || status === 500) {
      msgSet.add(data.msg ? data.msg : '服务器发生错误')
      setTimeout(() => {
        msgSet.forEach(item => {
          Notification.error({
            title: '错误提示',
            message: item
          })
        })
        msgSet.clear()
      }, 500)
    }
    return Promise.reject(data)
  }
  return Promise.reject(error)
}

// 响应拦截器
service.interceptors.response.use(response => {
  return response.data
}, err)

export { service as axios }
