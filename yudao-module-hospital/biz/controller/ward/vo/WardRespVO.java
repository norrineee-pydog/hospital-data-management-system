package cn.iocoder.yudao.module.hospital.controller.admin.ward.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 病房 Response VO")
@Data
@ExcelIgnoreUnannotated
public class WardRespVO {

    @Schema(description = "病房ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21572")
    @ExcelProperty("病房ID")
    private Integer wardId;

    @Schema(description = "所属科室ID", example = "3376")
    @ExcelProperty("所属科室ID")
    private Integer deptId;

    @Schema(description = "病房编号")
    @ExcelProperty("病房编号")
    private String wardNo;

    @Schema(description = "病房类型", example = "1")
    @ExcelProperty("病房类型")
    private String type;

    @Schema(description = "总床位数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总床位数")
    private Integer capacity;

    @Schema(description = "已使用床位数")
    @ExcelProperty("已使用床位数")
    private Integer usedBeds;

    @Schema(description = "状态：1可用/0停用", example = "1")
    @ExcelProperty("状态：1可用/0停用")
    private Integer status;

    @Schema(description = "描述", example = "随便")
    @ExcelProperty("描述")
    private String description;

}