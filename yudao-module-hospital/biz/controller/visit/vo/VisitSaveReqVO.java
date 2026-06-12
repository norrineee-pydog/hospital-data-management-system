package cn.iocoder.yudao.module.hospital.controller.admin.visit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 就诊记录新增/修改 Request VO")
@Data
public class VisitSaveReqVO {

    @Schema(description = "就诊ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8812")
    private Integer visitId;

    @Schema(description = "患者ID", example = "10921")
    private Integer patientId;

    @Schema(description = "医生ID", example = "17151")
    private Integer doctorId;

    @Schema(description = "科室ID", example = "12542")
    private Integer deptId;

    @Schema(description = "就诊时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "就诊时间不能为空")
    private LocalDateTime visitDate;

    @Schema(description = "就诊原因", example = "不好")
    private String reason;

    @Schema(description = "诊断结果")
    private String diagnosis;

    @Schema(description = "医生备注")
    private String notes;

    @Schema(description = "状态：待就诊/就诊中/已完成/已取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态：待就诊/就诊中/已完成/已取消不能为空")
    private String status;

}