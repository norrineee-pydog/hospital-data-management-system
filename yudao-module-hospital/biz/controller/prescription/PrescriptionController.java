package cn.iocoder.yudao.module.hospital.controller.admin.prescription;

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

import cn.iocoder.yudao.module.hospital.controller.admin.prescription.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescription.PrescriptionDO;
import cn.iocoder.yudao.module.hospital.service.prescription.PrescriptionService;

@Tag(name = "管理后台 - 处方")
@RestController
@RequestMapping("/hospital/prescription")
@Validated
public class PrescriptionController {

    @Resource
    private PrescriptionService prescriptionService;

    @PostMapping("/create")
    @Operation(summary = "创建处方")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:create')")
    public CommonResult<Integer> createPrescription(@Valid @RequestBody PrescriptionSaveReqVO createReqVO) {
        return success(prescriptionService.createPrescription(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新处方")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:update')")
    public CommonResult<Boolean> updatePrescription(@Valid @RequestBody PrescriptionSaveReqVO updateReqVO) {
        prescriptionService.updatePrescription(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除处方")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:prescription:delete')")
    public CommonResult<Boolean> deletePrescription(@RequestParam("id") Integer id) {
        prescriptionService.deletePrescription(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除处方")
                @PreAuthorize("@ss.hasPermission('hospital:prescription:delete')")
    public CommonResult<Boolean> deletePrescriptionList(@RequestParam("ids") List<Integer> ids) {
        prescriptionService.deletePrescriptionListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得处方")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:query')")
    public CommonResult<PrescriptionRespVO> getPrescription(@RequestParam("id") Integer id) {
        PrescriptionDO prescription = prescriptionService.getPrescription(id);
        return success(BeanUtils.toBean(prescription, PrescriptionRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得处方分页")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:query')")
    public CommonResult<PageResult<PrescriptionRespVO>> getPrescriptionPage(@Valid PrescriptionPageReqVO pageReqVO) {
        PageResult<PrescriptionDO> pageResult = prescriptionService.getPrescriptionPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrescriptionRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出处方 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:prescription:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPrescriptionExcel(@Valid PrescriptionPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PrescriptionDO> list = prescriptionService.getPrescriptionPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "处方.xls", "数据", PrescriptionRespVO.class,
                        BeanUtils.toBean(list, PrescriptionRespVO.class));
    }

}