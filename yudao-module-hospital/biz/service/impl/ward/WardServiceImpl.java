package cn.iocoder.yudao.module.hospital.service.ward;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.ward.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.ward.WardDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.ward.WardMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 病房 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class WardServiceImpl implements WardService {

    @Resource
    private WardMapper wardMapper;

    @Override
    public Integer createWard(WardSaveReqVO createReqVO) {
        // 插入
        WardDO ward = BeanUtils.toBean(createReqVO, WardDO.class);
        wardMapper.insert(ward);

        // 返回
        return ward.getId();
    }

    @Override
    public void updateWard(WardSaveReqVO updateReqVO) {
        // 校验存在
        validateWardExists(updateReqVO.getId());
        // 更新
        WardDO updateObj = BeanUtils.toBean(updateReqVO, WardDO.class);
        wardMapper.updateById(updateObj);
    }

    @Override
    public void deleteWard(Integer id) {
        // 校验存在
        validateWardExists(id);
        // 删除
        wardMapper.deleteById(id);
    }

    @Override
        public void deleteWardListByIds(List<Integer> ids) {
        // 删除
        wardMapper.deleteByIds(ids);
        }


    private void validateWardExists(Integer id) {
        if (wardMapper.selectById(id) == null) {
            throw exception(WARD_NOT_EXISTS);
        }
    }

    @Override
    public WardDO getWard(Integer id) {
        return wardMapper.selectById(id);
    }

    @Override
    public PageResult<WardDO> getWardPage(WardPageReqVO pageReqVO) {
        return wardMapper.selectPage(pageReqVO);
    }

}