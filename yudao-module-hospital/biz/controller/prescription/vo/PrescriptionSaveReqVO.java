package cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 处方新增/修改 Request VO")
@Data
public class PrescriptionSaveReqVO {

    @Schema(description = "处方ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4885")
    private Integer prescriptionId;

    @Schema(description = "就诊记录ID", example = "1337")
    private Integer visitId;

    @Schema(description = "开方医生ID", example = "22000")
    private Integer doctorId;

    @Schema(description = "状态：未发药/已发药/已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotEmpty(message = "状态：未发药/已发药/已过期不能为空")
    private String status;

    @Schema(description = "处方备注")
    private String notes;

}