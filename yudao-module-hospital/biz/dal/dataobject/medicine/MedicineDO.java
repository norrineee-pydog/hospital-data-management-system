package cn.iocoder.yudao.module.hospital.dal.dataobject.medicine;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 药品信息 DO
 *
 * @author 芋道源码
 */
@TableName("medicine")
@KeySequence("medicine_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDO extends BaseDO {

    /**
     * 药品ID
     */
    @TableId
    private Integer medicineId;
    /**
     * 药品名称
     */
    private String name;
    /**
     * 规格
     */
    private String specification;
    /**
     * 单位
     */
    private String unit;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 库存数量
     */
    private Integer stock;
    /**
     * 生产厂家
     */
    private String manufacturer;
    /**
     * 有效期
     */
    private LocalDate expiryDate;


}