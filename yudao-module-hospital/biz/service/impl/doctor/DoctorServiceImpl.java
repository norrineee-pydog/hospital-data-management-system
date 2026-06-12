package cn.iocoder.yudao.module.hospital.service.doctor;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.doctor.DoctorDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.doctor.DoctorMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 医生信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private DoctorMapper doctorMapper;

    @Override
    public Integer createDoctor(DoctorSaveReqVO createReqVO) {
        // 插入
        DoctorDO doctor = BeanUtils.toBean(createReqVO, DoctorDO.class);
        doctorMapper.insert(doctor);

        // 返回
        return doctor.getId();
    }

    @Override
    public void updateDoctor(DoctorSaveReqVO updateReqVO) {
        // 校验存在
        validateDoctorExists(updateReqVO.getId());
        // 更新
        DoctorDO updateObj = BeanUtils.toBean(updateReqVO, DoctorDO.class);
        doctorMapper.updateById(updateObj);
    }

    @Override
    public void deleteDoctor(Integer id) {
        // 校验存在
        validateDoctorExists(id);
        // 删除
        doctorMapper.deleteById(id);
    }

    @Override
        public void deleteDoctorListByIds(List<Integer> ids) {
        // 删除
        doctorMapper.deleteByIds(ids);
        }


    private void validateDoctorExists(Integer id) {
        if (doctorMapper.selectById(id) == null) {
            throw exception(DOCTOR_NOT_EXISTS);
        }
    }

    @Override
    public DoctorDO getDoctor(Integer id) {
        return doctorMapper.selectById(id);
    }

    @Override
    public PageResult<DoctorDO> getDoctorPage(DoctorPageReqVO pageReqVO) {
        return doctorMapper.selectPage(pageReqVO);
    }

}