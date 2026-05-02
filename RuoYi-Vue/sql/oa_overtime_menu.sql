-- OA overtime menu and permissions init script
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

-- 2) Overtime menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3301, '�Ӱ�����', 3100, 3, 'overtime', 'system/overtime/index', '', 1, 0,
  'C', '0', '0', 'oa:overtime:list', 'time', 'admin', sysdate(), '', NULL, '�Ӱ�����˵�'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3301);

-- 3) Overtime button permissions
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3302, '�Ӱ��ѯ', 3301, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:overtime:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3302);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3303, '�Ӱ�����', 3301, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:overtime:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3303);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3304, '�Ӱ��޸�', 3301, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:overtime:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3304);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3305, '�Ӱ�ɾ��', 3301, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:overtime:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3305);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3306, '�Ӱ�����', 3301, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:overtime:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3306);

