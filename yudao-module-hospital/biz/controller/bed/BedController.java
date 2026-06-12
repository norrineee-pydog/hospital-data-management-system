package cn.iocoder.yudao.module.hospital.controller.admin.bed;

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

import cn.iocoder.yudao.module.hospital.controller.admin.bed.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bed.BedDO;
import cn.iocoder.yudao.module.hospital.service.bed.BedService;

@Tag(name = "管理后台 - 床位")
@RestController
@RequestMapping("/hospital/bed")
@Validated
public class BedController {

    @Resource
    private BedService bedService;

    @PostMapping("/create")
    @Operation(summary = "创建床位")
    @PreAuthorize("@ss.hasPermission('hospital:bed:create')")
    public CommonResult<Integer> createBed(@Valid @RequestBody BedSaveReqVO createReqVO) {
        return success(bedService.createBed(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新床位")
    @PreAuthorize("@ss.hasPermission('hospital:bed:update')")
    public CommonResult<Boolean> updateBed(@Valid @RequestBody BedSaveReqVO updateReqVO) {
        bedService.updateBed(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除床位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:bed:delete')")
    public CommonResult<Boolean> deleteBed(@RequestParam("id") Integer id) {
        bedService.deleteBed(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除床位")
                @PreAuthorize("@ss.hasPermission('hospital:bed:delete')")
    public CommonResult<Boolean> deleteBedList(@RequestParam("ids") List<Integer> ids) {
        bedService.deleteBedListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得床位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:bed:query')")
    public CommonResult<BedRespVO> getBed(@RequestParam("id") Integer id) {
        BedDO bed = bedService.getBed(id);
        return success(BeanUtils.toBean(bed, BedRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得床位分页")
    @PreAuthorize("@ss.hasPermission('hospital:bed:query')")
    public CommonResult<PageResult<BedRespVO>> getBedPage(@Valid BedPageReqVO pageReqVO) {
        PageResult<BedDO> pageResult = bedService.getBedPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BedRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出床位 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:bed:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBedExcel(@Valid BedPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BedDO> list = bedService.getBedPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "床位.xls", "数据", BedRespVO.class,
                        BeanUtils.toBean(list, BedRespVO.class));
    }

}