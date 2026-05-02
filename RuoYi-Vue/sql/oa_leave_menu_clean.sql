-- OA leave menu and permissions init script (clean)
-- Re-runnable via NOT EXISTS guards

-- 1) OA root directory
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3100, 'OA办公', 0, 10, 'oa', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'OA办公目录'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3100);

-- 2) Leave menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3101, '请假申请', 3100, 1, 'leave', 'system/leave/index', '', 1, 0,
  'C', '0', '0', 'oa:leave:list', 'date', 'admin', sysdate(), '', NULL, '请假申请菜单'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3101);

-- 3) Leave button permissions
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3102, '请假查询', 3101, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3102);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3103, '请假新增', 3101, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3103);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3104, '请假修改', 3101, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3104);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3105, '请假删除', 3101, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3105);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3106, '请假审批', 3101, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3106);

