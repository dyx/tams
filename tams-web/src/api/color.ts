import { axios } from '@/utils/request'

export const getColorList = () => {
    return axios({
        url: 'color/list/effective',
        method: 'get'
    })
}
