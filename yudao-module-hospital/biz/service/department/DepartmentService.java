package cn.iocoder.yudao.module.hospital.service.department;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.department.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.department.DepartmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 科室 Service 接口
 *
 * @author 芋道源码
 */
public interface DepartmentService {

    /**
     * 创建科室
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createDepartment(@Valid DepartmentSaveReqVO createReqVO);

    /**
     * 更新科室
     *
     * @param updateReqVO 更新信息
     */
    void updateDepartment(@Valid DepartmentSaveReqVO updateReqVO);

    /**
     * 删除科室
     *
     * @param id 编号
     */
    void deleteDepartment(Integer id);

    /**
    * 批量删除科室
    *
    * @param ids 编号
    */
    void deleteDepartmentListByIds(List<Integer> ids);

    /**
     * 获得科室
     *
     * @param id 编号
     * @return 科室
     */
    DepartmentDO getDepartment(Integer id);

    /**
     * 获得科室分页
     *
     * @param pageReqVO 分页查询
     * @return 科室分页
     */
    PageResult<DepartmentDO> getDepartmentPage(DepartmentPageReqVO pageReqVO);

}