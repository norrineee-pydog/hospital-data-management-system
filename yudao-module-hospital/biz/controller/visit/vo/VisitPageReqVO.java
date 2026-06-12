package cn.iocoder.yudao.module.hospital.controller.admin.visit.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 就诊记录分页 Request VO")
@Data
public class VisitPageReqVO extends PageParam {

    @Schema(description = "患者ID", example = "10921")
    private Integer patientId;

    @Schema(description = "医生ID", example = "17151")
    private Integer doctorId;

    @Schema(description = "科室ID", example = "12542")
    private Integer deptId;

    @Schema(description = "就诊时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] visitDate;

    @Schema(description = "就诊原因", example = "不好")
    private String reason;

    @Schema(description = "诊断结果")
    private String diagnosis;

    @Schema(description = "医生备注")
    private String notes;

    @Schema(description = "状态：待就诊/就诊中/已完成/已取消", example = "2")
    private String status;

}