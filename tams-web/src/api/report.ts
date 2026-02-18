import { axios } from '@/utils/request'

export const getReportTeacherCount = (params: any) => {
    return axios({
        url: 'report/teacher/count',
        method: 'get',
        params
    })
}

export const getReportCourseCount = (params: any) => {
    return axios({
        url: 'report/course/count',
        method: 'get',
        params
    })
}
