import { axios } from '@/util/request'

export const getEffectiveList = () => {
  return axios({
    url: 'color/list/effective',
    method: 'get'
  })
}
