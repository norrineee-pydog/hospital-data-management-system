package cn.iocoder.yudao.module.hospital.controller.admin.medicine;

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

import cn.iocoder.yudao.module.hospital.controller.admin.medicine.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.medicine.MedicineDO;
import cn.iocoder.yudao.module.hospital.service.medicine.MedicineService;

@Tag(name = "管理后台 - 药品信息")
@RestController
@RequestMapping("/hospital/medicine")
@Validated
public class MedicineController {

    @Resource
    private MedicineService medicineService;

    @PostMapping("/create")
    @Operation(summary = "创建药品信息")
    @PreAuthorize("@ss.hasPermission('hospital:medicine:create')")
    public CommonResult<Integer> createMedicine(@Valid @RequestBody MedicineSaveReqVO createReqVO) {
        return success(medicineService.createMedicine(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新药品信息")
    @PreAuthorize("@ss.hasPermission('hospital:medicine:update')")
    public CommonResult<Boolean> updateMedicine(@Valid @RequestBody MedicineSaveReqVO updateReqVO) {
        medicineService.updateMedicine(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除药品信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:medicine:delete')")
    public CommonResult<Boolean> deleteMedicine(@RequestParam("id") Integer id) {
        medicineService.deleteMedicine(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除药品信息")
                @PreAuthorize("@ss.hasPermission('hospital:medicine:delete')")
    public CommonResult<Boolean> deleteMedicineList(@RequestParam("ids") List<Integer> ids) {
        medicineService.deleteMedicineListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得药品信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:medicine:query')")
    public CommonResult<MedicineRespVO> getMedicine(@RequestParam("id") Integer id) {
        MedicineDO medicine = medicineService.getMedicine(id);
        return success(BeanUtils.toBean(medicine, MedicineRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得药品信息分页")
    @PreAuthorize("@ss.hasPermission('hospital:medicine:query')")
    public CommonResult<PageResult<MedicineRespVO>> getMedicinePage(@Valid MedicinePageReqVO pageReqVO) {
        PageResult<MedicineDO> pageResult = medicineService.getMedicinePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MedicineRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出药品信息 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:medicine:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMedicineExcel(@Valid MedicinePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MedicineDO> list = medicineService.getMedicinePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "药品信息.xls", "数据", MedicineRespVO.class,
                        BeanUtils.toBean(list, MedicineRespVO.class));
    }

}