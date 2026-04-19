package cn.iocoder.yudao.module.hospital.controller.admin.prescription;

import cn.hutool.core.bean.BeanUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PrescriptionDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.VisitMapper;
import cn.iocoder.yudao.module.hospital.service.doctor.DoctorService;
import cn.iocoder.yudao.module.hospital.service.patient.PatientService;
import cn.iocoder.yudao.module.hospital.service.prescription.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 处方管理")
@RestController
@RequestMapping("/hospital/prescription")
@Validated
public class PrescriptionController {

    @Resource
    private PrescriptionService prescriptionService;

    @Resource
    private PatientService patientService;

    @Resource
    private DoctorService doctorService;

    @Resource
    private VisitMapper visitMapper;

    @PostMapping("/create")
    @Operation(summary = "创建处方")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:create')")
    public CommonResult<Long> createPrescription(@Valid @RequestBody PrescriptionSaveReqVO createReqVO) {
        return success(prescriptionService.createPrescription(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改处方")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:update')")
    public CommonResult<Boolean> updatePrescription(@Valid @RequestBody PrescriptionSaveReqVO updateReqVO) {
        prescriptionService.updatePrescription(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除处方")
    @Parameter(name = "id", description = "处方ID", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:prescription:delete')")
    public CommonResult<Boolean> deletePrescription(@RequestParam("id") Long id) {
        prescriptionService.deletePrescription(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取处方详情")
    @Parameter(name = "id", description = "处方ID", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:prescription:query')")
    public CommonResult<PrescriptionRespVO> getPrescription(@RequestParam("id") Long id) {
        PrescriptionDO prescription = prescriptionService.getPrescription(id);
        if (prescription == null) {
            return success(null);
        }

        // ====== 数据权限校验 ======
        Long currentUserId = getLoginUserId();

        // 查是否是病人
        PatientDO patient = patientService.getPatientByUserId(currentUserId);
        if (patient != null) {
            // 病人：只能看自己的处方（通过就诊记录关联）
            VisitDO visit = visitMapper.selectById(prescription.getVisitId());
            if (visit == null || !visit.getPatientId().equals(patient.getId())) {
                return success(null);
            }
        } else {
            // 不是病人，查是否是医生
            DoctorDO doctor = doctorService.getDoctorByUserId(currentUserId);
            if (doctor != null) {
                // 医生：只能看自己开的处方
                if (!prescription.getDoctorId().equals(doctor.getId())) {
                    return success(null);
                }
            }
            // 管理员：不限制
        }
        // =========================

        // 转换为带名称的VO
        PrescriptionRespVO resp = new PrescriptionRespVO();
        BeanUtil.copyProperties(prescription, resp);

        // 填充医生名称
        DoctorDO doctor = doctorService.getDoctor(prescription.getDoctorId());
        resp.setDoctorName(doctor != null ? doctor.getName() : null);

        // 通过就诊记录查患者名称
        VisitDO visit = visitMapper.selectById(prescription.getVisitId());
        if (visit != null) {
            PatientDO pat = patientService.getPatient(visit.getPatientId());
            resp.setPatientName(pat != null ? pat.getName() : null);
        }

        return success(resp);
    }

    @GetMapping("/page")
    @Operation(summary = "获取处方分页列表")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:query')")
    public CommonResult<PageResult<PrescriptionRespVO>> getPrescriptionPage(@Validated PrescriptionPageReqVO pageReqVO) {

        Long currentUserId = getLoginUserId();

        // ====== 数据权限过滤 ======
        PatientDO patient = patientService.getPatientByUserId(currentUserId);
        if (patient != null) {
            // 病人：先查所有，再过滤
            PageResult<PrescriptionRespVO> pageResult = prescriptionService.getPrescriptionPageWithNames(pageReqVO);
            List<PrescriptionRespVO> filteredList = pageResult.getList().stream()
                    .filter(p -> patient.getName().equals(p.getPatientName()))
                    .collect(Collectors.toList());
            return success(new PageResult<>(filteredList, (long) filteredList.size()));
        } else {
            // 不是病人，查是否是医生
            DoctorDO doctor = doctorService.getDoctorByUserId(currentUserId);
            if (doctor != null) {
                // 医生：只能看自己开的处方
                pageReqVO.setDoctorId(doctor.getId());
            }
            // 管理员：不限制
        }
        // =========================

        PageResult<PrescriptionRespVO> pageResult = prescriptionService.getPrescriptionPageWithNames(pageReqVO);
        return success(pageResult);
    }

    @PostMapping("/dispense")
    @Operation(summary = "处方发药")
    @Parameter(name = "id", description = "处方ID", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:prescription:dispense')")
    public CommonResult<Boolean> dispensePrescription(@RequestParam("id") Long id) {
        prescriptionService.dispensePrescription(id);
        return success(true);
    }
}