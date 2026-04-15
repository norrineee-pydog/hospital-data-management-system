package cn.iocoder.yudao.module.hospital.dal.dataobject.patient;

import lombok.*;
import java.util.*;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 病人信息 DO
 *
 * @author 芋道源码
 */
@TableName("patient")
@KeySequence("patient_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDO extends BaseDO {

    /**
     * 患者ID
     */
    @TableId
    private Integer patientId;
    /**
     * 关联用户ID
     */
    private Integer userId;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 出生日期
     */
    private LocalDate birthDate;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    /**
     * 紧急联系人电话
     */
    private String emergencyPhone;
    /**
     * 医保卡号
     */
    private String insuranceNo;
    /**
     * 既往病史
     */
    private String medicalHistory;
    /**
     * 最近入院时间
     */
    private LocalDate admissionDate;


}