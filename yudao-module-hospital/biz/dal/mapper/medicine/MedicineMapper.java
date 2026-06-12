package cn.iocoder.yudao.module.hospital.dal.mysql.medicine;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.medicine.MedicineDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo.*;

/**
 * 药品信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MedicineMapper extends BaseMapperX<MedicineDO> {

    default PageResult<MedicineDO> selectPage(MedicinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MedicineDO>()
                .likeIfPresent(MedicineDO::getName, reqVO.getName())
                .eqIfPresent(MedicineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(MedicineDO::getUnit, reqVO.getUnit())
                .eqIfPresent(MedicineDO::getPrice, reqVO.getPrice())
                .eqIfPresent(MedicineDO::getStock, reqVO.getStock())
                .eqIfPresent(MedicineDO::getManufacturer, reqVO.getManufacturer())
                .betweenIfPresent(MedicineDO::getExpiryDate, reqVO.getExpiryDate())
                .orderByDesc(MedicineDO::getId));
    }

}