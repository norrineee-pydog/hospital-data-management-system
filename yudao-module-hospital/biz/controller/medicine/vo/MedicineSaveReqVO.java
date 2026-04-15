package cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 药品信息新增/修改 Request VO")
@Data
public class MedicineSaveReqVO {

    @Schema(description = "药品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27749")
    private Integer medicineId;

    @Schema(description = "药品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "药品名称不能为空")
    private String name;

    @Schema(description = "规格")
    private String specification;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "19953")
    @NotNull(message = "单价不能为空")
    private BigDecimal price;

    @Schema(description = "库存数量")
    private Integer stock;

    @Schema(description = "生产厂家")
    private String manufacturer;

    @Schema(description = "有效期")
    private LocalDate expiryDate;

}