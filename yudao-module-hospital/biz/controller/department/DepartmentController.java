package cn.iocoder.yudao.module.hospital.controller.admin.department;

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

import cn.iocoder.yudao.module.hospital.controller.admin.department.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.department.DepartmentDO;
import cn.iocoder.yudao.module.hospital.service.department.DepartmentService;

@Tag(name = "管理后台 - 科室")
@RestController
@RequestMapping("/hospital/department")
@Validated
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @PostMapping("/create")
    @Operation(summary = "创建科室")
    @PreAuthorize("@ss.hasPermission('hospital:department:create')")
    public CommonResult<Integer> createDepartment(@Valid @RequestBody DepartmentSaveReqVO createReqVO) {
        return success(departmentService.createDepartment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新科室")
    @PreAuthorize("@ss.hasPermission('hospital:department:update')")
    public CommonResult<Boolean> updateDepartment(@Valid @RequestBody DepartmentSaveReqVO updateReqVO) {
        departmentService.updateDepartment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除科室")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('hospital:department:delete')")
    public CommonResult<Boolean> deleteDepartment(@RequestParam("id") Integer id) {
        departmentService.deleteDepartment(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除科室")
                @PreAuthorize("@ss.hasPermission('hospital:department:delete')")
    public CommonResult<Boolean> deleteDepartmentList(@RequestParam("ids") List<Integer> ids) {
        departmentService.deleteDepartmentListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得科室")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('hospital:department:query')")
    public CommonResult<DepartmentRespVO> getDepartment(@RequestParam("id") Integer id) {
        DepartmentDO department = departmentService.getDepartment(id);
        return success(BeanUtils.toBean(department, DepartmentRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得科室分页")
    @PreAuthorize("@ss.hasPermission('hospital:department:query')")
    public CommonResult<PageResult<DepartmentRespVO>> getDepartmentPage(@Valid DepartmentPageReqVO pageReqVO) {
        PageResult<DepartmentDO> pageResult = departmentService.getDepartmentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DepartmentRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出科室 Excel")
    @PreAuthorize("@ss.hasPermission('hospital:department:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDepartmentExcel(@Valid DepartmentPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DepartmentDO> list = departmentService.getDepartmentPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "科室.xls", "数据", DepartmentRespVO.class,
                        BeanUtils.toBean(list, DepartmentRespVO.class));
    }

}