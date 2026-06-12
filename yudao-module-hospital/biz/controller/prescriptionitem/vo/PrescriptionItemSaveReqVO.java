package cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 处方明细新增/修改 Request VO")
@Data
public class PrescriptionItemSaveReqVO {

    @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5873")
    private Integer itemId;

    @Schema(description = "处方ID", example = "13533")
    private Integer prescriptionId;

    @Schema(description = "药品ID", example = "20687")
    private Integer medicineId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量不能为空")
    private Integer quantity;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "16837")
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    @Schema(description = "用法说明")
    private String instructions;

}