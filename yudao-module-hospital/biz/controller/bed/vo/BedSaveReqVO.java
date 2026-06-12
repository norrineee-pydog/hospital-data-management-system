package cn.iocoder.yudao.module.hospital.controller.admin.bed.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 床位新增/修改 Request VO")
@Data
public class BedSaveReqVO {

    @Schema(description = "床位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20303")
    private Integer bedId;

    @Schema(description = "病房ID", example = "16625")
    private Integer wardId;

    @Schema(description = "床位号")
    private String bedNo;

    @Schema(description = "状态：空闲/已占用/维护中", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "状态：空闲/已占用/维护中不能为空")
    private String status;

    @Schema(description = "当前入住患者ID", example = "1214")
    private Integer patientId;

    @Schema(description = "入住时间")
    private LocalDateTime admissionTime;

}