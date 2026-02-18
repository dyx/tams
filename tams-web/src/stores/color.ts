import { defineStore } from 'pinia'
import * as Api from '@/api/color'

export const useColorStore = defineStore('color', () => {
    const getColorList = async () => {
        const res: any = await Api.getColorList()
        return res.data
    }

    return { getColorList }
})
