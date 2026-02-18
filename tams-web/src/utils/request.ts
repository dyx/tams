import axios from 'axios'
import { ElNotification } from 'element-plus'
import { CONSTS } from '@/utils/consts'

const msgSet = new Set<string>()

const service = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL as string || '/',
    timeout: 30000
})

const err = (error: any) => {
    if (error.response) {
        const data = error.response.data
        const status = error.response.status
        if (CONSTS.SELF_HANDLE_ERROR_CODE.indexOf(data.code) >= 0) {
            return Promise.reject(data)
        }
        if (status === 400 || status === 403 || status === 500) {
            msgSet.add(data.msg ? data.msg : '服务器发生错误')
            setTimeout(() => {
                msgSet.forEach(item => {
                    ElNotification.error({
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
