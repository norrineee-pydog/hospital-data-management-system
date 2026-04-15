package cn.iocoder.yudao.module.hospital.dal.dataobject.department;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 科室 DO
 *
 * @author 芋道源码
 */
@TableName("department")
@KeySequence("department_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDO extends BaseDO {

    /**
     * 科室ID
     */
    @TableId
    private Integer deptId;
    /**
     * 科室名称
     */
    private String deptName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 科室主任
     */
    private String manager;
    /**
     * 科室位置
     */
    private String location;
    /**
     * 科室描述
     */
    private String description;


}