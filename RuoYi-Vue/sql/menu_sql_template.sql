-- Generic menu SQL template (idempotent)
-- Usage:
-- 1) Replace MODULE values and IDs
-- 2) Execute repeatedly safely

-- Suggested ID plan:
-- Root dir: 31x0
-- Module page: 31x1
-- Buttons: 31x2 ~ 31x9

-- [ROOT] directory menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9990, 'MODULE_ROOT_NAME', 0, 10, 'module-root-path', NULL, '', 1, 0,
  'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, 'root directory'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9990);

-- [PAGE] module page menu
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9991, 'MODULE_PAGE_NAME', 9990, 1, 'module-page-path', 'module/component/index', '', 1, 0,
  'C', '0', '0', 'module:entity:list', 'date', 'admin', sysdate(), '', NULL, 'module page menu'
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9991);

-- [BUTTON] query
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9992, 'Query', 9991, 1, '#', '', '', 1, 0,
  'F', '0', '0', 'module:entity:query', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9992);

-- [BUTTON] add
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9993, 'Add', 9991, 2, '#', '', '', 1, 0,
  'F', '0', '0', 'module:entity:add', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9993);

-- [BUTTON] edit
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9994, 'Edit', 9991, 3, '#', '', '', 1, 0,
  'F', '0', '0', 'module:entity:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9994);

-- [BUTTON] remove
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9995, 'Remove', 9991, 4, '#', '', '', 1, 0,
  'F', '0', '0', 'module:entity:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9995);

-- [BUTTON] approve (optional)
INSERT INTO sys_menu
(
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT
  9996, 'Approve', 9991, 5, '#', '', '', 1, 0,
  'F', '0', '0', 'module:entity:approve', '#', 'admin', sysdate(), '', NULL, ''
FROM dual
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_id = 9996);

