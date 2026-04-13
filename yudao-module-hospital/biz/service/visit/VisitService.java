package cn.iocoder.yudao.module.hospital.service.visit;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.*;
import cn.iocoder.yudao.module.hospital.dal.dataobject.visit.VisitDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 就诊记录 Service 接口
 *
 * @author 芋道源码
 */
public interface VisitService {

    /**
     * 创建就诊记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createVisit(@Valid VisitSaveReqVO createReqVO);

    /**
     * 更新就诊记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVisit(@Valid VisitSaveReqVO updateReqVO);

    /**
     * 删除就诊记录
     *
     * @param id 编号
     */
    void deleteVisit(Integer id);

    /**
    * 批量删除就诊记录
    *
    * @param ids 编号
    */
    void deleteVisitListByIds(List<Integer> ids);

    /**
     * 获得就诊记录
     *
     * @param id 编号
     * @return 就诊记录
     */
    VisitDO getVisit(Integer id);

    /**
     * 获得就诊记录分页
     *
     * @param pageReqVO 分页查询
     * @return 就诊记录分页
     */
    PageResult<VisitDO> getVisitPage(VisitPageReqVO pageReqVO);

}