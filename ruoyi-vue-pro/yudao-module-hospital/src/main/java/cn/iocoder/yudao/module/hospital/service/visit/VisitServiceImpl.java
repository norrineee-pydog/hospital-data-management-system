package cn.iocoder.yudao.module.hospital.service.visit;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitPageReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitRespVO;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitSaveReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DepartmentDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.DepartmentMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.DoctorMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.PatientMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.VisitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.VISIT_NOT_EXISTS;

@Service
public class VisitServiceImpl implements VisitService {

    @Resource
    private VisitMapper visitMapper;

    @Resource
    private PatientMapper patientMapper;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Long createVisit(VisitSaveReqVO createReqVO) {
        VisitDO visit = BeanUtils.toBean(createReqVO, VisitDO.class);
        visitMapper.insert(visit);
        return visit.getId();
    }

    @Override
    public void updateVisit(VisitSaveReqVO updateReqVO) {
        validateVisitExists(updateReqVO.getId());
        VisitDO updateObj = BeanUtils.toBean(updateReqVO, VisitDO.class);
        visitMapper.updateById(updateObj);
    }

    @Override
    public void deleteVisit(Long id) {
        validateVisitExists(id);
        visitMapper.deleteById(id);
    }

    @Override
    public VisitDO getVisit(Long id) {
        return visitMapper.selectById(id);
    }

    @Override
    public PageResult<VisitDO> getVisitPage(VisitPageReqVO pageReqVO) {
        return visitMapper.selectPage(pageReqVO);
    }

    // ====== 新增：获取带名称的分页列表 ======
    @Override
    public PageResult<VisitRespVO> getVisitPageWithNames(VisitPageReqVO pageReqVO) {
        PageResult<VisitDO> pageResult = visitMapper.selectPage(pageReqVO);

        List<VisitRespVO> respList = pageResult.getList().stream().map(visit -> {
            VisitRespVO resp = new VisitRespVO();
            BeanUtil.copyProperties(visit, resp);

            // 填充患者名称
            if (visit.getPatientId() != null) {
                PatientDO patient = patientMapper.selectById(visit.getPatientId());
                resp.setPatientName(patient != null ? patient.getName() : null);
            }

            // 填充医生名称
            if (visit.getDoctorId() != null) {
                DoctorDO doctor = doctorMapper.selectById(visit.getDoctorId());
                resp.setDoctorName(doctor != null ? doctor.getName() : null);
            }

            // 填充科室名称
            if (visit.getDeptId() != null) {
                DepartmentDO dept = departmentMapper.selectById(visit.getDeptId());
                resp.setDeptName(dept != null ? dept.getDeptName() : null);
            }

            return resp;
        }).collect(Collectors.toList());

        return new PageResult<>(respList, pageResult.getTotal());
    }

    private void validateVisitExists(Long id) {
        if (id == null) return;
        if (visitMapper.selectById(id) == null) throw exception(VISIT_NOT_EXISTS);
    }

}