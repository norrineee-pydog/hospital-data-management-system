package cn.iocoder.yudao.module.hospital.controller.admin.patient;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.hospital.controller.admin.patient.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.patient.PatientDO;
import cn.iocoder.yudao.module.hospital.service.patient.PatientService;

@Tag(name = "管理后台 - 病人信息")
@RestController
@RequestMapping("/hospital/patient")
@Validated
public class PatientController {

    @Resource
    private PatientService patientService;

    @PostMapping("/create")
    @Operation(summary = "创建病人信息")
    @PreAuthorize("@ss.hasPermission('hospital:patient:create')")
    public CommonResult<Integer> createPatient(@Valid @RequestBody PatientSaveReqVO createReqVO) {
        return success(patientService.createPatient(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新病人信息")
    @PreAuthorize("@ss.hasPermission('hospital:patient:update')")
    public CommonResult<Boolean> updatePatient(@Valid @RequestBody PatientSaveReqVO updateReqVO) {
        patientService.updatePatient(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除病人信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:patient:delete')")
    public CommonResult<Boolean> deletePatient(@RequestParam("id") Integer id) {
        patientService.deletePatient(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除病人信息")
                @PreAuthorize("@ss.hasPermission('hospital:patient:delete')")
    public CommonResult<Boolean> deletePatientList(@RequestParam("ids") List<Integer> ids) {
        patientService.deletePatientListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得病人信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:patient:query')")
    public CommonResult<PatientRespVO> getPatient(@RequestParam("id") Integer id) {
        PatientDO patient = patientService.getPatient(id);
        return success(BeanUtils.toBean(patient, PatientRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得病人信息分页")
    @PreAuthorize("@ss.hasPermission('hospital:patient:query')")
    public CommonResult<PageResult<PatientRespVO>> getPatientPage(@Valid PatientPageReqVO pageReqVO) {
        PageResult<PatientDO> pageResult = patientService.getPatientPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PatientRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出病人信息 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:patient:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPatientExcel(@Valid PatientPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PatientDO> list = patientService.getPatientPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "病人信息.xls", "数据", PatientRespVO.class,
                        BeanUtils.toBean(list, PatientRespVO.class));
    }

}