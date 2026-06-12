package cn.iocoder.yudao.module.hospital.dal.dataobject.prescription;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 处方 DO
 *
 * @author 芋道源码
 */
@TableName("prescription")
@KeySequence("prescription_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDO extends BaseDO {

    /**
     * 处方ID
     */
    @TableId
    private Integer prescriptionId;
    /**
     * 就诊记录ID
     */
    private Integer visitId;
    /**
     * 开方医生ID
     */
    private Integer doctorId;
    /**
     * 状态：未发药/已发药/已过期
     */
    private String status;
    /**
     * 处方备注
     */
    private String notes;


}