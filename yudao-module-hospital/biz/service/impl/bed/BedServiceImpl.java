package cn.iocoder.yudao.module.hospital.service.bed;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.bed.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bed.BedDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.bed.BedMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 床位 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class BedServiceImpl implements BedService {

    @Resource
    private BedMapper bedMapper;

    @Override
    public Integer createBed(BedSaveReqVO createReqVO) {
        // 插入
        BedDO bed = BeanUtils.toBean(createReqVO, BedDO.class);
        bedMapper.insert(bed);

        // 返回
        return bed.getId();
    }

    @Override
    public void updateBed(BedSaveReqVO updateReqVO) {
        // 校验存在
        validateBedExists(updateReqVO.getId());
        // 更新
        BedDO updateObj = BeanUtils.toBean(updateReqVO, BedDO.class);
        bedMapper.updateById(updateObj);
    }

    @Override
    public void deleteBed(Integer id) {
        // 校验存在
        validateBedExists(id);
        // 删除
        bedMapper.deleteById(id);
    }

    @Override
        public void deleteBedListByIds(List<Integer> ids) {
        // 删除
        bedMapper.deleteByIds(ids);
        }


    private void validateBedExists(Integer id) {
        if (bedMapper.selectById(id) == null) {
            throw exception(BED_NOT_EXISTS);
        }
    }

    @Override
    public BedDO getBed(Integer id) {
        return bedMapper.selectById(id);
    }

    @Override
    public PageResult<BedDO> getBedPage(BedPageReqVO pageReqVO) {
        return bedMapper.selectPage(pageReqVO);
    }

}