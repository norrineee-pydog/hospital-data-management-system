package cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 医生信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DoctorRespVO {

    @Schema(description = "医生ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31198")
    @ExcelProperty("医生ID")
    private Integer doctorId;

    @Schema(description = "关联用户ID", example = "28811")
    @ExcelProperty("关联用户ID")
    private Integer userId;

    @Schema(description = "所属科室ID", example = "30905")
    @ExcelProperty("所属科室ID")
    private Integer deptId;

    @Schema(description = "医生姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("医生姓名")
    private String name;

    @Schema(description = "性别")
    @ExcelProperty("性别")
    private String gender;

    @Schema(description = "年龄")
    @ExcelProperty("年龄")
    private Integer age;

    @Schema(description = "职称")
    @ExcelProperty("职称")
    private String title;

    @Schema(description = "执业医师证编号")
    @ExcelProperty("执业医师证编号")
    private String licenseNo;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

}