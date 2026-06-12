package cn.iocoder.yudao.module.hospital.controller.admin.department.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 科室新增/修改 Request VO")
@Data
public class DepartmentSaveReqVO {

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12509")
    private Integer deptId;

    @Schema(description = "科室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "科室名称不能为空")
    private String deptName;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "科室主任")
    private String manager;

    @Schema(description = "科室位置")
    private String location;

    @Schema(description = "科室描述", example = "你猜")
    private String description;

}