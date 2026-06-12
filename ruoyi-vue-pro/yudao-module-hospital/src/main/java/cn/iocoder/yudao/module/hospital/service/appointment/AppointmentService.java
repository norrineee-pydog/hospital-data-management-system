package cn.iocoder.yudao.module.hospital.service.appointment;

public interface AppointmentService {

    /**
     * 取消挂号
     * @param visitId 就诊记录ID
     */
    void cancelAppointment(Long visitId);

}