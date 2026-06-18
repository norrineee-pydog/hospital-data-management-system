package cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 科室响应 VO")
@Data
public class DepartmentRespVO {

    @Schema(description = "科室ID", example = "1")
    private Long deptId;

    @Schema(description = "科室名称", example = "内科")
    private String deptName;

    @Schema(description = "联系电话", example = "021-12345678")
    private String phone;

    @Schema(description = "科室主任", example = "张主任")
    private String manager;

    @Schema(description = "科室位置", example = "1号楼2楼")
    private String location;
}