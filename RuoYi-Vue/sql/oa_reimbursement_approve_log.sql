-- reimbursement approve history log table
CREATE TABLE IF NOT EXISTS oa_reimbursement_approve_log (
    log_id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '��־ID',
    reimb_id        BIGINT(20)   NOT NULL COMMENT '����ID',
    approve_result  VARCHAR(20)  NOT NULL COMMENT '���������1ͨ�� 2���أ�',
    approve_remark  VARCHAR(500)          COMMENT '�������',
    approve_by      VARCHAR(64)           COMMENT '������',
    approve_time    DATETIME              COMMENT '����ʱ��',
    PRIMARY KEY (log_id),
    KEY idx_reimb_id (reimb_id)
) ENGINE=InnoDB COMMENT='����������ʷ��';
