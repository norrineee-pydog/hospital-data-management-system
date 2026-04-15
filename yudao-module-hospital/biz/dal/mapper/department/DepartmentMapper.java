package cn.iocoder.yudao.module.hospital.dal.mysql.department;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.hospital.dal.dataobject.department.DepartmentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.hospital.controller.admin.department.vo.*;

/**
 * 科室 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DepartmentMapper extends BaseMapperX<DepartmentDO> {

    default PageResult<DepartmentDO> selectPage(DepartmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DepartmentDO>()
                .likeIfPresent(DepartmentDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(DepartmentDO::getPhone, reqVO.getPhone())
                .eqIfPresent(DepartmentDO::getManager, reqVO.getManager())
                .eqIfPresent(DepartmentDO::getLocation, reqVO.getLocation())
                .eqIfPresent(DepartmentDO::getDescription, reqVO.getDescription())
                .orderByDesc(DepartmentDO::getId));
    }

}