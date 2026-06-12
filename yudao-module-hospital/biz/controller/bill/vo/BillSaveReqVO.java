package cn.iocoder.yudao.module.hospital.controller.admin.bill.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 收费账单新增/修改 Request VO")
@Data
public class BillSaveReqVO {

    @Schema(description = "账单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12266")
    private Integer billId;

    @Schema(description = "就诊记录ID", example = "9651")
    private Integer visitId;

    @Schema(description = "患者ID", example = "22109")
    private Integer patientId;

    @Schema(description = "总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总金额不能为空")
    private BigDecimal totalAmount;

    @Schema(description = "已支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "状态：未支付/已支付/已退费", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态：未支付/已支付/已退费不能为空")
    private String status;

}