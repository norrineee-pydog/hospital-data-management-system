package cn.iocoder.yudao.module.hospital.dal.mysql.visit;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.visit.VisitDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.*;

/**
 * 就诊记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface VisitMapper extends BaseMapperX<VisitDO> {

    default PageResult<VisitDO> selectPage(VisitPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VisitDO>()
                .eqIfPresent(VisitDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(VisitDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(VisitDO::getDeptId, reqVO.getDeptId())
                .betweenIfPresent(VisitDO::getVisitDate, reqVO.getVisitDate())
                .eqIfPresent(VisitDO::getReason, reqVO.getReason())
                .eqIfPresent(VisitDO::getDiagnosis, reqVO.getDiagnosis())
                .eqIfPresent(VisitDO::getNotes, reqVO.getNotes())
                .eqIfPresent(VisitDO::getStatus, reqVO.getStatus())
                .orderByDesc(VisitDO::getId));
    }

}