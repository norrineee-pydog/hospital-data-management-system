import request from '@/config/axios'

// 创建挂号
export const createAppointment = (data: {
  patientId: number
  doctorId: number
  deptId: number
  scheduleId: number
  reason?: string
}) => {
  return request.post({ url: '/hospital/appointment/create', data })
}

// 获取科室列表（挂号用）
export const getDepartmentListForAppointment = () => {
  return request.get({ url: '/hospital/appointment/get-department-list' })
}

// 获取某科室下的医生列表
export const getDoctorListForAppointment = (deptId: number) => {
  return request.get({ url: '/hospital/appointment/get-doctor-list?deptId=' + deptId })
}

// 获取某医生某日期的可用排班
export const getScheduleListForAppointment = (doctorId: number, date: string) => {
  return request.get({ url: '/hospital/appointment/get-schedule-list?doctorId=' + doctorId + '&date=' + date })
}

// 获取某医生的所有排班日期
export const getScheduleDatesForAppointment = (doctorId: number) => {
  return request.get({ url: '/hospital/appointment/get-schedule-dates?doctorId=' + doctorId })
}