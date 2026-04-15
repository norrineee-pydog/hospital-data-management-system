package cn.iocoder.yudao.module.hospital.service.patient;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.patient.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.patient.PatientDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.patient.PatientMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 病人信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class PatientServiceImpl implements PatientService {

    @Resource
    private PatientMapper patientMapper;

    @Override
    public Integer createPatient(PatientSaveReqVO createReqVO) {
        // 插入
        PatientDO patient = BeanUtils.toBean(createReqVO, PatientDO.class);
        patientMapper.insert(patient);

        // 返回
        return patient.getId();
    }

    @Override
    public void updatePatient(PatientSaveReqVO updateReqVO) {
        // 校验存在
        validatePatientExists(updateReqVO.getId());
        // 更新
        PatientDO updateObj = BeanUtils.toBean(updateReqVO, PatientDO.class);
        patientMapper.updateById(updateObj);
    }

    @Override
    public void deletePatient(Integer id) {
        // 校验存在
        validatePatientExists(id);
        // 删除
        patientMapper.deleteById(id);
    }

    @Override
        public void deletePatientListByIds(List<Integer> ids) {
        // 删除
        patientMapper.deleteByIds(ids);
        }


    private void validatePatientExists(Integer id) {
        if (patientMapper.selectById(id) == null) {
            throw exception(PATIENT_NOT_EXISTS);
        }
    }

    @Override
    public PatientDO getPatient(Integer id) {
        return patientMapper.selectById(id);
    }

    @Override
    public PageResult<PatientDO> getPatientPage(PatientPageReqVO pageReqVO) {
        return patientMapper.selectPage(pageReqVO);
    }

}