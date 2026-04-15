package cn.iocoder.yudao.module.hospital.controller.admin.bill;

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

import cn.iocoder.yudao.module.hospital.controller.admin.bill.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bill.BillDO;
import cn.iocoder.yudao.module.hospital.service.bill.BillService;

@Tag(name = "管理后台 - 收费账单")
@RestController
@RequestMapping("/hospital/bill")
@Validated
public class BillController {

    @Resource
    private BillService billService;

    @PostMapping("/create")
    @Operation(summary = "创建收费账单")
    @PreAuthorize("@ss.hasPermission('hospital:bill:create')")
    public CommonResult<Integer> createBill(@Valid @RequestBody BillSaveReqVO createReqVO) {
        return success(billService.createBill(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新收费账单")
    @PreAuthorize("@ss.hasPermission('hospital:bill:update')")
    public CommonResult<Boolean> updateBill(@Valid @RequestBody BillSaveReqVO updateReqVO) {
        billService.updateBill(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除收费账单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:bill:delete')")
    public CommonResult<Boolean> deleteBill(@RequestParam("id") Integer id) {
        billService.deleteBill(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除收费账单")
                @PreAuthorize("@ss.hasPermission('hospital:bill:delete')")
    public CommonResult<Boolean> deleteBillList(@RequestParam("ids") List<Integer> ids) {
        billService.deleteBillListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得收费账单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:bill:query')")
    public CommonResult<BillRespVO> getBill(@RequestParam("id") Integer id) {
        BillDO bill = billService.getBill(id);
        return success(BeanUtils.toBean(bill, BillRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得收费账单分页")
    @PreAuthorize("@ss.hasPermission('hospital:bill:query')")
    public CommonResult<PageResult<BillRespVO>> getBillPage(@Valid BillPageReqVO pageReqVO) {
        PageResult<BillDO> pageResult = billService.getBillPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BillRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出收费账单 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:bill:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBillExcel(@Valid BillPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BillDO> list = billService.getBillPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "收费账单.xls", "数据", BillRespVO.class,
                        BeanUtils.toBean(list, BillRespVO.class));
    }

}