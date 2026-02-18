import { axios } from '@/utils/request'

export const getCourseSchedulingList = (params: any) => {
    return axios({
        url: 'course-scheduling/list',
        method: 'post',
        data: params
    })
}

export const getCourseSchedulingById = (id: number | string) => {
    return axios({
        url: 'course-scheduling/' + id,
        method: 'get'
    })
}

export const getCourseSchedulingCourseCount = (params: any) => {
    return axios({
        url: 'course-scheduling/course/count',
        method: 'post',
        data: params
    })
}

export const saveCourseScheduling = (data: any) => {
    return axios({
        url: 'course-scheduling',
        method: 'post',
        data
    })
}

export const batchSaveCourseScheduling = (data: any) => {
    return axios({
        url: 'course-scheduling/batch',
        method: 'post',
        data
    })
}

export const updateCourseSchedulingById = (id: number | string, data: any) => {
    return axios({
        url: 'course-scheduling/' + id,
        method: 'put',
        data
    })
}

export const updateCourseSchedulingTimeById = (id: number | string, data: any) => {
    return axios({
        url: 'course-scheduling/' + id + '/time',
        method: 'put',
        data
    })
}

export const removeCourseSchedulingById = (id: number | string) => {
    return axios({
        url: 'course-scheduling/' + id,
        method: 'delete'
    })
}

export const removeCourseSchedulingByIdList = (idList: number[]) => {
    return axios({
        url: 'course-scheduling/batch',
        method: 'delete',
        data: idList
    })
}
