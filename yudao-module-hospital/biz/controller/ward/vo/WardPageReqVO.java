package cn.iocoder.yudao.module.hospital.controller.admin.ward.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 病房分页 Request VO")
@Data
public class WardPageReqVO extends PageParam {

    @Schema(description = "所属科室ID", example = "3376")
    private Integer deptId;

    @Schema(description = "病房编号")
    private String wardNo;

    @Schema(description = "病房类型", example = "1")
    private String type;

    @Schema(description = "总床位数")
    private Integer capacity;

    @Schema(description = "已使用床位数")
    private Integer usedBeds;

    @Schema(description = "状态：1可用/0停用", example = "1")
    private Integer status;

    @Schema(description = "描述", example = "随便")
    private String description;

}