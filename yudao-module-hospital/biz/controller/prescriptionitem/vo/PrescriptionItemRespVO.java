package cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 处方明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrescriptionItemRespVO {

    @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5873")
    @ExcelProperty("明细ID")
    private Integer itemId;

    @Schema(description = "处方ID", example = "13533")
    @ExcelProperty("处方ID")
    private Integer prescriptionId;

    @Schema(description = "药品ID", example = "20687")
    @ExcelProperty("药品ID")
    private Integer medicineId;

    @Schema(description = "数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("数量")
    private Integer quantity;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "16837")
    @ExcelProperty("单价")
    private BigDecimal price;

    @Schema(description = "用法说明")
    @ExcelProperty("用法说明")
    private String instructions;

}