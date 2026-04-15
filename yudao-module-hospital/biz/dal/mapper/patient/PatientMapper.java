package cn.iocoder.yudao.module.hospital.dal.mysql.patient;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.patient.PatientDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.patient.vo.*;

/**
 * 病人信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PatientMapper extends BaseMapperX<PatientDO> {

    default PageResult<PatientDO> selectPage(PatientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PatientDO>()
                .eqIfPresent(PatientDO::getUserId, reqVO.getUserId())
                .likeIfPresent(PatientDO::getName, reqVO.getName())
                .eqIfPresent(PatientDO::getGender, reqVO.getGender())
                .betweenIfPresent(PatientDO::getBirthDate, reqVO.getBirthDate())
                .eqIfPresent(PatientDO::getIdCard, reqVO.getIdCard())
                .eqIfPresent(PatientDO::getPhone, reqVO.getPhone())
                .eqIfPresent(PatientDO::getAddress, reqVO.getAddress())
                .eqIfPresent(PatientDO::getEmergencyContact, reqVO.getEmergencyContact())
                .eqIfPresent(PatientDO::getEmergencyPhone, reqVO.getEmergencyPhone())
                .eqIfPresent(PatientDO::getInsuranceNo, reqVO.getInsuranceNo())
                .eqIfPresent(PatientDO::getMedicalHistory, reqVO.getMedicalHistory())
                .betweenIfPresent(PatientDO::getAdmissionDate, reqVO.getAdmissionDate())
                .orderByDesc(PatientDO::getId));
    }

}