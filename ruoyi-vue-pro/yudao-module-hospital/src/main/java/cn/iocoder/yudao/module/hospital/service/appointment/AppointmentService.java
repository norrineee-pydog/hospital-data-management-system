package cn.iocoder.yudao.module.hospital.service.appointment;

import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.*;
import java.util.List;

public interface AppointmentService {

    // 取消挂号
    void cancelAppointment(Long visitId);

    // 获取科室列表
    List<DepartmentRespVO> getDepartmentList();

    // 根据科室获取医生列表
    List<DoctorSimpleRespVO> getDoctorListByDeptId(Long deptId);  // ← 改成 Long

    // 获取医生的排班日期
    List<String> getScheduleDates(Long doctorId);  // ← 改成 Long

    // 获取医生某日期的可用排班
    List<DoctorScheduleRespVO> getScheduleList(Long doctorId, String date);  // ← 改成 Long

    // 创建挂号
    Integer createAppointment(AppointmentCreateReqVO reqVO);
}