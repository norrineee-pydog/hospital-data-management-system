package cn.iocoder.yudao.module.hospital.dal.mysql.bill;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bill.BillDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.bill.vo.*;

/**
 * 收费账单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BillMapper extends BaseMapperX<BillDO> {

    default PageResult<BillDO> selectPage(BillPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BillDO>()
                .eqIfPresent(BillDO::getVisitId, reqVO.getVisitId())
                .eqIfPresent(BillDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(BillDO::getTotalAmount, reqVO.getTotalAmount())
                .eqIfPresent(BillDO::getPayAmount, reqVO.getPayAmount())
                .betweenIfPresent(BillDO::getPayTime, reqVO.getPayTime())
                .eqIfPresent(BillDO::getPayMethod, reqVO.getPayMethod())
                .eqIfPresent(BillDO::getStatus, reqVO.getStatus())
                .orderByDesc(BillDO::getId));
    }

}