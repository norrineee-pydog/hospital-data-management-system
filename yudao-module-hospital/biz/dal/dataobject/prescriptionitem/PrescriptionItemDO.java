package cn.iocoder.yudao.module.hospital.dal.dataobject.prescriptionitem;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 处方明细 DO
 *
 * @author 芋道源码
 */
@TableName("prescriptionitem")
@KeySequence("prescriptionitem_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItemDO extends BaseDO {

    /**
     * 明细ID
     */
    @TableId
    private Integer itemId;
    /**
     * 处方ID
     */
    private Integer prescriptionId;
    /**
     * 药品ID
     */
    private Integer medicineId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 用法说明
     */
    private String instructions;


}