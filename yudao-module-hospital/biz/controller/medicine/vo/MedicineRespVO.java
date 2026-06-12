package cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 药品信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MedicineRespVO {

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27749")
    @ExcelProperty("药品ID")
    private Integer medicineId;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("药品名称")
    private String name;

    @Schema(description = "规格")
    @ExcelProperty("规格")
    private String specification;

    @Schema(description = "单位")
    @ExcelProperty("单位")
    private String unit;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "19953")
    @ExcelProperty("单价")
    private BigDecimal price;

    @Schema(description = "库存数量")
    @ExcelProperty("库存数量")
    private Integer stock;

    @Schema(description = "生产厂家")
    @ExcelProperty("生产厂家")
    private String manufacturer;

    @Schema(description = "有效期")
    @ExcelProperty("有效期")
    private LocalDate expiryDate;

}