package cn.iocoder.yudao.module.hospital.dal.dataobject.doctor;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 医生信息 DO
 *
 * @author 芋道源码
 */
@TableName("doctor")
@KeySequence("doctor_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDO extends BaseDO {

    /**
     * 医生ID
     */
    @TableId
    private Integer doctorId;
    /**
     * 关联用户ID
     */
    private Integer userId;
    /**
     * 所属科室ID
     */
    private Integer deptId;
    /**
     * 医生姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 职称
     */
    private String title;
    /**
     * 执业医师证编号
     */
    private String licenseNo;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;


}