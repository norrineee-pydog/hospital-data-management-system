package cn.iocoder.yudao.module.hospital.service.doctor;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.DoctorPageReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.DoctorSaveReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.DoctorMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.DOCTOR_NOT_EXISTS;

@Service
@Validated
public class DoctorServiceImpl implements DoctorService {

    @Resource
    private DoctorMapper doctorMapper;

    @Override
    public Long createDoctor(DoctorSaveReqVO createReqVO) {
        DoctorDO doctor = new DoctorDO();
        BeanUtil.copyProperties(createReqVO, doctor);
        doctorMapper.insert(doctor);
        return doctor.getId();
    }

    @Override
    public void updateDoctor(DoctorSaveReqVO updateReqVO) {
        validateDoctorExists(updateReqVO.getId());
        DoctorDO updateObj = new DoctorDO();
        BeanUtil.copyProperties(updateReqVO, updateObj);
        doctorMapper.updateById(updateObj);
    }

    @Override
    public void deleteDoctor(Long id) {
        validateDoctorExists(id);
        doctorMapper.deleteById(id);
    }

    private void validateDoctorExists(Long id) {
        if (doctorMapper.selectById(id) == null) {
            throw exception(DOCTOR_NOT_EXISTS);
        }
    }

    @Override
    public DoctorDO getDoctor(Long id) {
        return doctorMapper.selectById(id);
    }

    @Override
    public PageResult<DoctorDO> getDoctorPage(DoctorPageReqVO pageReqVO) {
        return doctorMapper.selectPage(pageReqVO);
    }

    @Override
    public DoctorDO getDoctorByUserId(Long userId) {
        return doctorMapper.selectByUserId(userId);
    }

    @Override
    public List<DoctorDO> getDoctorListByDeptId(Long deptId) {
        return doctorMapper.selectListByDeptId(deptId);
    }
}