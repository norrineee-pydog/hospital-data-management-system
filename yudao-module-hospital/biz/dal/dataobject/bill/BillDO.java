package cn.iocoder.yudao.module.hospital.dal.dataobject.bill;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 收费账单 DO
 *
 * @author 芋道源码
 */
@TableName("bill")
@KeySequence("bill_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillDO extends BaseDO {

    /**
     * 账单ID
     */
    @TableId
    private Integer billId;
    /**
     * 就诊记录ID
     */
    private Integer visitId;
    /**
     * 患者ID
     */
    private Integer patientId;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 已支付金额
     */
    private BigDecimal payAmount;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 支付方式
     */
    private String payMethod;
    /**
     * 状态：未支付/已支付/已退费
     */
    private String status;


}