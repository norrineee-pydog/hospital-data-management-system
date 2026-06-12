// src/router/modules/hospital.ts
import { RouteRecordRaw } from 'vue-router'

const hospitalRoutes: RouteRecordRaw = {
  path: '/hospital',
  component: () => import('@/layout/index.vue'),
  redirect: '/hospital/register',
  meta: {
    title: '诊疗管理',
    icon: 'ep:hospital',
    alwaysShow: true,
    order: 10 // 控制菜单顺序
  },
  children: [
    {
      path: 'register',
      component: () => import('@/views/hospital/register/index.vue'),
      name: 'HospitalRegister',
      meta: {
        title: '挂号管理',
        icon: 'ep:tickets',
        noCache: false
      }
    },
    {
      path: 'prescription',
      component: () => import('@/views/hospital/prescription/index.vue'),
      name: 'HospitalPrescription',
      meta: {
        title: '处方管理',
        icon: 'ep:edit',
        noCache: false
      }
    },
    {
      path: 'dashboard',
      component: () => import('@/views/hospital/dashboard/index.vue'),
      name: 'HospitalDashboard',
      meta: {
        title: '院长驾驶舱',
        icon: 'ep:data-analysis',
        noCache: false
      }
    }
  ]
}

export default hospitalRoutes
