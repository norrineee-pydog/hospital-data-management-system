package cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem;

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

import cn.iocoder.yudao.module.hospital.controller.admin.prescriptionitem.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.prescriptionitem.PrescriptionItemDO;
import cn.iocoder.yudao.module.hospital.service.prescriptionitem.PrescriptionItemService;

@Tag(name = "管理后台 - 处方明细")
@RestController
@RequestMapping("/hospital/prescription-item")
@Validated
public class PrescriptionItemController {

    @Resource
    private PrescriptionItemService prescriptionItemService;

    @PostMapping("/create")
    @Operation(summary = "创建处方明细")
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:create')")
    public CommonResult<Integer> createPrescriptionItem(@Valid @RequestBody PrescriptionItemSaveReqVO createReqVO) {
        return success(prescriptionItemService.createPrescriptionItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新处方明细")
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:update')")
    public CommonResult<Boolean> updatePrescriptionItem(@Valid @RequestBody PrescriptionItemSaveReqVO updateReqVO) {
        prescriptionItemService.updatePrescriptionItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除处方明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:delete')")
    public CommonResult<Boolean> deletePrescriptionItem(@RequestParam("id") Integer id) {
        prescriptionItemService.deletePrescriptionItem(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除处方明细")
                @PreAuthorize("@ss.hasPermission('hospital:prescription-item:delete')")
    public CommonResult<Boolean> deletePrescriptionItemList(@RequestParam("ids") List<Integer> ids) {
        prescriptionItemService.deletePrescriptionItemListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得处方明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:query')")
    public CommonResult<PrescriptionItemRespVO> getPrescriptionItem(@RequestParam("id") Integer id) {
        PrescriptionItemDO prescriptionItem = prescriptionItemService.getPrescriptionItem(id);
        return success(BeanUtils.toBean(prescriptionItem, PrescriptionItemRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得处方明细分页")
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:query')")
    public CommonResult<PageResult<PrescriptionItemRespVO>> getPrescriptionItemPage(@Valid PrescriptionItemPageReqVO pageReqVO) {
        PageResult<PrescriptionItemDO> pageResult = prescriptionItemService.getPrescriptionItemPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PrescriptionItemRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出处方明细 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:prescription-item:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPrescriptionItemExcel(@Valid PrescriptionItemPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PrescriptionItemDO> list = prescriptionItemService.getPrescriptionItemPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "处方明细.xls", "数据", PrescriptionItemRespVO.class,
                        BeanUtils.toBean(list, PrescriptionItemRespVO.class));
    }

}