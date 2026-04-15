package cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 医生信息分页 Request VO")
@Data
public class DoctorPageReqVO extends PageParam {

    @Schema(description = "关联用户ID", example = "28811")
    private Integer userId;

    @Schema(description = "所属科室ID", example = "30905")
    private Integer deptId;

    @Schema(description = "医生姓名", example = "赵六")
    private String name;

    @Schema(description = "性别")
    private String gender;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "职称")
    private String title;

    @Schema(description = "执业医师证编号")
    private String licenseNo;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

}