package cn.iocoder.yudao.module.hospital.dal.mysql;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitPageReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VisitMapper extends BaseMapperX<VisitDO> {

    default PageResult<VisitDO> selectPage(VisitPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<VisitDO>()
                .eqIfPresent(VisitDO::getPatientId, reqVO.getPatientId())
                .eqIfPresent(VisitDO::getDoctorId, reqVO.getDoctorId())
                .eqIfPresent(VisitDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(VisitDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(VisitDO::getVisitDate, reqVO.getVisitDate())
                .orderByDesc(VisitDO::getId));
    }

    // 关联查询：获取就诊详情（包含患者、医生、科室名称）
    @Select("SELECT v.*, " +
            "p.name as patient_name, " +
            "d.name as doctor_name, " +
            "dept.dept_name as dept_name " +
            "FROM hospital_visit v " +
            "LEFT JOIN hospital_patient p ON v.patient_id = p.id " +
            "LEFT JOIN hospital_doctor d ON v.doctor_id = d.id " +
            "LEFT JOIN hospital_department dept ON v.dept_id = dept.id " +
            "WHERE v.id = #{id} AND v.deleted = 0")
    VisitDO selectByIdWithNames(@Param("id") Long id);

    // 关联查询分页列表（包含名称）
    default PageResult<VisitDO> selectPageWithNames(VisitPageReqVO reqVO) {
        // 先用基础条件查询ID列表，再关联查询详情
        // 或者写自定义SQL
        return selectPage(reqVO); // 先保持原样，后面优化
    }
}