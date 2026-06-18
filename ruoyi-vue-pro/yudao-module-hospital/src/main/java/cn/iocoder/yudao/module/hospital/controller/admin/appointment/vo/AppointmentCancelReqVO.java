package cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 取消挂号 Request VO")
@Data
public class AppointmentCancelReqVO {

    @Schema(description = "就诊记录ID", required = true, example = "1")
    @NotNull(message = "就诊记录ID不能为空")
    private Long visitId;

}
