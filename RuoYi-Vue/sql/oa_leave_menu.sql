-- OA leave menu and permissions init script
-- Re-runnable via NOT EXISTS guards

-- 1) OA root directory
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3000, 'OA', 0, 10, 'oa', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'OA root directory'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3000);

-- 2) Leave menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3001, 'Leave Request', 3000, 1, 'leave', 'system/leave/index', '', 1, 0,
  'C', '0', '0', 'oa:leave:list', 'date', 'admin', sysdate(), '', NULL, 'Leave request menu'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3001);

-- 3) Leave button permissions
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3002, 'Leave Query', 3001, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3002);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3003, 'Leave Add', 3001, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3003);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3004, 'Leave Edit', 3001, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3004);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3005, 'Leave Remove', 3001, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3005);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3006, 'Leave Approve', 3001, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3006);

-- OA ?????????????????
-- ???????��???? NOT EXISTS ???????

-- 1) OA ??????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3000, 'OA??', 0, 10, 'oa', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'OA????'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3000);

-- 2) ?????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3001, '???????', 3000, 1, 'leave', 'system/leave/index', '', 1, 0,
  'C', '0', '0', 'oa:leave:list', 'date', 'admin', sysdate(), '', NULL, '?????????'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3001);

-- 3) ????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3002, '?????', 3001, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3002);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3003, '???????', 3001, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3003);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3004, '??????', 3001, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3004);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3005, '??????', 3001, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3005);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3006, '???????', 3001, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3006);

-- OA ?????????????????
-- ????????????? NOT EXISTS ???????

-- 1) OA ?????????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3000, 'OA??', 0, 10, 'oa', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'OA????'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3000);

-- 2) ?????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3001, '???????', 3000, 1, 'leave', 'system/leave/index', '', 1, 0,
  'C', '0', '0', 'oa:leave:list', 'date', 'admin', sysdate(), '', NULL, '?????????'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3001);

-- 3) ????????
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3002, '?????', 3001, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3002);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3003, '???????', 3001, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3003);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3004, '??????', 3001, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3004);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3005, '??????', 3001, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3005);

INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  3006, '???????', 3001, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'oa:leave:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 3006);

