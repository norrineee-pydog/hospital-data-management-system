package cn.iocoder.yudao.module.hospital.service.patient;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.patient.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.patient.PatientDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 病人信息 Service 接口
 *
 * @author 芋道源码
 */
public interface PatientService {

    /**
     * 创建病人信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPatient(@Valid PatientSaveReqVO createReqVO);

    /**
     * 更新病人信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePatient(@Valid PatientSaveReqVO updateReqVO);

    /**
     * 删除病人信息
     *
     * @param id 编号
     */
    void deletePatient(Integer id);

    /**
    * 批量删除病人信息
    *
    * @param ids 编号
    */
    void deletePatientListByIds(List<Integer> ids);

    /**
     * 获得病人信息
     *
     * @param id 编号
     * @return 病人信息
     */
    PatientDO getPatient(Integer id);

    /**
     * 获得病人信息分页
     *
     * @param pageReqVO 分页查询
     * @return 病人信息分页
     */
    PageResult<PatientDO> getPatientPage(PatientPageReqVO pageReqVO);

}