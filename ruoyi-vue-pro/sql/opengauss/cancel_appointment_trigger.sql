-- =====================================================
-- 取消挂号释放号源触发器
-- 功能：取消挂号时，自动将医生排班的剩余号量 +1
-- =====================================================

-- 创建释放号源函数
CREATE OR REPLACE FUNCTION cancel_appointment_release_slot()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
    -- 只有当状态从非'已取消'变为'已取消'时，才释放号源
    IF OLD.status != '已取消' AND NEW.status = '已取消' THEN
UPDATE doctor_schedule
SET available_num = available_num + 1,
    update_time = CURRENT_TIMESTAMP
WHERE schedule_id = NEW.schedule_id;
END IF;
RETURN NEW;
END;
$$;

-- 绑定 AFTER UPDATE 触发器（每次更新后执行）
DROP TRIGGER IF EXISTS trg_cancel_appointment ON visit;
CREATE TRIGGER trg_cancel_appointment
    AFTER UPDATE ON visit
    FOR EACH ROW
    WHEN (OLD.status != '已取消' AND NEW.status = '已取消')
EXECUTE PROCEDURE cancel_appointment_release_slot();
