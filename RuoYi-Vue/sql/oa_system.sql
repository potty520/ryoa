-- =============================================
-- OA办公系统数据库设计
-- 基于RuoYi-Vue框架
-- =============================================

-- 1. 日程安排表 oa_schedule
DROP TABLE IF EXISTS oa_schedule;
CREATE TABLE oa_schedule (
    schedule_id     BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '日程ID',
    schedule_title   VARCHAR(100)    NOT NULL                 COMMENT '日程标题',
    schedule_type    VARCHAR(20)     DEFAULT '1'               COMMENT '日程类型（1日程 2会议 3约会）',
    start_time       DATETIME       NOT NULL                  COMMENT '开始时间',
    end_time         DATETIME       NOT NULL                  COMMENT '结束时间',
    schedule_content TEXT                                     COMMENT '日程内容',
    location         VARCHAR(200)                            COMMENT '地点',
    remind_flag      CHAR(1)        DEFAULT '1'                COMMENT '是否提醒（0否 1是）',
    remind_time      INT            DEFAULT 15                COMMENT '提前提醒分钟数',
    status           CHAR(1)        DEFAULT '0'                COMMENT '状态（0正常 1已取消）',
    user_id          BIGINT(20)     NOT NULL                  COMMENT '用户ID',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (schedule_id)
) ENGINE=InnoDB COMMENT='日程安排表';

-- 2. 请假申请表 oa_leave
DROP TABLE IF EXISTS oa_leave;
CREATE TABLE oa_leave (
    leave_id         BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '请假ID',
    leave_type       VARCHAR(20)     NOT NULL                  COMMENT '请假类型（1年假 2事假 3病假 4婚假 5产假 6丧假）',
    start_time       DATETIME       NOT NULL                  COMMENT '开始时间',
    end_time         DATETIME       NOT NULL                  COMMENT '结束时间',
    leave_days       DECIMAL(5,1)   NOT NULL                  COMMENT '请假天数',
    leave_reason     VARCHAR(500)   NOT NULL                  COMMENT '请假原因',
    apply_time       DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '申请时间',
    flow_id          BIGINT(20)                                COMMENT '流程ID',
    current_step     INT            DEFAULT 1                  COMMENT '当前步骤',
    approval_status   VARCHAR(20)    DEFAULT '0'                COMMENT '审批状态（0审批中 1已通过 2已拒绝 3已撤回）',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (leave_id)
) ENGINE=InnoDB COMMENT='请假申请表';

-- 3. 报销申请表 oa_reimbursement
DROP TABLE IF EXISTS oa_reimbursement;
CREATE TABLE oa_reimbursement (
    reimb_id        BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '报销ID',
    reimb_type       VARCHAR(20)     NOT NULL                  COMMENT '报销类型（1差旅费 2交通费 3餐饮费 4办公费 5其他）',
    total_amount     DECIMAL(10,2)  NOT NULL                  COMMENT '报销总金额',
    reimb_reason     VARCHAR(500)   NOT NULL                  COMMENT '报销事由',
    apply_time       DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '申请时间',
    flow_id          BIGINT(20)                                COMMENT '流程ID',
    current_step     INT            DEFAULT 1                  COMMENT '当前步骤',
    approval_status   VARCHAR(20)    DEFAULT '0'                COMMENT '审批状态（0审批中 1已通过 2已拒绝 3已撤回）',
    payment_status   VARCHAR(20)    DEFAULT '0'                COMMENT '支付状态（0未支付 1已支付）',
    payment_time     DATETIME                                   COMMENT '支付时间',
    approve_by       VARCHAR(64)                               COMMENT '审批人',
    approve_time     DATETIME                                   COMMENT '审批时间',
    approve_remark   VARCHAR(500)                              COMMENT '审批意见',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (reimb_id)
) ENGINE=InnoDB COMMENT='报销申请表';

-- 4. 报销明细表 oa_reimbursement_detail
DROP TABLE IF EXISTS oa_reimbursement_detail;
CREATE TABLE oa_reimbursement_detail (
    detail_id        BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '明细ID',
    reimb_id         BIGINT(20)      NOT NULL                  COMMENT '报销ID',
    expense_type     VARCHAR(20)     NOT NULL                  COMMENT '费用类型',
    expense_date     DATE            NOT NULL                  COMMENT '费用日期',
    expense_amount   DECIMAL(10,2)  NOT NULL                  COMMENT '费用金额',
    expense_desc     VARCHAR(500)                            COMMENT '费用描述',
    receipt_url      VARCHAR(200)                            COMMENT '收据图片URL',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (detail_id)
) ENGINE=InnoDB COMMENT='报销明细表';

-- 报销审批历史表 oa_reimbursement_approve_log
DROP TABLE IF EXISTS oa_reimbursement_approve_log;
CREATE TABLE oa_reimbursement_approve_log (
    log_id          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    reimb_id        BIGINT(20)   NOT NULL COMMENT '报销ID',
    approve_result  VARCHAR(20)  NOT NULL COMMENT '审批结果（1通过 2驳回）',
    approve_remark  VARCHAR(500)          COMMENT '审批意见',
    approve_by      VARCHAR(64)           COMMENT '审批人',
    approve_time    DATETIME              COMMENT '审批时间',
    PRIMARY KEY (log_id),
    KEY idx_reimb_id (reimb_id)
) ENGINE=InnoDB COMMENT='报销审批历史表';

-- 5. 考勤记录表 oa_attendance
DROP TABLE IF EXISTS oa_attendance;
CREATE TABLE oa_attendance (
    attendance_id    BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '考勤ID',
    user_id          BIGINT(20)      NOT NULL                  COMMENT '用户ID',
    attendance_date  DATE            NOT NULL                  COMMENT '考勤日期',
    check_in_time    DATETIME                                COMMENT '签到时间',
    check_out_time   DATETIME                                COMMENT '签退时间',
    check_in_status  VARCHAR(20)     DEFAULT '0'                COMMENT '签到状态（0正常 1迟到 2缺勤）',
    check_out_status VARCHAR(20)     DEFAULT '0'                COMMENT '签退状态（0正常 1早退 2缺勤）',
    work_hours       DECIMAL(5,2)                           COMMENT '工作时长',
    location         VARCHAR(200)                            COMMENT '打卡地点',
    device_info      VARCHAR(200)                            COMMENT '打卡设备',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_time      DATETIME                                   COMMENT '更新时间',
    PRIMARY KEY (attendance_id),
    UNIQUE KEY uk_user_date (user_id, attendance_date)
) ENGINE=InnoDB COMMENT='考勤记录表';

-- 6. 加班记录表 oa_overtime
DROP TABLE IF EXISTS oa_overtime;
CREATE TABLE oa_overtime (
    overtime_id      BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '加班ID',
    user_id          BIGINT(20)      NOT NULL                  COMMENT '用户ID',
    start_time       DATETIME       NOT NULL                  COMMENT '加班开始时间',
    end_time         DATETIME       NOT NULL                  COMMENT '加班结束时间',
    overtime_hours   DECIMAL(5,2)   NOT NULL                  COMMENT '加班时长',
    overtime_type    VARCHAR(20)     DEFAULT '1'                COMMENT '加班类型（1工作日 2休息日 3节假日）',
    overtime_reason  VARCHAR(500)                            COMMENT '加班原因',
    apply_time       DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '申请时间',
    approval_status  VARCHAR(20)    DEFAULT '0'                COMMENT '审批状态（0审批中 1已通过 2已拒绝）',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (overtime_id)
) ENGINE=InnoDB COMMENT='加班记录表';

-- 7. 通讯录表 oa_contact
DROP TABLE IF EXISTS oa_contact;
CREATE TABLE oa_contact (
    contact_id       BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '联系人ID',
    user_id          BIGINT(20)      NOT NULL                  COMMENT '用户ID（创建者）',
    contact_user_id  BIGINT(20)      NOT NULL                  COMMENT '联系人用户ID',
    contact_name     VARCHAR(50)     NOT NULL                  COMMENT '联系人姓名',
    contact_phone    VARCHAR(20)                             COMMENT '联系电话',
    contact_email    VARCHAR(100)                            COMMENT '电子邮箱',
    contact_dept     VARCHAR(100)                            COMMENT '所属部门',
    contact_post     VARCHAR(50)                             COMMENT '岗位',
    is_star          CHAR(1)        DEFAULT '0'                COMMENT '是否星标（0否 1是）',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (contact_id)
) ENGINE=InnoDB COMMENT='通讯录表';

-- 8. 文档管理表 oa_document
DROP TABLE IF EXISTS oa_document;
CREATE TABLE oa_document (
    doc_id           BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '文档ID',
    doc_name         VARCHAR(200)    NOT NULL                  COMMENT '文档名称',
    doc_type         VARCHAR(20)     NOT NULL                  COMMENT '文档类型（1文件夹 2文件）',
    file_type        VARCHAR(20)                             COMMENT '文件类型（doc/xls/pdf/txt等）',
    file_size        BIGINT(20)                              COMMENT '文件大小（字节）',
    file_path        VARCHAR(500)                            COMMENT '文件路径',
    parent_id        BIGINT(20)      DEFAULT 0                 COMMENT '父文件夹ID',
    doc_level        INT            DEFAULT 0                 COMMENT '文档层级',
    is_public        CHAR(1)        DEFAULT '0'                COMMENT '是否公开（0私有 1公共）',
    share_range      VARCHAR(100)   DEFAULT ''                 COMMENT '共享范围（部门ID列表）',
    user_id          BIGINT(20)     NOT NULL                  COMMENT '上传用户ID',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (doc_id)
) ENGINE=InnoDB COMMENT='文档管理表';

-- 9. 审批流程定义表 oa_approval_flow
DROP TABLE IF EXISTS oa_approval_flow;
CREATE TABLE oa_approval_flow (
    flow_id          BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '流程ID',
    flow_name        VARCHAR(100)    NOT NULL                  COMMENT '流程名称',
    flow_type        VARCHAR(20)     NOT NULL                  COMMENT '流程类型（1请假 2报销 3加班）',
    flow_level       INT            DEFAULT 1                  COMMENT '审批级别（1一级审批 2多级审批）',
    approval_type    VARCHAR(20)    DEFAULT '1'                COMMENT '审批方式（1逐级审批 2会签审批）',
    is_active        CHAR(1)        DEFAULT '1'                COMMENT '是否启用（0否 1是）',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (flow_id)
) ENGINE=InnoDB COMMENT='审批流程定义表';

-- 10. 审批流程步骤表 oa_approval_step
DROP TABLE IF EXISTS oa_approval_step;
CREATE TABLE oa_approval_step (
    step_id          BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '步骤ID',
    flow_id          BIGINT(20)      NOT NULL                  COMMENT '流程ID',
    step_order       INT            NOT NULL                  COMMENT '步骤顺序',
    step_name        VARCHAR(50)    NOT NULL                  COMMENT '步骤名称',
    approver_type    VARCHAR(20)    DEFAULT '1'                COMMENT '审批人类型（1指定人 2部门主管 3角色）',
    approver_id      BIGINT(20)                              COMMENT '审批人ID',
    approver_name    VARCHAR(100)                            COMMENT '审批人名称',
    is_final         CHAR(1)        DEFAULT '0'                COMMENT '是否终审（0否 1是）',
    create_time      DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (step_id)
) ENGINE=InnoDB COMMENT='审批流程步骤表';

-- 11. 审批记录表 oa_approval_record
DROP TABLE IF EXISTS oa_approval_record;
CREATE TABLE oa_approval_record (
    record_id        BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '记录ID',
    business_type    VARCHAR(20)     NOT NULL                  COMMENT '业务类型（leave/reimb/overtime）',
    business_id      BIGINT(20)      NOT NULL                  COMMENT '业务ID',
    flow_id          BIGINT(20)      NOT NULL                  COMMENT '流程ID',
    step_id          BIGINT(20)      NOT NULL                  COMMENT '步骤ID',
    step_name        VARCHAR(50)                             COMMENT '步骤名称',
    approver_id      BIGINT(20)      NOT NULL                  COMMENT '审批人ID',
    approver_name    VARCHAR(100)                            COMMENT '审批人姓名',
    approval_result  VARCHAR(20)    NOT NULL                  COMMENT '审批结果（1通过 2拒绝 3退回）',
    approval_comment VARCHAR(500)                            COMMENT '审批意见',
    approval_time    DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '审批时间',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (record_id)
) ENGINE=InnoDB COMMENT='审批记录表';

-- 12. 系统公告表（扩展RuoYi） oa_announcement
DROP TABLE IF EXISTS oa_announcement;
CREATE TABLE oa_announcement (
    ann_id           BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '公告ID',
    ann_title        VARCHAR(200)    NOT NULL                  COMMENT '公告标题',
    ann_type         VARCHAR(20)     NOT NULL                  COMMENT '公告类型（1公司公告 2部门公告 3系统公告）',
    ann_content      TEXT                                      COMMENT '公告内容',
    ann_level        VARCHAR(20)     DEFAULT '1'                COMMENT '重要程度（1普通 2重要 3紧急）',
    attachment_url   VARCHAR(500)                            COMMENT '附件URL',
    publish_start    DATETIME                                  COMMENT '发布时间',
    publish_end      DATETIME                                  COMMENT '结束时间',
    is_top           CHAR(1)        DEFAULT '0'                COMMENT '是否置顶（0否 1是）',
    is_public        CHAR(1)        DEFAULT '1'                COMMENT '是否公开（0否 1是）',
    view_count       INT            DEFAULT 0                 COMMENT '阅读次数',
    status           CHAR(1)        DEFAULT '0'                COMMENT '状态（0草稿 1已发布 2已下架）',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (ann_id)
) ENGINE=InnoDB COMMENT='系统公告表';

-- 13. 公告阅读记录表 oa_announcement_read
DROP TABLE IF EXISTS oa_announcement_read;
CREATE TABLE oa_announcement_read (
    read_id          BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '阅读ID',
    ann_id           BIGINT(20)      NOT NULL                  COMMENT '公告ID',
    user_id          BIGINT(20)      NOT NULL                  COMMENT '用户ID',
    read_time        DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '阅读时间',
    PRIMARY KEY (read_id),
    UNIQUE KEY uk_ann_user (ann_id, user_id)
) ENGINE=InnoDB COMMENT='公告阅读记录表';

-- =============================================
-- 初始化数据
-- =============================================

-- 插入请假类型字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, status) VALUES ('请假类型', 'oa_leave_type', '0');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default) VALUES
(1, '年假', '1', 'oa_leave_type', '', 'primary', 'Y'),
(2, '事假', '2', 'oa_leave_type', '', 'danger', 'N'),
(3, '病假', '3', 'oa_leave_type', '', 'warning', 'N'),
(4, '婚假', '4', 'oa_leave_type', '', 'success', 'N'),
(5, '产假', '5', 'oa_leave_type', '', 'info', 'N'),
(6, '丧假', '6', 'oa_leave_type', '', 'N', 'N');

-- 插入报销类型字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, status) VALUES ('报销类型', 'oa_reimb_type', '0');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default) VALUES
(1, '差旅费', '1', 'oa_reimb_type', '', 'primary', 'Y'),
(2, '交通费', '2', 'oa_reimb_type', '', 'success', 'N'),
(3, '餐饮费', '3', 'oa_reimb_type', '', 'warning', 'N'),
(4, '办公费', '4', 'oa_reimb_type', '', 'info', 'N'),
(5, '其他', '5', 'oa_reimb_type', '', 'N', 'N');

-- 插入日程类型字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, status) VALUES ('日程类型', 'oa_schedule_type', '0');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default) VALUES
(1, '日程', '1', 'oa_schedule_type', '', 'primary', 'Y'),
(2, '会议', '2', 'oa_schedule_type', '', 'success', 'N'),
(3, '约会', '3', 'oa_schedule_type', '', 'warning', 'N');

-- 插入加班类型字典数据
INSERT INTO sys_dict_type (dict_name, dict_type, status) VALUES ('加班类型', 'oa_overtime_type', '0');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default) VALUES
(1, '工作日加班', '1', 'oa_overtime_type', '', 'primary', 'Y'),
(2, '休息日加班', '2', 'oa_overtime_type', '', 'success', 'N'),
(3, '节假日加班', '3', 'oa_overtime_type', '', 'danger', 'N');

-- =============================================
-- 公文协同处理系统表结构
-- =============================================

-- 14. 公文类型表 oa_doc_type
DROP TABLE IF EXISTS oa_doc_type;
CREATE TABLE oa_doc_type (
    type_id           BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '类型ID',
    type_code        VARCHAR(50)     NOT NULL                  COMMENT '类型代码',
    type_name        VARCHAR(100)    NOT NULL                  COMMENT '类型名称',
    doc_category     VARCHAR(20)     NOT NULL                  COMMENT '公文类别（1上行文 2平行文 3下行文）',
    flow_id          BIGINT(20)                              COMMENT '默认流程ID',
    is_active        CHAR(1)        DEFAULT '1'                COMMENT '是否启用（0否 1是）',
    sort_num         INT            DEFAULT 0                  COMMENT '排序号',
    create_by        VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time      DATETIME                                   COMMENT '创建时间',
    update_by        VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time      DATETIME                                   COMMENT '更新时间',
    remark           VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (type_id),
    UNIQUE KEY uk_type_code (type_code)
) ENGINE=InnoDB COMMENT='公文类型表';

-- 15. 收文登记表 oa_incoming_doc
DROP TABLE IF EXISTS oa_incoming_doc;
CREATE TABLE oa_incoming_doc (
    doc_id           BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '收文ID',
    doc_num         VARCHAR(50)     NOT NULL                  COMMENT '收文编号',
    doc_title       VARCHAR(200)    NOT NULL                  COMMENT '公文标题',
    doc_type_id     BIGINT(20)      NOT NULL                  COMMENT '公文类型ID',
    doc_type_name   VARCHAR(100)                            COMMENT '公文类型名称',
    source_unit     VARCHAR(100)    NOT NULL                  COMMENT '来文单位',
    doc_code        VARCHAR(100)                            COMMENT '来文字号',
    doc_date        DATE            NOT NULL                  COMMENT '来文日期',
    doc_level       VARCHAR(20)     DEFAULT '1'                COMMENT '紧急程度（1普通 2重要 3紧急）',
    page_count      INT                                      COMMENT '页数',
    attachment_url  VARCHAR(500)                            COMMENT '附件URL',
    secret_level    VARCHAR(20)     DEFAULT '1'                COMMENT '密级（1普通 2秘密 3机密 4绝密）',
    receive_date    DATETIME       NOT NULL                  COMMENT '收文日期',
    flow_id         BIGINT(20)                              COMMENT '流程ID',
    current_step    INT            DEFAULT 1                  COMMENT '当前步骤',
    current_node    VARCHAR(100)                            COMMENT '当前环节',
    doc_status      VARCHAR(20)     DEFAULT '0'                COMMENT '文档状态（0待签收 1待登记 2待审批 3审批中 4已办结 5已归档）',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    update_by       VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time     DATETIME                                   COMMENT '更新时间',
    remark          VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (doc_id),
    UNIQUE KEY uk_doc_num (doc_num)
) ENGINE=InnoDB COMMENT='收文登记表';

-- 16. 发文稿表 oa_outgoing_doc
DROP TABLE IF EXISTS oa_outgoing_doc;
CREATE TABLE oa_outgoing_doc (
    doc_id           BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '发文ID',
    doc_num         VARCHAR(50)     NOT NULL                  COMMENT '发文编号',
    doc_title       VARCHAR(200)    NOT NULL                  COMMENT '公文标题',
    doc_type_id     BIGINT(20)      NOT NULL                  COMMENT '公文类型ID',
    doc_type_name   VARCHAR(100)                            COMMENT '公文类型名称',
    main_unit       VARCHAR(100)                            COMMENT '主送单位',
    copy_unit       VARCHAR(500)                            COMMENT '抄送单位',
    doc_code        VARCHAR(100)                            COMMENT '发文字号',
    doc_date        DATE                                      COMMENT '发文日期',
    doc_level       VARCHAR(20)     DEFAULT '1'                COMMENT '紧急程度（1普通 2重要 3紧急）',
    secret_level    VARCHAR(20)     DEFAULT '1'                COMMENT '密级（1普通 2秘密 3机密 4绝密）',
    attachment_url  VARCHAR(500)                            COMMENT '附件URL',
    doc_content     TEXT                                      COMMENT '公文内容',
    flow_id         BIGINT(20)                              COMMENT '流程ID',
    current_step    INT            DEFAULT 1                  COMMENT '当前步骤',
    current_node    VARCHAR(100)                            COMMENT '当前环节',
    doc_status      VARCHAR(20)     DEFAULT '0'                COMMENT '文档状态（0草稿 1待审核 2审核中 3已发布 4已归档）',
    publish_date    DATETIME                                  COMMENT '发布日期',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    update_by       VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time     DATETIME                                   COMMENT '更新时间',
    remark          VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (doc_id)
) ENGINE=InnoDB COMMENT='发文稿表';

-- 17. 公文流程配置表 oa_doc_flow
DROP TABLE IF EXISTS oa_doc_flow;
CREATE TABLE oa_doc_flow (
    flow_id          BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '流程ID',
    flow_name       VARCHAR(100)    NOT NULL                  COMMENT '流程名称',
    flow_category   VARCHAR(20)     NOT NULL                  COMMENT '流程类别（1收文流程 2发文流程）',
    is_default      CHAR(1)        DEFAULT '0'                COMMENT '是否默认（0否 1是）',
    is_active       CHAR(1)        DEFAULT '1'                COMMENT '是否启用（0否 1是）',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    update_by       VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time     DATETIME                                   COMMENT '更新时间',
    remark          VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (flow_id)
) ENGINE=InnoDB COMMENT='公文流程配置表';

-- 18. 公文流程步骤表 oa_doc_flow_step
DROP TABLE IF EXISTS oa_doc_flow_step;
CREATE TABLE oa_doc_flow_step (
    step_id          BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '步骤ID',
    flow_id          BIGINT(20)     NOT NULL                  COMMENT '流程ID',
    step_order      INT            NOT NULL                  COMMENT '步骤序号',
    step_name       VARCHAR(100)    NOT NULL                  COMMENT '步骤名称',
    step_type       VARCHAR(20)    NOT NULL                  COMMENT '步骤类型（1起草 2审核 3审批 4签发 5核稿 6分发 7归档）',
    approver_type   VARCHAR(20)    DEFAULT '1'                COMMENT '审批人类型（1指定人 2部门主管 3角色 4会签）',
    approver_id     BIGINT(20)                              COMMENT '审批人ID',
    approver_name   VARCHAR(100)                            COMMENT '审批人名称',
    time_limit      INT            DEFAULT 3                  COMMENT '办理时限（天）',
    is_required     CHAR(1)        DEFAULT '1'                COMMENT '是否必须（0否 1是）',
    can_back        CHAR(1)        DEFAULT '1'                COMMENT '能否退回（0否 1是）',
    back_step_id    BIGINT(20)                              COMMENT '可退回步骤ID',
    create_time     DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (step_id)
) ENGINE=InnoDB COMMENT='公文流程步骤表';

-- 19. 公文审批记录表 oa_doc_approval
DROP TABLE IF EXISTS oa_doc_approval;
CREATE TABLE oa_doc_approval (
    approval_id      BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '审批ID',
    doc_id          BIGINT(20)      NOT NULL                  COMMENT '公文ID',
    doc_type        VARCHAR(20)     NOT NULL                  COMMENT '公文类型（1收文 2发文）',
    flow_id         BIGINT(20)     NOT NULL                  COMMENT '流程ID',
    step_id         BIGINT(20)     NOT NULL                  COMMENT '步骤ID',
    step_name       VARCHAR(100)    NOT NULL                  COMMENT '步骤名称',
    approver_id     BIGINT(20)     NOT NULL                  COMMENT '审批人ID',
    approver_name   VARCHAR(100)    NOT NULL                  COMMENT '审批人姓名',
    approval_result VARCHAR(20)     NOT NULL                  COMMENT '审批结果（1通过 2拒绝 3退回 4会签）',
    approval_comment VARCHAR(500)                            COMMENT '审批意见',
    approval_time   DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '审批时间',
    deadline        DATETIME                                  COMMENT '办理期限',
    finish_time     DATETIME                                  COMMENT '完成时间',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (approval_id)
) ENGINE=InnoDB COMMENT='公文审批记录表';

-- 20. 公文传阅记录表 oa_doc_circulation
DROP TABLE IF EXISTS oa_doc_circulation;
CREATE TABLE oa_doc_circulation (
    circulation_id   BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '传阅ID',
    doc_id          BIGINT(20)      NOT NULL                  COMMENT '公文ID',
    doc_type        VARCHAR(20)     NOT NULL                  COMMENT '公文类型（1收文 2发文）',
    user_id         BIGINT(20)      NOT NULL                  COMMENT '传阅人ID',
    user_name       VARCHAR(100)    NOT NULL                  COMMENT '传阅人姓名',
    dept_name       VARCHAR(100)                            COMMENT '部门名称',
    circulation_date DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '传阅日期',
    read_status     VARCHAR(20)    DEFAULT '0'                COMMENT '阅读状态（0未读 1已读）',
    read_time       DATETIME                                  COMMENT '阅读时间',
    is_commented    CHAR(1)        DEFAULT '0'                COMMENT '是否批注（0否 1是）',
    comment_content VARCHAR(500)                            COMMENT '批注内容',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (circulation_id)
) ENGINE=InnoDB COMMENT='公文传阅记录表';

-- 21. 公文催办记录表 oa_doc_reminder
DROP TABLE IF EXISTS oa_doc_reminder;
CREATE TABLE oa_doc_reminder (
    reminder_id      BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '催办ID',
    doc_id          BIGINT(20)      NOT NULL                  COMMENT '公文ID',
    doc_type        VARCHAR(20)     NOT NULL                  COMMENT '公文类型（1收文 2发文）',
    step_id         BIGINT(20)      NOT NULL                  COMMENT '步骤ID',
    reminder_type   VARCHAR(20)    DEFAULT '1'                COMMENT '催办类型（1超时催办 2正常催办）',
    reminder_user   BIGINT(20)      NOT NULL                  COMMENT '催办人ID',
    reminder_name   VARCHAR(100)    NOT NULL                  COMMENT '催办人姓名',
    reminder_time   DATETIME       DEFAULT CURRENT_TIMESTAMP  COMMENT '催办时间',
    reminder_count  INT            DEFAULT 1                  COMMENT '催办次数',
    response_status VARCHAR(20)    DEFAULT '0'                COMMENT '响应状态（0未响应 1已响应）',
    response_time   DATETIME                                  COMMENT '响应时间',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    PRIMARY KEY (reminder_id)
) ENGINE=InnoDB COMMENT='公文催办记录表';

-- 22. 公文归档记录表 oa_doc_archive
DROP TABLE IF EXISTS oa_doc_archive;
CREATE TABLE oa_doc_archive (
    archive_id      BIGINT(20)      NOT NULL AUTO_INCREMENT  COMMENT '归档ID',
    doc_id          BIGINT(20)      NOT NULL                  COMMENT '公文ID',
    doc_type        VARCHAR(20)     NOT NULL                  COMMENT '公文类型（1收文 2发文）',
    archive_code    VARCHAR(100)    NOT NULL                  COMMENT '归档编号',
    archive_date    DATE            NOT NULL                  COMMENT '归档日期',
    archive_type    VARCHAR(20)    DEFAULT '1'                COMMENT '归档类型（1永久 2长期 3短期）',
    archive_path    VARCHAR(500)                            COMMENT '归档路径',
    archive_user    VARCHAR(64)                             COMMENT '归档人',
    archive_status  VARCHAR(20)     DEFAULT '0'                COMMENT '归档状态（0待归档 1已归档）',
    create_by       VARCHAR(64)    DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    update_by       VARCHAR(64)    DEFAULT ''                 COMMENT '更新者',
    update_time     DATETIME                                   COMMENT '更新时间',
    remark          VARCHAR(500)   DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (archive_id)
) ENGINE=InnoDB COMMENT='公文归档记录表';

-- =============================================
-- 公文系统初始化数据
-- =============================================

-- 插入公文类型
INSERT INTO oa_doc_type (type_code, type_name, doc_category, is_active, sort_num) VALUES
('SW_001', '请示', '1', '1', 1),
('SW_002', '报告', '1', '1', 2),
('SW_003', '意见', '1', '1', 3),
('PX_001', '函', '2', '1', 4),
('PX_002', '通知', '2', '1', 5),
('PX_003', '通报', '2', '1', 6),
('XW_001', '决定', '3', '1', 7),
('XW_002', '公告', '3', '1', 8),
('XW_003', '通告', '3', '1', 9);

-- 插入默认收文流程
INSERT INTO oa_doc_flow (flow_name, flow_category, is_default, is_active) VALUES
('收文默认流程', '1', '1', '1');

-- 插入收文流程步骤
INSERT INTO oa_doc_flow_step (flow_id, step_order, step_name, step_type, approver_type, approver_id, approver_name, time_limit, is_required, can_back) VALUES
(1, 1, '签收登记', '1', '2', NULL, '办公室', 1, '1', '0'),
(1, 2, '拟办', '2', '1', NULL, NULL, 2, '1', '1'),
(1, 3, '批办', '3', '1', NULL, NULL, 2, '1', '1'),
(1, 4, '承办', '6', '1', NULL, NULL, 5, '1', '0'),
(1, 5, '归档', '7', '1', NULL, '办公室', 1, '1', '0');

-- 插入默认发文流程
INSERT INTO oa_doc_flow (flow_name, flow_category, is_default, is_active) VALUES
('发文默认流程', '2', '1', '1');

-- 插入发文流程步骤
INSERT INTO oa_doc_flow_step (flow_id, step_order, step_name, step_type, approver_type, approver_id, approver_name, time_limit, is_required, can_back) VALUES
(2, 1, '起草', '1', '1', NULL, NULL, 2, '1', '0'),
(2, 2, '核稿', '5', '1', NULL, '办公室', 2, '1', '1'),
(2, 3, '审核', '2', '1', NULL, NULL, 2, '1', '1'),
(2, 4, '审批', '3', '1', NULL, NULL, 3, '1', '1'),
(2, 5, '签发', '4', '1', NULL, NULL, 1, '1', '0'),
(2, 6, '分发', '6', '1', NULL, '办公室', 1, '1', '0'),
(2, 7, '归档', '7', '1', NULL, '办公室', 1, '1', '0');
