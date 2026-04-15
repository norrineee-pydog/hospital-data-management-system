package cn.iocoder.yudao.module.hospital.dal.mysql.ward;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.ward.WardDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.ward.vo.*;

/**
 * 病房 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface WardMapper extends BaseMapperX<WardDO> {

    default PageResult<WardDO> selectPage(WardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<WardDO>()
                .eqIfPresent(WardDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(WardDO::getWardNo, reqVO.getWardNo())
                .eqIfPresent(WardDO::getType, reqVO.getType())
                .eqIfPresent(WardDO::getCapacity, reqVO.getCapacity())
                .eqIfPresent(WardDO::getUsedBeds, reqVO.getUsedBeds())
                .eqIfPresent(WardDO::getStatus, reqVO.getStatus())
                .eqIfPresent(WardDO::getDescription, reqVO.getDescription())
                .orderByDesc(WardDO::getId));
    }

}