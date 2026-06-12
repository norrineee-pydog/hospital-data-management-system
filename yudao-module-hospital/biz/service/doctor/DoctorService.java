package cn.iocoder.yudao.module.hospital.service.doctor;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.doctor.DoctorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 医生信息 Service 接口
 *
 * @author 芋道源码
 */
public interface DoctorService {

    /**
     * 创建医生信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createDoctor(@Valid DoctorSaveReqVO createReqVO);

    /**
     * 更新医生信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDoctor(@Valid DoctorSaveReqVO updateReqVO);

    /**
     * 删除医生信息
     *
     * @param id 编号
     */
    void deleteDoctor(Integer id);

    /**
    * 批量删除医生信息
    *
    * @param ids 编号
    */
    void deleteDoctorListByIds(List<Integer> ids);

    /**
     * 获得医生信息
     *
     * @param id 编号
     * @return 医生信息
     */
    DoctorDO getDoctor(Integer id);

    /**
     * 获得医生信息分页
     *
     * @param pageReqVO 分页查询
     * @return 医生信息分页
     */
    PageResult<DoctorDO> getDoctorPage(DoctorPageReqVO pageReqVO);

}