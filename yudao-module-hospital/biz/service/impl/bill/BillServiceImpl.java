package cn.iocoder.yudao.module.hospital.service.bill;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.hospital.controller.admin.bill.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bill.BillDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.hospital.dal.mysql.bill.BillMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.*;

/**
 * 收费账单 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class BillServiceImpl implements BillService {

    @Resource
    private BillMapper billMapper;

    @Override
    public Integer createBill(BillSaveReqVO createReqVO) {
        // 插入
        BillDO bill = BeanUtils.toBean(createReqVO, BillDO.class);
        billMapper.insert(bill);

        // 返回
        return bill.getId();
    }

    @Override
    public void updateBill(BillSaveReqVO updateReqVO) {
        // 校验存在
        validateBillExists(updateReqVO.getId());
        // 更新
        BillDO updateObj = BeanUtils.toBean(updateReqVO, BillDO.class);
        billMapper.updateById(updateObj);
    }

    @Override
    public void deleteBill(Integer id) {
        // 校验存在
        validateBillExists(id);
        // 删除
        billMapper.deleteById(id);
    }

    @Override
        public void deleteBillListByIds(List<Integer> ids) {
        // 删除
        billMapper.deleteByIds(ids);
        }


    private void validateBillExists(Integer id) {
        if (billMapper.selectById(id) == null) {
            throw exception(BILL_NOT_EXISTS);
        }
    }

    @Override
    public BillDO getBill(Integer id) {
        return billMapper.selectById(id);
    }

    @Override
    public PageResult<BillDO> getBillPage(BillPageReqVO pageReqVO) {
        return billMapper.selectPage(pageReqVO);
    }

}