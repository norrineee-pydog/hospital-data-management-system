package cn.iocoder.yudao.module.hospital.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 床位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BedRespVO {

    @Schema(description = "床位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20303")
    @ExcelProperty("床位ID")
    private Integer bedId;

    @Schema(description = "病房ID", example = "16625")
    @ExcelProperty("病房ID")
    private Integer wardId;

    @Schema(description = "床位号")
    @ExcelProperty("床位号")
    private String bedNo;

    @Schema(description = "状态：空闲/已占用/维护中", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态：空闲/已占用/维护中")
    private String status;

    @Schema(description = "当前入住患者ID", example = "1214")
    @ExcelProperty("当前入住患者ID")
    private Integer patientId;

    @Schema(description = "入住时间")
    @ExcelProperty("入住时间")
    private LocalDateTime admissionTime;

}