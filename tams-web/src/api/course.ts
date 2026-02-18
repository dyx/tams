import { axios } from '@/utils/request'

export const getCoursePage = (params: any) => {
    return axios({
        url: 'course',
        method: 'get',
        params
    })
}

export const getCourseRefList = () => {
    return axios({
        url: 'course/list/ref',
        method: 'get'
    })
}

export const getCourseById = (id: number) => {
    return axios({
        url: 'course/' + id,
        method: 'get'
    })
}

export const saveCourse = (data: any) => {
    return axios({
        url: 'course',
        method: 'post',
        data
    })
}

export const updateCourseById = (id: number, data: any) => {
    return axios({
        url: 'course/' + id,
        method: 'put',
        data
    })
}

export const updateCourseEnableStateById = (id: number, enableState: number) => {
    return axios({
        url: 'course/' + id + '/enable-state/' + enableState,
        method: 'put'
    })
}
