package cn.iocoder.yudao.module.hospital.service.prescriptionitem;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescriptionitem.PrescriptionItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 处方明细 Service 接口
 *
 * @author 芋道源码
 */
public interface PrescriptionItemService {

    /**
     * 创建处方明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createPrescriptionItem(@Valid PrescriptionItemSaveReqVO createReqVO);

    /**
     * 更新处方明细
     *
     * @param updateReqVO 更新信息
     */
    void updatePrescriptionItem(@Valid PrescriptionItemSaveReqVO updateReqVO);

    /**
     * 删除处方明细
     *
     * @param id 编号
     */
    void deletePrescriptionItem(Integer id);

    /**
    * 批量删除处方明细
    *
    * @param ids 编号
    */
    void deletePrescriptionItemListByIds(List<Integer> ids);

    /**
     * 获得处方明细
     *
     * @param id 编号
     * @return 处方明细
     */
    PrescriptionItemDO getPrescriptionItem(Integer id);

    /**
     * 获得处方明细分页
     *
     * @param pageReqVO 分页查询
     * @return 处方明细分页
     */
    PageResult<PrescriptionItemDO> getPrescriptionItemPage(PrescriptionItemPageReqVO pageReqVO);

}