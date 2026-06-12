package cn.iocoder.yudao.module.hospital.dal.mysql.doctor;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.doctor.DoctorDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.*;

/**
 * 医生信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DoctorMapper extends BaseMapperX<DoctorDO> {

    default PageResult<DoctorDO> selectPage(DoctorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DoctorDO>()
                .eqIfPresent(DoctorDO::getUserId, reqVO.getUserId())
                .eqIfPresent(DoctorDO::getDeptId, reqVO.getDeptId())
                .likeIfPresent(DoctorDO::getName, reqVO.getName())
                .eqIfPresent(DoctorDO::getGender, reqVO.getGender())
                .eqIfPresent(DoctorDO::getAge, reqVO.getAge())
                .eqIfPresent(DoctorDO::getTitle, reqVO.getTitle())
                .eqIfPresent(DoctorDO::getLicenseNo, reqVO.getLicenseNo())
                .eqIfPresent(DoctorDO::getPhone, reqVO.getPhone())
                .eqIfPresent(DoctorDO::getEmail, reqVO.getEmail())
                .orderByDesc(DoctorDO::getId));
    }

}