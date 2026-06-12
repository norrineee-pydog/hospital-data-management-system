package cn.iocoder.yudao.module.hospital.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // ========== 就诊记录模块 ==========
    ErrorCode VISIT_NOT_EXISTS = new ErrorCode(500, "就诊记录不存在");
    ErrorCode VISIT_CANCEL_FAIL_STATUS = new ErrorCode(500, "当前状态不可取消挂号");

}