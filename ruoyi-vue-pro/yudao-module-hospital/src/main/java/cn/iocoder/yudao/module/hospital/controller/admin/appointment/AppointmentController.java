package cn.iocoder.yudao.module.hospital.controller.admin.appointment;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.AppointmentCancelReqVO;
import cn.iocoder.yudao.module.hospital.service.appointment.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 挂号预约")
@RestController
@RequestMapping("/hospital/appointment")
@Validated
public class AppointmentController {

    @Resource
    private AppointmentService appointmentService;

    @PostMapping("/cancel")
    @Operation(summary = "取消挂号")
    public CommonResult<Boolean> cancel(@Valid @RequestBody AppointmentCancelReqVO reqVO) {
        appointmentService.cancelAppointment(reqVO.getVisitId());
        return success(true);
    }

}