import { defineStore } from 'pinia'
import * as Api from '@/api/report'

export const useReportStore = defineStore('report', () => {
    const getReportTeacherCount = async (params: any) => {
        const res: any = await Api.getReportTeacherCount(params)
        return res.data
    }

    const getReportCourseCount = async (params: any) => {
        const res: any = await Api.getReportCourseCount(params)
        return res.data
    }

    return { getReportTeacherCount, getReportCourseCount }
})
