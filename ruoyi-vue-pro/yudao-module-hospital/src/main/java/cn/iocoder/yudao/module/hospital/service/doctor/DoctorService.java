package cn.iocoder.yudao.module.hospital.service.doctor;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.DoctorPageReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.DoctorSaveReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;

import javax.validation.Valid;
import java.util.List;
public interface DoctorService {

    /**
     * 创建医生档案
     */
    Long createDoctor(@Valid DoctorSaveReqVO createReqVO);

    /**
     * 修改医生档案
     */
    void updateDoctor(@Valid DoctorSaveReqVO updateReqVO);

    /**
     * 删除医生档案
     */
    void deleteDoctor(Long id);

    /**
     * 获取医生详情
     */
    DoctorDO getDoctor(Long id);

    /**
     * 获取医生分页列表
     */
    PageResult<DoctorDO> getDoctorPage(DoctorPageReqVO pageReqVO);

    /**
     * 根据用户ID查询医生
     */
    DoctorDO getDoctorByUserId(Long userId);
    /**
     * 根据科室ID获取医生列表
     */
    List<DoctorDO> getDoctorListByDeptId(Long deptId);
}