package cn.iocoder.yudao.module.hospital.service.bill;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.bill.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bill.BillDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 收费账单 Service 接口
 *
 * @author 芋道源码
 */
public interface BillService {

    /**
     * 创建收费账单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBill(@Valid BillSaveReqVO createReqVO);

    /**
     * 更新收费账单
     *
     * @param updateReqVO 更新信息
     */
    void updateBill(@Valid BillSaveReqVO updateReqVO);

    /**
     * 删除收费账单
     *
     * @param id 编号
     */
    void deleteBill(Integer id);

    /**
    * 批量删除收费账单
    *
    * @param ids 编号
    */
    void deleteBillListByIds(List<Integer> ids);

    /**
     * 获得收费账单
     *
     * @param id 编号
     * @return 收费账单
     */
    BillDO getBill(Integer id);

    /**
     * 获得收费账单分页
     *
     * @param pageReqVO 分页查询
     * @return 收费账单分页
     */
    PageResult<BillDO> getBillPage(BillPageReqVO pageReqVO);

}