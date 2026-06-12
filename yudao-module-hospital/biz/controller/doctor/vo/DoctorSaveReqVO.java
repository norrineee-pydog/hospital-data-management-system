package cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 医生信息新增/修改 Request VO")
@Data
public class DoctorSaveReqVO {

    @Schema(description = "医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31198")
    private Integer doctorId;

    @Schema(description = "关联用户ID", example = "28811")
    private Integer userId;

    @Schema(description = "所属科室ID", example = "30905")
    private Integer deptId;

    @Schema(description = "医生姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "医生姓名不能为空")
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