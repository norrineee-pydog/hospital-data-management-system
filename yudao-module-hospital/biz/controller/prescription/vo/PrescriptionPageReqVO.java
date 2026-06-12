package cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 处方分页 Request VO")
@Data
public class PrescriptionPageReqVO extends PageParam {

    @Schema(description = "就诊记录ID", example = "1337")
    private Integer visitId;

    @Schema(description = "开方医生ID", example = "22000")
    private Integer doctorId;

    @Schema(description = "开方时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态：未发药/已发药/已过期", example = "2")
    private String status;

    @Schema(description = "处方备注")
    private String notes;

}