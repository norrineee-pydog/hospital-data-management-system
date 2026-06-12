package cn.iocoder.yudao.module.hospital.dal.dataobject.schedule;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 医生排班 DO
 *
 * @author 芋道源码
 */
@TableName("doctor_schedule")
@KeySequence("doctor_schedule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDO extends BaseDO {

    /**
     * 排班ID
     */
    @TableId
    private Integer scheduleId;

    /**
     * 医生ID
     */
    private Integer doctorId;

    /**
     * 工作日期
     */
    private LocalDate workDate;

    /**
     * 时间段：上午/下午/晚上
     */
    private String timeSlot;

    /**
     * 总号量
     */
    private Integer totalNum;

    /**
     * 剩余号量
     */
    private Integer availableNum;

    /**
     * 状态：1正常 0停用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}