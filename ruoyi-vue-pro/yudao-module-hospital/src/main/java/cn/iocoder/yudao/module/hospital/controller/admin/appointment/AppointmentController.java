package cn.iocoder.yudao.module.hospital.controller.admin.appointment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.AppointmentCancelReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.AppointmentCreateReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.DepartmentRespVO;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.DoctorScheduleRespVO;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.DoctorSimpleRespVO;
import cn.iocoder.yudao.module.hospital.service.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 挂号预约")
@RestController
@RequestMapping("/hospital/appointment")
@Validated
public class AppointmentController {

    @Resource
    private AppointmentService appointmentService;

    // ========== 取消挂号 ==========
    @PostMapping("/cancel")
    @Operation(summary = "取消挂号")
    public CommonResult<Boolean> cancel(@Valid @RequestBody AppointmentCancelReqVO reqVO) {
        appointmentService.cancelAppointment(reqVO.getVisitId());
        return success(true);
    }

    // ========== 预约挂号（新增）==========

    @GetMapping("/get-department-list")
    @Operation(summary = "获取科室列表")
    public CommonResult<List<DepartmentRespVO>> getDepartmentList() {
        return success(appointmentService.getDepartmentList());
    }

    @GetMapping("/get-doctor-list")
    public CommonResult<List<DoctorSimpleRespVO>> getDoctorList(@RequestParam("deptId") Long deptId) {
        return success(appointmentService.getDoctorListByDeptId(deptId));
    }

    @GetMapping("/get-schedule-dates")
    public CommonResult<List<String>> getScheduleDates(@RequestParam("doctorId") Long doctorId) {
        return success(appointmentService.getScheduleDates(doctorId));
    }

    @GetMapping("/get-schedule-list")
    public CommonResult<List<DoctorScheduleRespVO>> getScheduleList(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("date") String date) {
        return success(appointmentService.getScheduleList(doctorId, date));
    }

    @PostMapping("/create")
    @Operation(summary = "创建挂号")
    public CommonResult<Integer> createAppointment(@Valid @RequestBody AppointmentCreateReqVO reqVO) {
        return success(appointmentService.createAppointment(reqVO));
    }
}