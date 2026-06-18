package cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 医生简要响应 VO")
@Data
public class DoctorSimpleRespVO {

    @Schema(description = "医生ID", example = "1")
    private Long doctorId;

    @Schema(description = "医生姓名", example = "张医生")
    private String name;

    @Schema(description = "职称", example = "主任医师")
    private String title;

    @Schema(description = "性别", example = "男")
    private String gender;

    @Schema(description = "年龄", example = "45")
    private Integer age;

    @Schema(description = "联系电话", example = "13800000001")
    private String phone;
}