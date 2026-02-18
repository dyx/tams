import { axios } from '@/utils/request'

export const getClassroomPage = (params: any) => {
    return axios({
        url: 'classroom',
        method: 'get',
        params
    })
}

export const getClassroomRefList = () => {
    return axios({
        url: 'classroom/list/ref',
        method: 'get'
    })
}

export const getClassroomById = (id: number) => {
    return axios({
        url: 'classroom/' + id,
        method: 'get'
    })
}

export const saveClassroom = (data: any) => {
    return axios({
        url: 'classroom',
        method: 'post',
        data
    })
}

export const updateClassroomById = (id: number, data: any) => {
    return axios({
        url: 'classroom/' + id,
        method: 'put',
        data
    })
}

export const updateClassroomEnableStateById = (id: number, enableState: number) => {
    return axios({
        url: 'classroom/' + id + '/enable-state/' + enableState,
        method: 'put'
    })
}
