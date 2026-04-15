package cn.iocoder.yudao.module.hospital.dal.mysql.prescriptionitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescriptionitem.PrescriptionItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo.*;

/**
 * 处方明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface PrescriptionItemMapper extends BaseMapperX<PrescriptionItemDO> {

    default PageResult<PrescriptionItemDO> selectPage(PrescriptionItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PrescriptionItemDO>()
                .eqIfPresent(PrescriptionItemDO::getPrescriptionId, reqVO.getPrescriptionId())
                .eqIfPresent(PrescriptionItemDO::getMedicineId, reqVO.getMedicineId())
                .eqIfPresent(PrescriptionItemDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(PrescriptionItemDO::getPrice, reqVO.getPrice())
                .eqIfPresent(PrescriptionItemDO::getInstructions, reqVO.getInstructions())
                .orderByDesc(PrescriptionItemDO::getId));
    }

}