import { defineStore } from 'pinia'
import * as Api from '@/api/teacher'

export const useTeacherStore = defineStore('teacher', () => {
    const getTeacherPage = async (param: any) => {
        const res: any = await Api.getTeacherPage(param)
        return res.data
    }

    const getTeacherRefList = async () => {
        const res: any = await Api.getTeacherRefList()
        return res.data
    }

    const getTeacherById = async (id: number) => {
        const res: any = await Api.getTeacherById(id)
        return res.data
    }

    const saveTeacher = async (data: any) => {
        const res: any = await Api.saveTeacher(data)
        return res.data
    }

    const updateTeacherById = async (id: number, data: any) => {
        const res: any = await Api.updateTeacherById(id, data)
        return res.data
    }

    const updateTeacherEnableStateById = async (id: number, enableState: number) => {
        const res: any = await Api.updateTeacherEnableStateById(id, enableState)
        return res.data
    }

    return {
        getTeacherPage,
        getTeacherRefList,
        getTeacherById,
        saveTeacher,
        updateTeacherById,
        updateTeacherEnableStateById
    }
})
