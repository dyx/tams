import { defineStore } from 'pinia'
import * as Api from '@/api/course'

export const useCourseStore = defineStore('course', () => {
    const getCoursePage = async (param: any) => {
        const res: any = await Api.getCoursePage(param)
        return res.data
    }

    const getCourseRefList = async () => {
        const res: any = await Api.getCourseRefList()
        return res.data
    }

    const getCourseById = async (id: number) => {
        const res: any = await Api.getCourseById(id)
        return res.data
    }

    const saveCourse = async (data: any) => {
        const res: any = await Api.saveCourse(data)
        return res.data
    }

    const updateCourseById = async (id: number, data: any) => {
        const res: any = await Api.updateCourseById(id, data)
        return res.data
    }

    const updateCourseEnableStateById = async (id: number, enableState: number) => {
        const res: any = await Api.updateCourseEnableStateById(id, enableState)
        return res.data
    }

    return {
        getCoursePage,
        getCourseRefList,
        getCourseById,
        saveCourse,
        updateCourseById,
        updateCourseEnableStateById
    }
})
