package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.OaLeave;
import com.ruoyi.system.service.IOaLeaveService;

/**
 * 请假申请管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/leave")
public class OaLeaveController extends BaseController
{
    @Autowired
    private IOaLeaveService oaLeaveService;

    /**
     * 获取请假申请列表
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaLeave oaLeave)
    {
        startPage();
        List<OaLeave> list = oaLeaveService.selectOaLeaveList(oaLeave);
        return getDataTable(list);
    }

    /**
     * 获取我的请假申请列表
     */
    @GetMapping("/my/list")
    public TableDataInfo myList(OaLeave oaLeave)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        List<OaLeave> list = oaLeaveService.selectOaLeaveListByUserId(userId);
        return getDataTable(list);
    }

    /**
     * 获取请假申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:query')")
    @GetMapping("/{leaveId}")
    public AjaxResult getInfo(@PathVariable("leaveId") Long leaveId)
    {
        return success(oaLeaveService.selectOaLeaveById(leaveId));
    }

    /**
     * 新增请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:add')")
    @Log(title = "请假申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaLeave oaLeave)
    {
        oaLeave.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaLeaveService.insertOaLeave(oaLeave));
    }

    /**
     * 修改请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:edit')")
    @Log(title = "请假申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaLeave oaLeave)
    {
        oaLeave.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaLeaveService.updateOaLeave(oaLeave));
    }

    /**
     * 删除请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:remove')")
    @Log(title = "请假申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{leaveIds}")
    public AjaxResult remove(@PathVariable Long[] leaveIds)
    {
        return toAjax(oaLeaveService.deleteOaLeaveByIds(leaveIds));
    }

    /**
     * 撤回请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:edit')")
    @Log(title = "请假申请", businessType = BusinessType.UPDATE)
    @PutMapping("/withdraw/{leaveId}")
    public AjaxResult withdraw(@PathVariable Long leaveId)
    {
        return toAjax(oaLeaveService.withdrawLeave(leaveId));
    }

    /**
     * 提交请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:edit')")
    @Log(title = "请假申请", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{leaveId}")
    public AjaxResult submit(@PathVariable Long leaveId)
    {
        return toAjax(oaLeaveService.submitLeave(leaveId));
    }

    /**
     * 审批请假申请
     */
    @PreAuthorize("@ss.hasPermi('oa:leave:approve')")
    @Log(title = "请假审批", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{leaveId}")
    public AjaxResult approve(@PathVariable Long leaveId, @RequestParam String approvalResult)
    {
        if (!"1".equals(approvalResult) && !"2".equals(approvalResult))
        {
            return error("审批结果参数错误");
        }
        return toAjax(oaLeaveService.approveLeave(leaveId, approvalResult));
    }
}
