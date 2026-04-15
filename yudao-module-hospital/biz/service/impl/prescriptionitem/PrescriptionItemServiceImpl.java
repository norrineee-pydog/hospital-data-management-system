package cn.iocoder.yudao.module.hospital.service.prescriptionitem;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescriptionitem.PrescriptionItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.prescriptionitem.PrescriptionItemMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 处方明细 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PrescriptionItemServiceImpl implements PrescriptionItemService {

    @Resource
    private PrescriptionItemMapper prescriptionItemMapper;

    @Override
    public Integer createPrescriptionItem(PrescriptionItemSaveReqVO createReqVO) {
        // 插入
        PrescriptionItemDO prescriptionItem = BeanUtils.toBean(createReqVO, PrescriptionItemDO.class);
        prescriptionItemMapper.insert(prescriptionItem);

        // 返回
        return prescriptionItem.getId();
    }

    @Override
    public void updatePrescriptionItem(PrescriptionItemSaveReqVO updateReqVO) {
        // 校验存在
        validatePrescriptionItemExists(updateReqVO.getId());
        // 更新
        PrescriptionItemDO updateObj = BeanUtils.toBean(updateReqVO, PrescriptionItemDO.class);
        prescriptionItemMapper.updateById(updateObj);
    }

    @Override
    public void deletePrescriptionItem(Integer id) {
        // 校验存在
        validatePrescriptionItemExists(id);
        // 删除
        prescriptionItemMapper.deleteById(id);
    }

    @Override
        public void deletePrescriptionItemListByIds(List<Integer> ids) {
        // 删除
        prescriptionItemMapper.deleteByIds(ids);
        }


    private void validatePrescriptionItemExists(Integer id) {
        if (prescriptionItemMapper.selectById(id) == null) {
            throw exception(PRESCRIPTION_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public PrescriptionItemDO getPrescriptionItem(Integer id) {
        return prescriptionItemMapper.selectById(id);
    }

    @Override
    public PageResult<PrescriptionItemDO> getPrescriptionItemPage(PrescriptionItemPageReqVO pageReqVO) {
        return prescriptionItemMapper.selectPage(pageReqVO);
    }

}