package cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 处方 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PrescriptionRespVO {

    @Schema(description = "处方ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4885")
    @ExcelProperty("处方ID")
    private Integer prescriptionId;

    @Schema(description = "就诊记录ID", example = "1337")
    @ExcelProperty("就诊记录ID")
    private Integer visitId;

    @Schema(description = "开方医生ID", example = "22000")
    @ExcelProperty("开方医生ID")
    private Integer doctorId;

    @Schema(description = "开方时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开方时间")
    private LocalDateTime createTime;

    @Schema(description = "状态：未发药/已发药/已过期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态：未发药/已发药/已过期")
    private String status;

    @Schema(description = "处方备注")
    @ExcelProperty("处方备注")
    private String notes;

}