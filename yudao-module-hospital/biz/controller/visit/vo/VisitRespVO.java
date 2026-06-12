package cn.iocoder.yudao.module.hospital.controller.admin.visit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 就诊记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VisitRespVO {

    @Schema(description = "就诊ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8812")
    @ExcelProperty("就诊ID")
    private Integer visitId;

    @Schema(description = "患者ID", example = "10921")
    @ExcelProperty("患者ID")
    private Integer patientId;

    @Schema(description = "医生ID", example = "17151")
    @ExcelProperty("医生ID")
    private Integer doctorId;

    @Schema(description = "科室ID", example = "12542")
    @ExcelProperty("科室ID")
    private Integer deptId;

    @Schema(description = "就诊时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("就诊时间")
    private LocalDateTime visitDate;

    @Schema(description = "就诊原因", example = "不好")
    @ExcelProperty("就诊原因")
    private String reason;

    @Schema(description = "诊断结果")
    @ExcelProperty("诊断结果")
    private String diagnosis;

    @Schema(description = "医生备注")
    @ExcelProperty("医生备注")
    private String notes;

    @Schema(description = "状态：待就诊/就诊中/已完成/已取消", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态：待就诊/就诊中/已完成/已取消")
    private String status;

}