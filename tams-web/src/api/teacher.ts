import { axios } from '@/utils/request'

export const getTeacherPage = (params: any) => {
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

export const getTeacherById = (id: number) => {
    return axios({
        url: 'teacher/' + id,
        method: 'get'
    })
}

export const saveTeacher = (data: any) => {
    return axios({
        url: 'teacher',
        method: 'post',
        data
    })
}

export const updateTeacherById = (id: number, data: any) => {
    return axios({
        url: 'teacher/' + id,
        method: 'put',
        data
    })
}

export const updateTeacherEnableStateById = (id: number, enableState: number) => {
    return axios({
        url: 'teacher/' + id + '/enable-state/' + enableState,
        method: 'put'
    })
}
