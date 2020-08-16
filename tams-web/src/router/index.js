import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'

Vue.use(VueRouter)

const routes = [
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

const router = new VueRouter({
  routes
})

export default router
