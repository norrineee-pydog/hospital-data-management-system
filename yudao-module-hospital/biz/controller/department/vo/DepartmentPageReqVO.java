package cn.iocoder.yudao.module.hospital.controller.admin.department.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 科室分页 Request VO")
@Data
public class DepartmentPageReqVO extends PageParam {

    @Schema(description = "科室名称", example = "张三")
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