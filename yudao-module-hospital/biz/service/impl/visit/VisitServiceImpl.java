package cn.iocoder.yudao.module.hospital.service.visit;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.visit.VisitDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.visit.VisitMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 就诊记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class VisitServiceImpl implements VisitService {

    @Resource
    private VisitMapper visitMapper;

    @Override
    public Integer createVisit(VisitSaveReqVO createReqVO) {
        // 插入
        VisitDO visit = BeanUtils.toBean(createReqVO, VisitDO.class);
        visitMapper.insert(visit);

        // 返回
        return visit.getId();
    }

    @Override
    public void updateVisit(VisitSaveReqVO updateReqVO) {
        // 校验存在
        validateVisitExists(updateReqVO.getId());
        // 更新
        VisitDO updateObj = BeanUtils.toBean(updateReqVO, VisitDO.class);
        visitMapper.updateById(updateObj);
    }

    @Override
    public void deleteVisit(Integer id) {
        // 校验存在
        validateVisitExists(id);
        // 删除
        visitMapper.deleteById(id);
    }

    @Override
        public void deleteVisitListByIds(List<Integer> ids) {
        // 删除
        visitMapper.deleteByIds(ids);
        }


    private void validateVisitExists(Integer id) {
        if (visitMapper.selectById(id) == null) {
            throw exception(VISIT_NOT_EXISTS);
        }
    }

    @Override
    public VisitDO getVisit(Integer id) {
        return visitMapper.selectById(id);
    }

    @Override
    public PageResult<VisitDO> getVisitPage(VisitPageReqVO pageReqVO) {
        return visitMapper.selectPage(pageReqVO);
    }

}