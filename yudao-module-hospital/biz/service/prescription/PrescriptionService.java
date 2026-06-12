package cn.iocoder.yudao.module.hospital.service.prescription;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescription.PrescriptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 处方 Service 接口
 *
 * @author 芋道源码
 */
public interface PrescriptionService {

    /**
     * 创建处方
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPrescription(@Valid PrescriptionSaveReqVO createReqVO);

    /**
     * 更新处方
     *
     * @param updateReqVO 更新信息
     */
    void updatePrescription(@Valid PrescriptionSaveReqVO updateReqVO);

    /**
     * 删除处方
     *
     * @param id 编号
     */
    void deletePrescription(Integer id);

    /**
    * 批量删除处方
    *
    * @param ids 编号
    */
    void deletePrescriptionListByIds(List<Integer> ids);

    /**
     * 获得处方
     *
     * @param id 编号
     * @return 处方
     */
    PrescriptionDO getPrescription(Integer id);

    /**
     * 获得处方分页
     *
     * @param pageReqVO 分页查询
     * @return 处方分页
     */
    PageResult<PrescriptionDO> getPrescriptionPage(PrescriptionPageReqVO pageReqVO);

}