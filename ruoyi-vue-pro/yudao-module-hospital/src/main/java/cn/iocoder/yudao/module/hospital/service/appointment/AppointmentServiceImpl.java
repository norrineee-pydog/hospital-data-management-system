package cn.iocoder.yudao.module.hospital.service.appointment;

import cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil;
import cn.iocoder.yudao.module.hospital.dal.dataobject.VisitDO;
import cn.iocoder.yudao.module.hospital.dal.mysql.VisitMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.VISIT_NOT_EXISTS;
import static cn.iocoder.yudao.module.hospital.enums.ErrorCodeConstants.VISIT_STATUS_CANT_CANCEL;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Resource
    private VisitMapper visitMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAppointment(Long visitId) {
        // 1. 查询就诊记录（使用 id 字段）
        VisitDO visit = visitMapper.selectById(visitId);
        if (visit == null) {
            throw ServiceExceptionUtil.exception(VISIT_NOT_EXISTS);
        }

        // 2. 检查状态是否可取消（只有"待就诊"状态可以取消）
        String status = visit.getStatus();
        if (!"待就诊".equals(status)) {
            throw ServiceExceptionUtil.exception(VISIT_STATUS_CANT_CANCEL);
        }

        // 3. 更新状态为"已取消"（使用 id 字段，不是 visitId）
        VisitDO updateVisit = new VisitDO();
        updateVisit.setId(visitId);      // 注意：是 setId，不是 setVisitId
        updateVisit.setStatus("已取消");
        visitMapper.updateById(updateVisit);
    }
}