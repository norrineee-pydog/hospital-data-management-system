package cn.iocoder.yudao.module.hospital.controller.admin.bill.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 收费账单分页 Request VO")
@Data
public class BillPageReqVO extends PageParam {

    @Schema(description = "就诊记录ID", example = "9651")
    private Integer visitId;

    @Schema(description = "患者ID", example = "22109")
    private Integer patientId;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "已支付金额")
    private BigDecimal payAmount;

    @Schema(description = "支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "支付方式")
    private String payMethod;

    @Schema(description = "状态：未支付/已支付/已退费", example = "1")
    private String status;

}