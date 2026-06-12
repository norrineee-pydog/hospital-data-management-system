package cn.iocoder.yudao.module.hospital.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.hospital.controller.admin.patient.vo.PatientPageReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PatientMapper extends BaseMapperX<PatientDO> {

    default PageResult<PatientDO> selectPage(PatientPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PatientDO>()
                .likeIfPresent(PatientDO::getName, reqVO.getName())
                .likeIfPresent(PatientDO::getPhone, reqVO.getPhone())
                .likeIfPresent(PatientDO::getIdCard, reqVO.getIdCard())
                .orderByDesc(PatientDO::getId));
    }
    @Select("SELECT * FROM hospital_patient WHERE user_id = #{userId} AND deleted = 0")
    PatientDO selectByUserId(Long userId);
}
