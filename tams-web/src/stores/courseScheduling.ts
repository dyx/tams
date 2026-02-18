import { defineStore } from 'pinia'
import * as Api from '@/api/course-scheduling'

export const useCourseSchedulingStore = defineStore('courseScheduling', () => {
    const getCourseSchedulingList = async (param: any) => {
        const res: any = await Api.getCourseSchedulingList(param)
        return res.data
    }

    const getCourseSchedulingById = async (id: number | string) => {
        const res: any = await Api.getCourseSchedulingById(id)
        return res.data
    }

    const getCourseSchedulingCourseCount = async (params: any) => {
        const res: any = await Api.getCourseSchedulingCourseCount(params)
        return res.data
    }

    const saveCourseScheduling = async (data: any) => {
        const res: any = await Api.saveCourseScheduling(data)
        return res.data
    }

    const batchSaveCourseScheduling = async (data: any) => {
        const res: any = await Api.batchSaveCourseScheduling(data)
        return res.data
    }

    const updateCourseSchedulingById = async (id: number | string, data: any) => {
        const res: any = await Api.updateCourseSchedulingById(id, data)
        return res.data
    }

    const updateCourseSchedulingTimeById = async (id: number | string, data: any) => {
        const res: any = await Api.updateCourseSchedulingTimeById(id, data)
        return res.data
    }

    const removeCourseSchedulingById = async (id: number | string) => {
        const res: any = await Api.removeCourseSchedulingById(id)
        return res.data
    }

    const removeCourseSchedulingByIdList = async (idList: number[]) => {
        const res: any = await Api.removeCourseSchedulingByIdList(idList)
        return res.data
    }

    return {
        getCourseSchedulingList,
        getCourseSchedulingById,
        getCourseSchedulingCourseCount,
        saveCourseScheduling,
        batchSaveCourseScheduling,
        updateCourseSchedulingById,
        updateCourseSchedulingTimeById,
        removeCourseSchedulingById,
        removeCourseSchedulingByIdList
    }
})
