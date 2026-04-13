package cn.iocoder.yudao.module.hospital.controller.admin.visit;

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

import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.visit.VisitDO;
import cn.iocoder.yudao.module.hospital.service.visit.VisitService;

@Tag(name = "管理后台 - 就诊记录")
@RestController
@RequestMapping("/hospital/visit")
@Validated
public class VisitController {

    @Resource
    private VisitService visitService;

    @PostMapping("/create")
    @Operation(summary = "创建就诊记录")
    @PreAuthorize("@ss.hasPermission('hospital:visit:create')")
    public CommonResult<Integer> createVisit(@Valid @RequestBody VisitSaveReqVO createReqVO) {
        return success(visitService.createVisit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新就诊记录")
    @PreAuthorize("@ss.hasPermission('hospital:visit:update')")
    public CommonResult<Boolean> updateVisit(@Valid @RequestBody VisitSaveReqVO updateReqVO) {
        visitService.updateVisit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除就诊记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:visit:delete')")
    public CommonResult<Boolean> deleteVisit(@RequestParam("id") Integer id) {
        visitService.deleteVisit(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除就诊记录")
                @PreAuthorize("@ss.hasPermission('hospital:visit:delete')")
    public CommonResult<Boolean> deleteVisitList(@RequestParam("ids") List<Integer> ids) {
        visitService.deleteVisitListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得就诊记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:visit:query')")
    public CommonResult<VisitRespVO> getVisit(@RequestParam("id") Integer id) {
        VisitDO visit = visitService.getVisit(id);
        return success(BeanUtils.toBean(visit, VisitRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得就诊记录分页")
    @PreAuthorize("@ss.hasPermission('hospital:visit:query')")
    public CommonResult<PageResult<VisitRespVO>> getVisitPage(@Valid VisitPageReqVO pageReqVO) {
        PageResult<VisitDO> pageResult = visitService.getVisitPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VisitRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出就诊记录 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:visit:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVisitExcel(@Valid VisitPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VisitDO> list = visitService.getVisitPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "就诊记录.xls", "数据", VisitRespVO.class,
                        BeanUtils.toBean(list, VisitRespVO.class));
    }

}