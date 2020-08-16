import Vue from 'vue'
import Vuex from 'vuex'
import teacher from '@/store/teacher'
import course from '@/store/course'
import classroom from '@/store/classroom'
import courseScheduling from '@/store/course-scheduling'
import color from '@/store/color'
import report from '@/store/report'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    teacher,
    course,
    classroom,
    courseScheduling,
    color,
    report
  }
})
