-- ======================================================================
-- 挂号安全与限额兜底 (BEFORE INSERT 触发器)
-- 功能：检测号源是否 <= 0，检测是否重复挂号，并在底层抛出自定义异常
-- ======================================================================

-- 1. 创建兜底拦截的触发器函数
CREATE OR REPLACE FUNCTION check_appointment_limit_and_duplicate()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
DECLARE
    v_available_num INT;
    v_dup_count INT;
BEGIN
    -- 【防止号源超卖】
    -- 结合成员 A 的设计，这里必须加上 FOR UPDATE 行级排他锁，防止高并发下多个请求同时拿到相同的号源
    SELECT available_num INTO v_available_num
    FROM hospital_doctor_schedule
    WHERE id = NEW.schedule_id AND deleted = 0
    FOR UPDATE;

    -- 如果根据 schedule_id 查不到排班记录
    IF v_available_num IS NULL THEN
        RAISE EXCEPTION 'HOSPITAL_ERROR:排班不存在' USING ERRCODE = '45000';
    END IF;

    -- 如果剩余号量已经小于等于 0，触发断言拦截
    IF v_available_num <= 0 THEN
        -- 抛出自定义错误码 45001 和固定前缀文本，供后端 Java 精确捕获
        RAISE EXCEPTION 'HOSPITAL_ERROR:号源已满' USING ERRCODE = '45001';
    END IF;

    -- 【防止同一患者在同一排班时段重复挂号】
    -- 只要患者在该时段有过记录，且状态不是 '已取消'（比如 '待就诊' 或 '已完成'），就判定为重复
    SELECT COUNT(*) INTO v_dup_count
    FROM hospital_visit
    WHERE patient_id = NEW.patient_id
      AND schedule_id = NEW.schedule_id
      AND status != 'CANCELLED'
      AND deleted = 0;

    -- 如果找到大于 0 条的有效记录，触发断言拦截
    IF v_dup_count > 0 THEN
        -- 抛出自定义错误码 45002 和固定前缀文本
        RAISE EXCEPTION 'HOSPITAL_ERROR:重复挂号' USING ERRCODE = '45002';
    END IF;

    -- 校验全数通过，放行插入操作（成员 A 的触发器会随后自动扣减 available_num）
    RETURN NEW;
END;
$$;

-- 2. 将函数绑定到 hospital_visit 表的 BEFORE INSERT 触发器上
DROP TRIGGER IF EXISTS trg_b_check_appointment_limit ON hospital_visit;
CREATE TRIGGER trg_b_check_appointment_limit
BEFORE INSERT ON hospital_visit
FOR EACH ROW
EXECUTE FUNCTION check_appointment_limit_and_duplicate();