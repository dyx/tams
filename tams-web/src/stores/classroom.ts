import { defineStore } from 'pinia'
import * as Api from '@/api/classroom'

export const useClassroomStore = defineStore('classroom', () => {
    const getClassroomPage = async (param: any) => {
        const res: any = await Api.getClassroomPage(param)
        return res.data
    }

    const getClassroomRefList = async () => {
        const res: any = await Api.getClassroomRefList()
        return res.data
    }

    const getClassroomById = async (id: number) => {
        const res: any = await Api.getClassroomById(id)
        return res.data
    }

    const saveClassroom = async (data: any) => {
        const res: any = await Api.saveClassroom(data)
        return res.data
    }

    const updateClassroomById = async (id: number, data: any) => {
        const res: any = await Api.updateClassroomById(id, data)
        return res.data
    }

    const updateClassroomEnableStateById = async (id: number, enableState: number) => {
        const res: any = await Api.updateClassroomEnableStateById(id, enableState)
        return res.data
    }

    return {
        getClassroomPage,
        getClassroomRefList,
        getClassroomById,
        saveClassroom,
        updateClassroomById,
        updateClassroomEnableStateById
    }
})
