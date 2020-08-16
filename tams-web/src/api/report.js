import { axios } from '@/util/request'

export const getReportTeacherCount = (params) => {
  return axios({
    url: 'report/teacher/count',
    method: 'get',
    params
  })
}

export const getReportCourseCount = (params) => {
  return axios({
    url: 'report/course/count',
    method: 'get',
    params
  })
}
