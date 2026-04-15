package cn.iocoder.yudao.module.hospital.service.ward;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.ward.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.ward.WardDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 病房 Service 接口
 *
 * @author 芋道源码
 */
public interface WardService {

    /**
     * 创建病房
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createWard(@Valid WardSaveReqVO createReqVO);

    /**
     * 更新病房
     *
     * @param updateReqVO 更新信息
     */
    void updateWard(@Valid WardSaveReqVO updateReqVO);

    /**
     * 删除病房
     *
     * @param id 编号
     */
    void deleteWard(Integer id);

    /**
    * 批量删除病房
    *
    * @param ids 编号
    */
    void deleteWardListByIds(List<Integer> ids);

    /**
     * 获得病房
     *
     * @param id 编号
     * @return 病房
     */
    WardDO getWard(Integer id);

    /**
     * 获得病房分页
     *
     * @param pageReqVO 分页查询
     * @return 病房分页
     */
    PageResult<WardDO> getWardPage(WardPageReqVO pageReqVO);

}