package cn.iocoder.yudao.module.hospital.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.hospital.controller.admin.doctor.vo.DoctorPageReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DoctorMapper extends BaseMapperX<DoctorDO> {

    default PageResult<DoctorDO> selectPage(DoctorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DoctorDO>()
                .likeIfPresent(DoctorDO::getName, reqVO.getName())

                .eqIfPresent(DoctorDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(DoctorDO::getId));
    }

    // 根据用户ID查询医生
    @Select("SELECT * FROM hospital_doctor WHERE user_id = #{userId} AND deleted = 0")
    DoctorDO selectByUserId(@Param("userId") Long userId);

    // 根据科室ID查询医生列表
    @Select("SELECT * FROM hospital_doctor WHERE dept_id = #{deptId} AND deleted = 0")
    List<DoctorDO> selectListByDeptId(@Param("deptId") Long deptId);
}