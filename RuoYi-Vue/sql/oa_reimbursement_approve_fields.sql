-- Add approval fields for reimbursement module
-- Safe to run multiple times on MySQL 8+

ALTER TABLE oa_reimbursement
    ADD COLUMN IF NOT EXISTS approve_by VARCHAR(64) NULL COMMENT '������' AFTER payment_time,
    ADD COLUMN IF NOT EXISTS approve_time DATETIME NULL COMMENT '����ʱ��' AFTER approve_by,
    ADD COLUMN IF NOT EXISTS approve_remark VARCHAR(500) NULL COMMENT '�������' AFTER approve_time;
