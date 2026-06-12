package cn.iocoder.yudao.module.hospital.controller.admin.ward;

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

import cn.iocoder.yudao.module.hospital.controller.admin.ward.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.ward.WardDO;
import cn.iocoder.yudao.module.hospital.service.ward.WardService;

@Tag(name = "管理后台 - 病房")
@RestController
@RequestMapping("/hospital/ward")
@Validated
public class WardController {

    @Resource
    private WardService wardService;

    @PostMapping("/create")
    @Operation(summary = "创建病房")
    @PreAuthorize("@ss.hasPermission('hospital:ward:create')")
    public CommonResult<Integer> createWard(@Valid @RequestBody WardSaveReqVO createReqVO) {
        return success(wardService.createWard(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新病房")
    @PreAuthorize("@ss.hasPermission('hospital:ward:update')")
    public CommonResult<Boolean> updateWard(@Valid @RequestBody WardSaveReqVO updateReqVO) {
        wardService.updateWard(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除病房")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:ward:delete')")
    public CommonResult<Boolean> deleteWard(@RequestParam("id") Integer id) {
        wardService.deleteWard(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除病房")
                @PreAuthorize("@ss.hasPermission('hospital:ward:delete')")
    public CommonResult<Boolean> deleteWardList(@RequestParam("ids") List<Integer> ids) {
        wardService.deleteWardListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得病房")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:ward:query')")
    public CommonResult<WardRespVO> getWard(@RequestParam("id") Integer id) {
        WardDO ward = wardService.getWard(id);
        return success(BeanUtils.toBean(ward, WardRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得病房分页")
    @PreAuthorize("@ss.hasPermission('hospital:ward:query')")
    public CommonResult<PageResult<WardRespVO>> getWardPage(@Valid WardPageReqVO pageReqVO) {
        PageResult<WardDO> pageResult = wardService.getWardPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, WardRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出病房 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:ward:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportWardExcel(@Valid WardPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<WardDO> list = wardService.getWardPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "病房.xls", "数据", WardRespVO.class,
                        BeanUtils.toBean(list, WardRespVO.class));
    }

}