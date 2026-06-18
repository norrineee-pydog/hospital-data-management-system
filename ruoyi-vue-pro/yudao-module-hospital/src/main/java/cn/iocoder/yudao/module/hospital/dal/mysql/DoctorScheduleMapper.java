package cn.iocoder.yudao.module.hospital.dal.mysql;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorScheduleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DoctorScheduleMapper extends BaseMapperX<DoctorScheduleDO> {

    @Select("SELECT DISTINCT work_date FROM doctor_schedule WHERE doctor_id = #{doctorId} AND available_num > 0 AND status = 1 ORDER BY work_date")
    List<LocalDate> selectDistinctWorkDatesByDoctorId(@Param("doctorId") Long doctorId);

    @Select("SELECT * FROM doctor_schedule WHERE doctor_id = #{doctorId} AND work_date = #{workDate} AND available_num > 0 AND status = 1 ORDER BY time_slot")
    List<DoctorScheduleDO> selectByDoctorIdAndWorkDate(@Param("doctorId") Long doctorId, @Param("workDate") LocalDate workDate);
}