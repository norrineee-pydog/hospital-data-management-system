package cn.iocoder.yudao.module.hospital.controller.admin.bed.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 床位分页 Request VO")
@Data
public class BedPageReqVO extends PageParam {

    @Schema(description = "病房ID", example = "16625")
    private Integer wardId;

    @Schema(description = "床位号")
    private String bedNo;

    @Schema(description = "状态：空闲/已占用/维护中", example = "1")
    private String status;

    @Schema(description = "当前入住患者ID", example = "1214")
    private Integer patientId;

    @Schema(description = "入住时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] admissionTime;

}