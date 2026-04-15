package cn.iocoder.yudao.module.hospital.controller.admin.ward.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 病房新增/修改 Request VO")
@Data
public class WardSaveReqVO {

    @Schema(description = "病房ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21572")
    private Integer wardId;

    @Schema(description = "所属科室ID", example = "3376")
    private Integer deptId;

    @Schema(description = "病房编号")
    private String wardNo;

    @Schema(description = "病房类型", example = "1")
    private String type;

    @Schema(description = "总床位数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总床位数不能为空")
    private Integer capacity;

    @Schema(description = "已使用床位数")
    private Integer usedBeds;

    @Schema(description = "状态：1可用/0停用", example = "1")
    private Integer status;

    @Schema(description = "描述", example = "随便")
    private String description;

}