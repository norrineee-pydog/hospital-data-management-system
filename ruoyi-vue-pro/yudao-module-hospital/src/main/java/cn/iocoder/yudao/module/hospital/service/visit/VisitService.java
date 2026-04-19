package cn.iocoder.yudao.module.hospital.service.visit;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitPageReqVO;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitRespVO;
import cn.iocoder.yudao.module.hospital.controller.admin.visit.vo.VisitSaveReqVO;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;

public interface VisitService {

    Long createVisit(VisitSaveReqVO createReqVO);

    void updateVisit(VisitSaveReqVO updateReqVO);

    void deleteVisit(Long id);

    VisitDO getVisit(Long id);


    PageResult<VisitDO> getVisitPage(VisitPageReqVO pageReqVO);

    // 新增：获取带名称的分页列表
    PageResult<VisitRespVO> getVisitPageWithNames(VisitPageReqVO pageReqVO);
}