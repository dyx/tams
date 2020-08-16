import { axios } from '@/util/request'

export const getTeacherPage = (params) => {
  return axios({
    url: 'teacher',
    method: 'get',
    params
  })
}

export const getTeacherRefList = () => {
  return axios({
    url: 'teacher/list/ref',
    method: 'get'
  })
}

export const getTeacherById = (id) => {
  return axios({
    url: 'teacher/' + id,
    method: 'get'
  })
}

export const saveTeacher = (data) => {
  return axios({
    url: 'teacher',
    method: 'post',
    data
  })
}

export const updateTeacherById = (id, data) => {
  return axios({
    url: 'teacher/' + id,
    method: 'put',
    data
  })
}

export const updateTeacherEnableStateById = (id, enableState) => {
  return axios({
    url: 'teacher/' + id + '/enable-state/' + enableState,
    method: 'put'
  })
}
