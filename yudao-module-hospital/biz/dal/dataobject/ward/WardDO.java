package cn.iocoder.yudao.module.hospital.dal.dataobject.ward;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 病房 DO
 *
 * @author 芋道源码
 */
@TableName("ward")
@KeySequence("ward_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardDO extends BaseDO {

    /**
     * 病房ID
     */
    @TableId
    private Integer wardId;
    /**
     * 所属科室ID
     */
    private Integer deptId;
    /**
     * 病房编号
     */
    private String wardNo;
    /**
     * 病房类型
     */
    private String type;
    /**
     * 总床位数
     */
    private Integer capacity;
    /**
     * 已使用床位数
     */
    private Integer usedBeds;
    /**
     * 状态：1可用/0停用
     */
    private Integer status;
    /**
     * 描述
     */
    private String description;


}