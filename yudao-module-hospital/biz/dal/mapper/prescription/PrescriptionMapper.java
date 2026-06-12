package cn.iocoder.yudao.module.hospital.dal.mysql.prescription;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescription.PrescriptionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.*;

/**
 * 处方 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PrescriptionMapper extends BaseMapperX<PrescriptionDO> {

    default PageResult<PrescriptionDO> selectPage(PrescriptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrescriptionDO>()
                .eqIfPresent(PrescriptionDO::getVisitId, reqVO.getVisitId())
                .eqIfPresent(PrescriptionDO::getDoctorId, reqVO.getDoctorId())
                .betweenIfPresent(PrescriptionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(PrescriptionDO::getStatus, reqVO.getStatus())
                .eqIfPresent(PrescriptionDO::getNotes, reqVO.getNotes())
                .orderByDesc(PrescriptionDO::getId));
    }

}