package cn.iocoder.yudao.module.hospital.dal.mysql.bed;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bed.BedDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.bed.vo.*;

/**
 * 床位 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BedMapper extends BaseMapperX<BedDO> {

    default PageResult<BedDO> selectPage(BedPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BedDO>()
                .eqIfPresent(BedDO::getWardId, reqVO.getWardId())
                .eqIfPresent(BedDO::getBedNo, reqVO.getBedNo())
                .eqIfPresent(BedDO::getStatus, reqVO.getStatus())
                .eqIfPresent(BedDO::getPatientId, reqVO.getPatientId())
                .betweenIfPresent(BedDO::getAdmissionTime, reqVO.getAdmissionTime())
                .orderByDesc(BedDO::getId));
    }

}