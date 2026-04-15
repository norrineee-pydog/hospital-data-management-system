package cn.iocoder.yudao.module.hospital.dal.dataobject.bed;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 床位 DO
 *
 * @author 芋道源码
 */
@TableName("bed")
@KeySequence("bed_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BedDO extends BaseDO {

    /**
     * 床位ID
     */
    @TableId
    private Integer bedId;
    /**
     * 病房ID
     */
    private Integer wardId;
    /**
     * 床位号
     */
    private String bedNo;
    /**
     * 状态：空闲/已占用/维护中
     */
    private String status;
    /**
     * 当前入住患者ID
     */
    private Integer patientId;
    /**
     * 入住时间
     */
    private LocalDateTime admissionTime;


}