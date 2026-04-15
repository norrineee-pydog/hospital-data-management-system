package cn.iocoder.yudao.module.hospital.controller.admin.patient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 病人信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PatientRespVO {

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8232")
    @ExcelProperty("患者ID")
    private Integer patientId;

    @Schema(description = "关联用户ID", example = "11806")
    @ExcelProperty("关联用户ID")
    private Integer userId;

    @Schema(description = "患者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("患者姓名")
    private String name;

    @Schema(description = "性别")
    @ExcelProperty("性别")
    private String gender;

    @Schema(description = "出生日期")
    @ExcelProperty("出生日期")
    private LocalDate birthDate;

    @Schema(description = "身份证号")
    @ExcelProperty("身份证号")
    private String idCard;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "家庭住址")
    @ExcelProperty("家庭住址")
    private String address;

    @Schema(description = "紧急联系人")
    @ExcelProperty("紧急联系人")
    private String emergencyContact;

    @Schema(description = "紧急联系人电话")
    @ExcelProperty("紧急联系人电话")
    private String emergencyPhone;

    @Schema(description = "医保卡号")
    @ExcelProperty("医保卡号")
    private String insuranceNo;

    @Schema(description = "既往病史")
    @ExcelProperty("既往病史")
    private String medicalHistory;

    @Schema(description = "最近入院时间")
    @ExcelProperty("最近入院时间")
    private LocalDate admissionDate;

}