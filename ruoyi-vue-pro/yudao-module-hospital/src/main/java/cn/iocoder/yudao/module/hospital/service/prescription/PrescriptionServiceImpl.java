package cn.iocoder.yudao.module.hospital.service.prescription;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.PrescriptionPageReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.PrescriptionRespVO;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.PrescriptionSaveReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PrescriptionDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.DoctorMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.PatientMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.PrescriptionMapper;
import cn.iocoder.yudao.module.hospital.dal.mysql.VisitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.PRESCRIPTION_NOT_EXISTS;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Resource
    private PrescriptionMapper prescriptionMapper;

    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private VisitMapper visitMapper;

    @Resource
    private PatientMapper patientMapper;

    @Override
    public Long createPrescription(PrescriptionSaveReqVO createReqVO) {
        PrescriptionDO prescription = new PrescriptionDO();
        BeanUtil.copyProperties(createReqVO, prescription);
        prescriptionMapper.insert(prescription);
        return prescription.getId();
    }

    @Override
    public void updatePrescription(PrescriptionSaveReqVO updateReqVO) {
        validatePrescriptionExists(updateReqVO.getId());
        PrescriptionDO updateObj = new PrescriptionDO();
        BeanUtil.copyProperties(updateReqVO, updateObj);
        prescriptionMapper.updateById(updateObj);
    }

    @Override
    public void deletePrescription(Long id) {
        validatePrescriptionExists(id);
        prescriptionMapper.deleteById(id);
    }

    @Override
    public PrescriptionDO getPrescription(Long id) {
        return prescriptionMapper.selectById(id);
    }

    @Override
    public PageResult<PrescriptionDO> getPrescriptionPage(PrescriptionPageReqVO pageReqVO) {
        return prescriptionMapper.selectPage(pageReqVO);
    }

    @Override
    public void dispensePrescription(Long id) {
        // 发药逻辑...
    }

    // ====== 新增：获取带名称的分页列表 ======
    @Override
    public PageResult<PrescriptionRespVO> getPrescriptionPageWithNames(PrescriptionPageReqVO pageReqVO) {
        PageResult<PrescriptionDO> pageResult = prescriptionMapper.selectPage(pageReqVO);

        List<PrescriptionRespVO> respList = pageResult.getList().stream().map(prescription -> {
            PrescriptionRespVO resp = new PrescriptionRespVO();
            BeanUtil.copyProperties(prescription, resp);

            // 填充医生名称
            if (prescription.getDoctorId() != null) {
                DoctorDO doctor = doctorMapper.selectById(prescription.getDoctorId());
                resp.setDoctorName(doctor != null ? doctor.getName() : null);
            }

            // 通过就诊记录查患者名称
            if (prescription.getVisitId() != null) {
                VisitDO visit = visitMapper.selectById(prescription.getVisitId());
                if (visit != null && visit.getPatientId() != null) {
                    PatientDO patient = patientMapper.selectById(visit.getPatientId());
                    resp.setPatientName(patient != null ? patient.getName() : null);
                }
            }

            return resp;
        }).collect(Collectors.toList());

        return new PageResult<>(respList, pageResult.getTotal());
    }

    private void validatePrescriptionExists(Long id) {
        if (id == null) return;
        if (prescriptionMapper.selectById(id) == null) {
            throw exception(PRESCRIPTION_NOT_EXISTS);
        }
    }
}