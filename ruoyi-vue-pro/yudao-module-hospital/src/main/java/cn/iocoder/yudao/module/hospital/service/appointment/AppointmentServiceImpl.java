package cn.iocoder.yudao.module.hospital.service.appointment;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.module.hospital.controller.admin.appointment.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DepartmentDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorScheduleDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.DepartmentMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.DoctorMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.PatientMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.VisitMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.DoctorScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Resource
    private VisitMapper visitMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private PatientMapper patientMapper;

    @Resource
    private DoctorScheduleMapper doctorScheduleMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ========== 取消挂号 ==========
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAppointment(Long visitId) {
        VisitDO visit = visitMapper.selectById(visitId);
        if (visit == null) {
            throw ServiceExceptionUtil.exception(VISIT_NOT_EXISTS);
        }
        String status = visit.getStatus();
        if (!"待就诊".equals(status)) {
            throw ServiceExceptionUtil.exception(VISIT_STATUS_CANT_CANCEL);
        }
        VisitDO updateVisit = new VisitDO();
        updateVisit.setId(visitId);
        updateVisit.setStatus("已取消");
        visitMapper.updateById(updateVisit);
    }

    // ========== 预约挂号 ==========

    @Override
    public List<DepartmentRespVO> getDepartmentList() {
        List<DepartmentDO> departments = departmentMapper.selectList(null);
        return departments.stream().map(dept -> {
            DepartmentRespVO vo = new DepartmentRespVO();
            vo.setDeptId(dept.getId());
            vo.setDeptName(dept.getDeptName());
            vo.setPhone(dept.getPhone());
            vo.setManager(dept.getManager());
            vo.setLocation(dept.getLocation());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DoctorSimpleRespVO> getDoctorListByDeptId(Long deptId) {
        List<DoctorDO> doctors = doctorMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<DoctorDO>()
                        .eq(DoctorDO::getDeptId, deptId)
        );
        return doctors.stream().map(doc -> {
            DoctorSimpleRespVO vo = new DoctorSimpleRespVO();
            vo.setDoctorId(doc.getId());
            vo.setName(doc.getName());
            vo.setTitle(doc.getTitle());
            vo.setGender(doc.getGender());
            vo.setAge(doc.getAge());
            vo.setPhone(doc.getPhone());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getScheduleDates(Long doctorId) {
        List<LocalDate> dates = doctorScheduleMapper.selectDistinctWorkDatesByDoctorId(doctorId);
        return dates.stream().map(date -> date.format(DATE_FORMATTER)).collect(Collectors.toList());
    }

    @Override
    public List<DoctorScheduleRespVO> getScheduleList(Long doctorId, String date) {
        LocalDate workDate = LocalDate.parse(date, DATE_FORMATTER);
        List<DoctorScheduleDO> schedules = doctorScheduleMapper.selectByDoctorIdAndWorkDate(doctorId, workDate);
        return schedules.stream().map(s -> {
            DoctorScheduleRespVO vo = new DoctorScheduleRespVO();
            vo.setScheduleId(Long.valueOf(s.getScheduleId()));
            vo.setDoctorId(Long.valueOf(s.getDoctorId()));
            vo.setWorkDate(s.getWorkDate());
            vo.setTimeSlot(s.getTimeSlot());
            vo.setTotalNum(s.getTotalNum());
            vo.setAvailableNum(s.getAvailableNum());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createAppointment(AppointmentCreateReqVO reqVO) {
        // 1. 校验患者存在
        PatientDO patient = patientMapper.selectById(reqVO.getPatientId());
        if (patient == null) {
            throw ServiceExceptionUtil.exception(PATIENT_NOT_EXISTS);
        }

        // 2. 校验医生存在
        DoctorDO doctor = doctorMapper.selectById(reqVO.getDoctorId());
        if (doctor == null) {
            throw ServiceExceptionUtil.exception(DOCTOR_NOT_EXISTS);
        }

        // 3. 校验排班存在
        DoctorScheduleDO schedule = doctorScheduleMapper.selectById(reqVO.getScheduleId());
        if (schedule == null) {
            throw ServiceExceptionUtil.exception(SCHEDULE_NOT_EXISTS);
        }

        // 4. 构造就诊时间
        String timeSlot = schedule.getTimeSlot();
        String startTime = timeSlot.split("-")[0];
        LocalDateTime visitDate = LocalDateTime.of(schedule.getWorkDate(), LocalTime.parse(startTime));

        // 5. 创建就诊记录
        VisitDO visit = new VisitDO();
        visit.setPatientId(reqVO.getPatientId());
        visit.setDoctorId(reqVO.getDoctorId());
        visit.setDeptId(reqVO.getDeptId());
        visit.setVisitDate(visitDate);
        visit.setScheduleId(reqVO.getScheduleId());
        visit.setReason(reqVO.getReason());
        visit.setStatus("待就诊");

        // 6. 包裹插入操作，捕获数据库触发器异常
        try {
            visitMapper.insert(visit);
        } catch (Exception e) {
            // 获取最底层的 SQL 异常信息
            Throwable rootCause = e;
            while (rootCause.getCause() != null && rootCause != rootCause.getCause()) {
                rootCause = rootCause.getCause();
            }

            String errorMsg = rootCause.getMessage();
            if (errorMsg != null) {
                // 拦截号源已满异常
                if (errorMsg.contains("号源已满")) {
                    throw ServiceExceptionUtil.exception(SCHEDULE_NO_AVAILABLE);
                }
                // 拦截重复挂号异常
                else if (errorMsg.contains("重复挂号")) {
                    throw ServiceExceptionUtil.exception(VISIT_DUPLICATE);
                }
            }
            // 其他异常继续抛出
            throw e;
        }

        return visit.getId().intValue();
    }
}