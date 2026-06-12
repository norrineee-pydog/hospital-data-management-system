package cn.iocoder.yudao.module.hospital.service.department;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.department.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.department.DepartmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.department.DepartmentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 科室 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Integer createDepartment(DepartmentSaveReqVO createReqVO) {
        // 插入
        DepartmentDO department = BeanUtils.toBean(createReqVO, DepartmentDO.class);
        departmentMapper.insert(department);

        // 返回
        return department.getId();
    }

    @Override
    public void updateDepartment(DepartmentSaveReqVO updateReqVO) {
        // 校验存在
        validateDepartmentExists(updateReqVO.getId());
        // 更新
        DepartmentDO updateObj = BeanUtils.toBean(updateReqVO, DepartmentDO.class);
        departmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteDepartment(Integer id) {
        // 校验存在
        validateDepartmentExists(id);
        // 删除
        departmentMapper.deleteById(id);
    }

    @Override
        public void deleteDepartmentListByIds(List<Integer> ids) {
        // 删除
        departmentMapper.deleteByIds(ids);
        }


    private void validateDepartmentExists(Integer id) {
        if (departmentMapper.selectById(id) == null) {
            throw exception(DEPARTMENT_NOT_EXISTS);
        }
    }

    @Override
    public DepartmentDO getDepartment(Integer id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public PageResult<DepartmentDO> getDepartmentPage(DepartmentPageReqVO pageReqVO) {
        return departmentMapper.selectPage(pageReqVO);
    }

}