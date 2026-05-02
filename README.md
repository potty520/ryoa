# RuoYi-OA 系统配置指南

## 一、数据库初始化

在开始配置之前，请先执行数据库脚本：

```bash
# 登录 MySQL 并创建数据库
mysql -u root -p
CREATE DATABASE IF NOT EXISTS ry_oa DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE ry_oa;

# 执行 OA 系统数据库脚本
source c:/Users/Administrator/Desktop/ryoa/RuoYi-Vue/sql/oa_system.sql;
```

---

## 二、菜单配置

### 2.1 菜单配置说明

在 RuoYi 系统中，菜单配置位于 `sys_menu` 表。以下是需要插入的 OA 模块菜单数据：

| 菜单ID | 菜单名称 | 上级菜单 | 路由地址 | 组件路径 | 菜单图标 | 排序号 | 权限标识 |
|--------|----------|----------|----------|----------|----------|--------|----------|
| 2000 | OA系统 | 0 (根目录) | /oa | - | system | 10 | - |
| 2001 | 通知公告 | 2000 | /oa/announcement | system/announcement/index | message | 1 | oa:announcement:list |
| 2002 | 日程管理 | 2000 | /oa/schedule | system/schedule/index | clock | 2 | oa:schedule:list |
| 2003 | 请假申请 | 2000 | /oa/leave | system/leave/index | leave | 3 | oa:leave:list |
| 2004 | 公文管理 | 2000 | - | - | document | 4 | - |
| 2005 | 收文管理 | 2004 | /oa/incoming | oa/incoming/index | download | 1 | oa:incoming:list |
| 2006 | 发文管理 | 2004 | /oa/outgoing | oa/outgoing/index | upload | 2 | oa:outgoing:list |
| 2007 | 流程配置 | 2004 | /oa/docflow | oa/docflow/index | workflow | 3 | oa:docflow:list |

### 2.2 菜单配置 SQL

执行以下 SQL 语句插入 OA 模块菜单：

```sql
-- 插入 OA 系统根菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('OA系统', 0, 10, '/oa', NULL, 'M', '0', '0', '', 'system', 'admin', sysdate(), 'OA系统根菜单');

-- 设置上级菜单ID（获取刚插入的菜单ID）
SET @oa_parent_id = LAST_INSERT_ID();

-- 插入通知公告菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('通知公告', @oa_parent_id, 1, 'announcement', 'system/announcement/index', 'C', '0', '0', 'oa:announcement:list', 'message', 'admin', sysdate(), '通知公告菜单');

-- 插入日程管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('日程管理', @oa_parent_id, 2, 'schedule', 'system/schedule/index', 'C', '0', '0', 'oa:schedule:list', 'clock', 'admin', sysdate(), '日程管理菜单');

-- 插入请假申请菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('请假申请', @oa_parent_id, 3, 'leave', 'system/leave/index', 'C', '0', '0', 'oa:leave:list', 'leave', 'admin', sysdate(), '请假申请菜单');

-- 插入公文管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('公文管理', @oa_parent_id, 4, 'doc', NULL, 'M', '0', '0', '', 'document', 'admin', sysdate(), '公文管理目录');

-- 获取公文管理菜单ID
SET @doc_parent_id = LAST_INSERT_ID();

-- 插入收文管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('收文管理', @doc_parent_id, 1, 'incoming', 'oa/incoming/index', 'C', '0', '0', 'oa:incoming:list', 'download', 'admin', sysdate(), '收文管理菜单');

-- 插入发文管理菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('发文管理', @doc_parent_id, 2, 'outgoing', 'oa/outgoing/index', 'C', '0', '0', 'oa:outgoing:list', 'upload', 'admin', sysdate(), '发文管理菜单');

-- 插入流程配置菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('流程配置', @doc_parent_id, 3, 'docflow', 'oa/docflow/index', 'C', '0', '0', 'oa:docflow:list', 'workflow', 'admin', sysdate(), '公文流程配置菜单');
```

### 2.3 手动配置步骤

如果通过 SQL 配置有问题，也可以手动在界面配置：

#### 步骤 1：登录系统管理
1. 使用管理员账号登录 RuoYi 系统
2. 进入 **系统管理** → **菜单管理**

#### 步骤 2：添加 OA 根菜单
```
菜单名称：OA系统
上级菜单：根目录
路由地址：/oa
菜单图标：system
显示排序：10
菜单类型：目录
```

#### 步骤 3：添加子菜单
按照上述表格，为每个功能模块添加子菜单

#### 步骤 4：配置组件路径
```
通知公告：system/announcement/index
日程管理：system/schedule/index
请假申请：system/leave/index
收文管理：oa/incoming/index
发文管理：oa/outgoing/index
流程配置：oa/docflow/index
```

---

## 三、权限标识配置

### 3.1 权限标识说明

权限标识（Permission）用于控制用户对功能的访问权限。OA 系统定义了以下权限标识：

| 模块 | 权限标识 | 说明 |
|------|----------|------|
| 通知公告 | oa:announcement:list | 查看公告列表 |
| 通知公告 | oa:announcement:query | 查看公告详情 |
| 通知公告 | oa:announcement:add | 新增公告 |
| 通知公告 | oa:announcement:edit | 修改公告 |
| 通知公告 | oa:announcement:remove | 删除公告 |
| 通知公告 | oa:announcement:export | 导出公告 |
| 日程管理 | oa:schedule:list | 查看日程列表 |
| 日程管理 | oa:schedule:query | 查看日程详情 |
| 日程管理 | oa:schedule:add | 新增日程 |
| 日程管理 | oa:schedule:edit | 修改日程 |
| 日程管理 | oa:schedule:remove | 删除日程 |
| 请假申请 | oa:leave:list | 查看请假列表 |
| 请假申请 | oa:leave:query | 查看请假详情 |
| 请假申请 | oa:leave:add | 新增请假 |
| 请假申请 | oa:leave:edit | 修改请假 |
| 请假申请 | oa:leave:remove | 删除请假 |
| 收文管理 | oa:incoming:list | 查看收文列表 |
| 收文管理 | oa:incoming:query | 查看收文详情 |
| 收文管理 | oa:incoming:add | 新增收文 |
| 收文管理 | oa:incoming:edit | 修改收文 |
| 收文管理 | oa:incoming:remove | 删除收文 |
| 收文管理 | oa:incoming:approve | 审批收文 |
| 发文管理 | oa:outgoing:list | 查看发文列表 |
| 发文管理 | oa:outgoing:query | 查看发文详情 |
| 发文管理 | oa:outgoing:add | 新增发文 |
| 发文管理 | oa:outgoing:edit | 修改发文 |
| 发文管理 | oa:outgoing:remove | 删除发文 |
| 发文管理 | oa:outgoing:approve | 审批发文 |
| 流程配置 | oa:docflow:list | 查看流程列表 |
| 流程配置 | oa:docflow:query | 查看流程详情 |
| 流程配置 | oa:docflow:add | 新增流程 |
| 流程配置 | oa:docflow:edit | 修改流程 |
| 流程配置 | oa:docflow:remove | 删除流程 |

### 3.2 权限标识 SQL

执行以下 SQL 为超级管理员添加所有 OA 权限：

```sql
-- 为角色添加 OA 系统权限（假设角色ID为1，即超级管理员）
-- 通知公告权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:announcement:%';

-- 日程管理权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:schedule:%';

-- 请假申请权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:leave:%';

-- 收文管理权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:incoming:%';

-- 发文管理权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:outgoing:%';

-- 流程配置权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:docflow:%';
```

### 3.3 手动配置权限步骤

#### 步骤 1：进入角色管理
1. 使用管理员账号登录 RuoYi 系统
2. 进入 **系统管理** → **角色管理**

#### 步骤 2：编辑角色权限
1. 找到需要配置权限的角色（如：超级管理员）
2. 点击 **编辑** 按钮
3. 在权限配置中，勾选 **OA系统** 目录下的所有菜单

#### 步骤 3：分配菜单权限
勾选以下菜单及其子菜单：
- ✅ OA系统
  - ✅ 通知公告（及其所有按钮权限）
  - ✅ 日程管理（及其所有按钮权限）
  - ✅ 请假申请（及其所有按钮权限）
  - ✅ 公文管理
    - ✅ 收文管理（及其所有按钮权限）
    - ✅ 发文管理（及其所有按钮权限）
    - ✅ 流程配置（及其所有按钮权限）

#### 步骤 4：保存配置
点击 **确定** 按钮保存配置

---

## 四、功能菜单权限对应关系

### 4.1 通知公告模块

| 功能按钮 | 权限标识 | 说明 |
|----------|----------|------|
| 查看列表 | oa:announcement:list | 进入通知公告列表页面 |
| 查看详情 | oa:announcement:query | 查看单条公告详情 |
| 新增公告 | oa:announcement:add | 新增公告按钮 |
| 修改公告 | oa:announcement:edit | 修改公告按钮 |
| 删除公告 | oa:announcement:remove | 删除公告按钮 |
| 导出公告 | oa:announcement:export | 导出公告数据 |

### 4.2 日程管理模块

| 功能按钮 | 权限标识 | 说明 |
|----------|----------|------|
| 查看列表 | oa:schedule:list | 进入日程管理列表页面 |
| 查看详情 | oa:schedule:query | 查看单条日程详情 |
| 新增日程 | oa:schedule:add | 新增日程按钮 |
| 修改日程 | oa:schedule:edit | 修改日程按钮 |
| 删除日程 | oa:schedule:remove | 删除日程按钮 |

### 4.3 请假申请模块

| 功能按钮 | 权限标识 | 说明 |
|----------|----------|------|
| 查看列表 | oa:leave:list | 进入请假申请列表页面 |
| 查看详情 | oa:leave:query | 查看单条请假详情 |
| 新增请假 | oa:leave:add | 新增请假按钮 |
| 修改请假 | oa:leave:edit | 修改请假按钮 |
| 删除请假 | oa:leave:remove | 删除请假按钮 |

### 4.4 公文管理模块

| 功能按钮 | 权限标识 | 说明 |
|----------|----------|------|
| 查看列表 | oa:incoming:list | 进入收文列表页面 |
| 查看详情 | oa:incoming:query | 查看收文详情 |
| 新增收文 | oa:incoming:add | 新增收文按钮 |
| 修改收文 | oa:incoming:edit | 修改收文按钮 |
| 删除收文 | oa:incoming:remove | 删除收文按钮 |
| 审批收文 | oa:incoming:approve | 审批收文按钮 |
| 查看列表 | oa:outgoing:list | 进入发文列表页面 |
| 查看详情 | oa:outgoing:query | 查看发文详情 |
| 新增发文 | oa:outgoing:add | 新增发文按钮 |
| 修改发文 | oa:outgoing:edit | 修改发文按钮 |
| 删除发文 | oa:outgoing:remove | 删除发文按钮 |
| 审批发文 | oa:outgoing:approve | 审批发文按钮 |
| 查看列表 | oa:docflow:list | 进入流程配置列表页面 |
| 查看详情 | oa:docflow:query | 查看流程详情 |
| 新增流程 | oa:docflow:add | 新增流程按钮 |
| 修改流程 | oa:docflow:edit | 修改流程按钮 |
| 删除流程 | oa:docflow:remove | 删除流程按钮 |

---

## 五、验证配置

配置完成后，请按照以下步骤验证：

### 5.1 清除缓存
```sql
-- 清除系统缓存
DELETE FROM sys_cache WHERE cache_key LIKE '%:login_tokens:%';
DELETE FROM sys_cache WHERE cache_key LIKE '%:user_info:%';
```

### 5.2 重新登录
1. 退出当前登录
2. 重新登录系统
3. 检查左侧菜单栏是否显示 **OA系统** 菜单

### 5.3 测试功能
使用管理员账号登录后，验证以下功能：
- ✅ 是否能看到 OA系统 菜单
- ✅ 点击各子菜单是否正常跳转
- ✅ 各功能按钮是否正常显示

---

## 六、常见问题

### Q1: 菜单不显示？
**解决方案：**
1. 检查菜单是否正确插入到 `sys_menu` 表
2. 确认菜单的 `visible` 字段为 '0'（显示）
3. 确认当前用户角色是否分配了该菜单权限
4. 清除浏览器缓存后重新登录

### Q2: 按钮权限不显示？
**解决方案：**
1. 检查按钮权限标识是否正确配置在菜单的 `perms` 字段
2. 确认角色是否勾选了对应的菜单权限
3. 在角色管理中重新分配权限

### Q3: SQL 执行报错？
**解决方案：**
1. 检查 SQL 语法是否正确
2. 确认数据库连接正常
3. 确认数据库用户有足够的权限

---

## 七、快速配置脚本

如果需要快速完成所有配置，可以执行以下一键配置脚本：

```sql
-- ============================================
-- RuoYi-OA 系统一键配置脚本
-- 执行前请确保已执行 oa_system.sql
-- ============================================

-- 1. 插入 OA 系统菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('OA系统', 0, 10, '/oa', NULL, 'M', '0', '0', '', 'system', 'admin', sysdate(), 'OA系统根菜单');

SET @oa_parent_id = LAST_INSERT_ID();

-- 2. 插入通知公告菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES
('通知公告', @oa_parent_id, 1, 'announcement', 'system/announcement/index', 'C', '0', '0', 'oa:announcement:list', 'message', 'admin', sysdate()),
('日程管理', @oa_parent_id, 2, 'schedule', 'system/schedule/index', 'C', '0', '0', 'oa:schedule:list', 'clock', 'admin', sysdate()),
('请假申请', @oa_parent_id, 3, 'leave', 'system/leave/index', 'C', '0', '0', 'oa:leave:list', 'leave', 'admin', sysdate());

-- 3. 插入公文管理目录
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES ('公文管理', @oa_parent_id, 4, 'doc', NULL, 'M', '0', '0', '', 'document', 'admin', sysdate());

SET @doc_parent_id = LAST_INSERT_ID();

-- 4. 插入公文管理子菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
VALUES
('收文管理', @doc_parent_id, 1, 'incoming', 'oa/incoming/index', 'C', '0', '0', 'oa:incoming:list', 'download', 'admin', sysdate()),
('发文管理', @doc_parent_id, 2, 'outgoing', 'oa/outgoing/index', 'C', '0', '0', 'oa:outgoing:list', 'upload', 'admin', sysdate()),
('流程配置', @doc_parent_id, 3, 'docflow', 'oa/docflow/index', 'C', '0', '0', 'oa:docflow:list', 'workflow', 'admin', sysdate());

-- 5. 为超级管理员（role_id=1）分配所有 OA 权限
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE perms LIKE 'oa:%' OR parent_id = @oa_parent_id;

-- 6. 显示配置结果
SELECT '菜单配置完成！' AS result;
SELECT menu_id, menu_name, perms FROM sys_menu WHERE perms LIKE 'oa:%' OR parent_id = @oa_parent_id ORDER BY parent_id, order_num;
```

---

## 八、技术支持

如果配置过程中遇到问题，请检查：
1. 数据库是否正确初始化
2. 菜单数据是否完整插入
3. 权限是否正确分配
4. 用户是否重新登录

---

**文档版本：** v1.0
**生成日期：** 2026-04-19
**适用版本：** RuoYi-Vue 3.9.2+
