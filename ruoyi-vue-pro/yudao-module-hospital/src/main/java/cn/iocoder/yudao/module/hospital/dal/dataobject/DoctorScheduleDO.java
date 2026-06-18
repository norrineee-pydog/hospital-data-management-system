package cn.iocoder.yudao.module.hospital.dal.dataobject;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

@TableName("doctor_schedule")
@KeySequence("doctor_schedule_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDO extends BaseDO {

    @TableId
    private Integer scheduleId;
    private Integer doctorId;
    private LocalDate workDate;
    private String timeSlot;
    private Integer totalNum;
    private Integer availableNum;
    private Integer status;
    private String remark;
}