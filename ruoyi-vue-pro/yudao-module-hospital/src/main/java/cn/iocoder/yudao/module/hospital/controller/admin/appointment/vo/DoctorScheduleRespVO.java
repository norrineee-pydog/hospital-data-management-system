package cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Schema(description = "管理后台 - 医生排班响应 VO")
@Data
public class DoctorScheduleRespVO {

    @Schema(description = "排班ID", example = "1")
    private Long scheduleId;

    @Schema(description = "医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "工作日期", example = "2026-06-14")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    @Schema(description = "时间段", example = "08:00-09:00")
    private String timeSlot;

    @Schema(description = "总号量", example = "10")
    private Integer totalNum;

    @Schema(description = "剩余号量", example = "8")
    private Integer availableNum;
}