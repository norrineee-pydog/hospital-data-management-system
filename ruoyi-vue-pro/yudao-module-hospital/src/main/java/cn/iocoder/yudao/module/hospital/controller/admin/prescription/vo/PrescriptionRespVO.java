package cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 处方 Response VO")
@Data
public class PrescriptionRespVO {

    @Schema(description = "处方ID")
    private Long id;

    @Schema(description = "就诊ID")
    private Long visitId;

    @Schema(description = "患者姓名", example = "刘大爷")
    private String patientName;

    @Schema(description = "医生ID")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "王建国")
    private String doctorName;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "备注")
    private String notes;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}