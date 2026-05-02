-- OA reimbursement menu and permissions init script
-- Re-runnable via NOT EXISTS guards

-- 1) OA root directory (reuse if exists)
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3100, 'OA�칫', 0, 10, 'oa', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'OA�칫Ŀ¼'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3100);

-- 2) Reimbursement menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3201, '��������', 3100, 2, 'reimbursement', 'system/reimbursement/index', '', 1, 0,
  'C', '0', '0', 'oa:reimb:list', 'money', 'admin', sysdate(), '', NULL, '��������˵�'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3201);

-- 3) Reimbursement button permissions
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3202, '������ѯ', 3201, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:reimb:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3202);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3203, '��������', 3201, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:reimb:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3203);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3204, '�����޸�', 3201, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:reimb:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3204);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3205, '����ɾ��', 3201, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:reimb:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3205);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3206, '��������', 3201, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:reimb:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3206);

