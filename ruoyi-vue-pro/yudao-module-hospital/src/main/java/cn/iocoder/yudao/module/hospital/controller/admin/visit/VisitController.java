package cn.iocoder.yudao.module.hospital.controller.admin.visit;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.DoctorDO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.PatientDO;
import cn.iocoder.yudao.module.hospital.service.doctor.DoctorService;
import cn.iocoder.yudao.module.hospital.service.patient.PatientService;
import cn.iocoder.yudao.module.hospital.service.visit.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Collections;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 就诊管理")
@RestController
@RequestMapping("/hospital/visit")
@Validated
public class VisitController {

    @Resource
    private VisitService visitService;

    @Resource
    private PatientService patientService;

    @Resource
    private DoctorService doctorService;

    @PostMapping("/create")
    @Operation(summary = "创建就诊记录")
    @PreAuthorize("@ss.hasPermission('hospital:visit:create')")
    public CommonResult<Long> createVisit(@Valid @RequestBody VisitSaveReqVO createReqVO) {
        return success(visitService.createVisit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改就诊记录")
    @PreAuthorize("@ss.hasPermission('hospital:visit:update')")
    public CommonResult<Boolean> updateVisit(@Valid @RequestBody VisitSaveReqVO updateReqVO) {
        visitService.updateVisit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除就诊记录")
    @Parameter(name = "id", description = "就诊ID", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:visit:delete')")
    public CommonResult<Boolean> deleteVisit(@RequestParam("id") Long id) {
        visitService.deleteVisit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取就诊详情")
    @Parameter(name = "id", description = "就诊ID", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:visit:query')")
    public CommonResult<VisitRespVO> getVisit(@RequestParam("id") Long id) {
        VisitDO visit = visitService.getVisit(id);
        if (visit == null) {
            return success(null);
        }

        // ====== 数据权限校验 ======
        Long currentUserId = getLoginUserId();

        // 查是否是病人
        PatientDO patient = patientService.getPatientByUserId(currentUserId);
        if (patient != null) {
            // 病人：只能看自己的就诊记录
            if (!visit.getPatientId().equals(patient.getId())) {
                return success(null); // 无权限，返回空
            }
        } else {
            // 不是病人，查是否是医生
            DoctorDO doctor = doctorService.getDoctorByUserId(currentUserId);
            if (doctor != null) {
                // 医生：只能看自己的接诊记录
                if (!visit.getDoctorId().equals(doctor.getId())) {
                    return success(null); // 无权限，返回空
                }
            }
            // 管理员：不限制
        }
        // =========================

        return success(BeanUtils.toBean(visit, VisitRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获取就诊分页列表")
    @PreAuthorize("@ss.hasPermission('hospital:visit:query')")
    public CommonResult<PageResult<VisitRespVO>> getVisitPage(@Validated VisitPageReqVO pageReqVO) {

        Long currentUserId = getLoginUserId();

        // 数据权限过滤
        PatientDO patient = patientService.getPatientByUserId(currentUserId);
        if (patient != null) {
            pageReqVO.setPatientId(patient.getId());
        } else {
            DoctorDO doctor = doctorService.getDoctorByUserId(currentUserId);
            if (doctor != null) {
                pageReqVO.setDoctorId(doctor.getId());
            }
        }

        // 使用带名称的方法
        PageResult<VisitRespVO> pageResult = visitService.getVisitPageWithNames(pageReqVO);
        return success(pageResult);
    }
}