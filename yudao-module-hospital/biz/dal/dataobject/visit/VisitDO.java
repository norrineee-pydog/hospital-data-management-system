package cn.iocoder.yudao.module.hospital.dal.dataobject.visit;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 就诊记录 DO
 *
 * @author 芋道源码
 */
@TableName("visit")
@KeySequence("visit_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDO extends BaseDO {

    /**
     * 就诊ID
     */
    @TableId
    private Integer visitId;
    /**
     * 患者ID
     */
    private Integer patientId;
    /**
     * 医生ID
     */
    private Integer doctorId;
    /**
     * 科室ID
     */
    private Integer deptId;
    /**
     * 就诊时间
     */
    private LocalDateTime visitDate;
    /**
     * 就诊原因
     */
    private String reason;
    /**
     * 诊断结果
     */
    private String diagnosis;
    /**
     * 医生备注
     */
    private String notes;
    /**
     * 状态：待就诊/就诊中/已完成/已取消
     */
    private String status;


}