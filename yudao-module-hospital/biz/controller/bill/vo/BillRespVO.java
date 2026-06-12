package cn.iocoder.yudao.module.hospital.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 收费账单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BillRespVO {

    @Schema(description = "账单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12266")
    @ExcelProperty("账单ID")
    private Integer billId;

    @Schema(description = "就诊记录ID", example = "9651")
    @ExcelProperty("就诊记录ID")
    private Integer visitId;

    @Schema(description = "患者ID", example = "22109")
    @ExcelProperty("患者ID")
    private Integer patientId;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已支付金额")
    @ExcelProperty("已支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付方式")
    @ExcelProperty("支付方式")
    private String payMethod;

    @Schema(description = "状态：未支付/已支付/已退费", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态：未支付/已支付/已退费")
    private String status;

}