package cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 创建挂号 Request VO")
@Data
public class AppointmentCreateReqVO {

    @Schema(description = "患者ID", required = true, example = "1")
    @NotNull(message = "患者ID不能为空")
    private Long patientId;

    @Schema(description = "医生ID", required = true, example = "1")
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    @Schema(description = "科室ID", required = true, example = "1")
    @NotNull(message = "科室ID不能为空")
    private Long deptId;

    @Schema(description = "排班ID", required = true, example = "1")
    @NotNull(message = "排班ID不能为空")
    private Long scheduleId;

    @Schema(description = "就诊原因", example = "感冒发烧")
    private String reason;
}