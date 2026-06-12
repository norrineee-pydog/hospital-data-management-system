package cn.iocoder.yudao.module.hospital.controller.admin.department.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 科室 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DepartmentRespVO {

    @Schema(description = "科室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12509")
    @ExcelProperty("科室ID")
    private Integer deptId;

    @Schema(description = "科室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("科室名称")
    private String deptName;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "科室主任")
    @ExcelProperty("科室主任")
    private String manager;

    @Schema(description = "科室位置")
    @ExcelProperty("科室位置")
    private String location;

    @Schema(description = "科室描述", example = "你猜")
    @ExcelProperty("科室描述")
    private String description;

}