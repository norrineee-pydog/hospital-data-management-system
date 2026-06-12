package cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 处方明细分页 Request VO")
@Data
public class PrescriptionItemPageReqVO extends PageParam {

    @Schema(description = "处方ID", example = "13533")
    private Integer prescriptionId;

    @Schema(description = "药品ID", example = "20687")
    private Integer medicineId;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "单价", example = "16837")
    private BigDecimal price;

    @Schema(description = "用法说明")
    private String instructions;

}