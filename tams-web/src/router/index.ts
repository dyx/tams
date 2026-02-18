import { createRouter, createWebHashHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Main from '@/views/Main.vue'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/course-scheduling',
        component: Main,
        children: [
            {
                path: '/course-scheduling',
                component: () => import('@/views/course-scheduling/CourseScheduling.vue')
            },
            {
                path: '/course-scheduling-list',
                component: () => import('@/views/course-scheduling-list/CourseSchedulingList.vue')
            },
            {
                path: '/classroom',
                component: () => import('@/views/classroom/Classroom.vue')
            },
            {
                path: '/course',
                component: () => import('@/views/course/Course.vue')
            },
            {
                path: '/teacher',
                component: () => import('@/views/teacher/Teacher.vue')
            },
            {
                path: '/report',
                component: () => import('@/views/report/Report.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
