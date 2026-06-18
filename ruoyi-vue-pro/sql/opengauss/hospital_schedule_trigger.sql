-- =============================================
-- 医院病人数据管理系统 - 医生排班表 + 号源扣减触发器
-- 功能: 预约挂号时自动扣减对应医生排班的可用号量
-- =============================================

-- 1. 医生排班表
DROP TABLE IF EXISTS hospital_doctor_schedule;
CREATE TABLE hospital_doctor_schedule
(
    id             int8         NOT NULL,
    doctor_id      int8         NOT NULL,
    schedule_date  date         NOT NULL,
    period         varchar(50)  NOT NULL,
    total_num      int4         NOT NULL DEFAULT 10,
    available_num  int4         NOT NULL DEFAULT 10,
    creator        varchar(64)  NULL     DEFAULT '',
    create_time    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updater        varchar(64)  NULL     DEFAULT '',
    update_time    timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted        int2         NOT NULL DEFAULT 0,
    tenant_id      int8         NOT NULL DEFAULT 0
);

ALTER TABLE hospital_doctor_schedule
    ADD CONSTRAINT pk_hospital_doctor_schedule PRIMARY KEY (id);

COMMENT ON COLUMN hospital_doctor_schedule.id IS '排班ID';
COMMENT ON COLUMN hospital_doctor_schedule.doctor_id IS '医生ID';
COMMENT ON COLUMN hospital_doctor_schedule.schedule_date IS '排班日期';
COMMENT ON COLUMN hospital_doctor_schedule.period IS '时段，如 08:00-09:00';
COMMENT ON COLUMN hospital_doctor_schedule.total_num IS '总号量';
COMMENT ON COLUMN hospital_doctor_schedule.available_num IS '剩余号量';
COMMENT ON TABLE hospital_doctor_schedule IS '医生排班表';

DROP SEQUENCE IF EXISTS hospital_doctor_schedule_seq;
CREATE SEQUENCE hospital_doctor_schedule_seq START 1;

-- 2. 给就诊记录表增加 schedule_id 列
ALTER TABLE hospital_visit
    ADD COLUMN IF NOT EXISTS schedule_id int8 NULL;

COMMENT ON COLUMN hospital_visit.schedule_id IS '关联的医生排班ID';

-- 3. 创建 BEFORE INSERT 触发器函数: 自动扣减号量
CREATE OR REPLACE FUNCTION trg_func_visit_before_insert()
RETURNS TRIGGER AS
$$
DECLARE
    v_available int4;
BEGIN
    -- 如果 schedule_id 不为空，则扣减对应排班的 available_num
    IF NEW.schedule_id IS NOT NULL THEN
        -- 检查排班是否存在
        SELECT available_num INTO v_available
        FROM hospital_doctor_schedule
        WHERE id = NEW.schedule_id AND deleted = 0
        FOR UPDATE;

        IF NOT FOUND THEN
            RAISE EXCEPTION '排班记录不存在 (schedule_id: %)', NEW.schedule_id;
        END IF;

        IF v_available <= 0 THEN
            RAISE EXCEPTION '该时段号源已满，无法挂号';
        END IF;

        -- 扣减可用号量
        UPDATE hospital_doctor_schedule
        SET available_num = available_num - 1,
            update_time = CURRENT_TIMESTAMP
        WHERE id = NEW.schedule_id AND deleted = 0;
    END IF;

    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

-- 4. 创建触发器
DROP TRIGGER IF EXISTS trg_visit_before_insert ON hospital_visit;
CREATE TRIGGER trg_visit_before_insert
    BEFORE INSERT ON hospital_visit
    FOR EACH ROW
    EXECUTE FUNCTION trg_func_visit_before_insert();
