package cn.iocoder.yudao.module.hospital.service.prescription;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescription.PrescriptionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.prescription.PrescriptionMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 处方 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PrescriptionServiceImpl implements PrescriptionService {

    @Resource
    private PrescriptionMapper prescriptionMapper;

    @Override
    public Integer createPrescription(PrescriptionSaveReqVO createReqVO) {
        // 插入
        PrescriptionDO prescription = BeanUtils.toBean(createReqVO, PrescriptionDO.class);
        prescriptionMapper.insert(prescription);

        // 返回
        return prescription.getId();
    }

    @Override
    public void updatePrescription(PrescriptionSaveReqVO updateReqVO) {
        // 校验存在
        validatePrescriptionExists(updateReqVO.getId());
        // 更新
        PrescriptionDO updateObj = BeanUtils.toBean(updateReqVO, PrescriptionDO.class);
        prescriptionMapper.updateById(updateObj);
    }

    @Override
    public void deletePrescription(Integer id) {
        // 校验存在
        validatePrescriptionExists(id);
        // 删除
        prescriptionMapper.deleteById(id);
    }

    @Override
        public void deletePrescriptionListByIds(List<Integer> ids) {
        // 删除
        prescriptionMapper.deleteByIds(ids);
        }


    private void validatePrescriptionExists(Integer id) {
        if (prescriptionMapper.selectById(id) == null) {
            throw exception(PRESCRIPTION_NOT_EXISTS);
        }
    }

    @Override
    public PrescriptionDO getPrescription(Integer id) {
        return prescriptionMapper.selectById(id);
    }

    @Override
    public PageResult<PrescriptionDO> getPrescriptionPage(PrescriptionPageReqVO pageReqVO) {
        return prescriptionMapper.selectPage(pageReqVO);
    }

}