package cn.iocoder.yudao.module.hospital.service.bed;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.bed.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.bed.BedDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 床位 Service 接口
 *
 * @author 芋道源码
 */
public interface BedService {

    /**
     * 创建床位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createBed(@Valid BedSaveReqVO createReqVO);

    /**
     * 更新床位
     *
     * @param updateReqVO 更新信息
     */
    void updateBed(@Valid BedSaveReqVO updateReqVO);

    /**
     * 删除床位
     *
     * @param id 编号
     */
    void deleteBed(Integer id);

    /**
    * 批量删除床位
    *
    * @param ids 编号
    */
    void deleteBedListByIds(List<Integer> ids);

    /**
     * 获得床位
     *
     * @param id 编号
     * @return 床位
     */
    BedDO getBed(Integer id);

    /**
     * 获得床位分页
     *
     * @param pageReqVO 分页查询
     * @return 床位分页
     */
    PageResult<BedDO> getBedPage(BedPageReqVO pageReqVO);

}