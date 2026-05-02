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
import com.ruoyi.system.domain.OaSchedule;
import com.ruoyi.system.service.IOaScheduleService;

/**
 * 日程安排管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/schedule")
public class OaScheduleController extends BaseController
{
    @Autowired
    private IOaScheduleService oaScheduleService;

    /**
     * 获取日程列表
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaSchedule oaSchedule)
    {
        startPage();
        List<OaSchedule> list = oaScheduleService.selectOaScheduleList(oaSchedule);
        return getDataTable(list);
    }

    /**
     * 获取用户日程列表（用于日历展示）
     */
    @GetMapping("/user/list")
    public AjaxResult userScheduleList(
        @RequestParam(required = false) String startTime,
        @RequestParam(required = false) String endTime)
    {
        Long userId = SecurityUtils.getUserId();
        List<OaSchedule> list = oaScheduleService.selectUserScheduleList(userId, startTime, endTime);
        return success(list);
    }

    /**
     * 获取日程详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:query')")
    @GetMapping("/{scheduleId}")
    public AjaxResult getInfo(@PathVariable("scheduleId") Long scheduleId)
    {
        return success(oaScheduleService.selectOaScheduleById(scheduleId));
    }

    /**
     * 新增日程
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:add')")
    @Log(title = "日程管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaSchedule oaSchedule)
    {
        oaSchedule.setCreateBy(SecurityUtils.getUsername());
        oaSchedule.setUserId(SecurityUtils.getUserId());
        return toAjax(oaScheduleService.insertOaSchedule(oaSchedule));
    }

    /**
     * 修改日程
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:edit')")
    @Log(title = "日程管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaSchedule oaSchedule)
    {
        oaSchedule.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaScheduleService.updateOaSchedule(oaSchedule));
    }

    /**
     * 删除日程
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:remove')")
    @Log(title = "日程管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{scheduleIds}")
    public AjaxResult remove(@PathVariable Long[] scheduleIds)
    {
        return toAjax(oaScheduleService.deleteOaScheduleByIds(scheduleIds));
    }

    /**
     * 取消日程（软删除）
     */
    @PreAuthorize("@ss.hasPermi('oa:schedule:edit')")
    @Log(title = "日程管理", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{scheduleId}")
    public AjaxResult cancel(@PathVariable Long scheduleId)
    {
        OaSchedule schedule = new OaSchedule();
        schedule.setScheduleId(scheduleId);
        schedule.setStatus("1");
        schedule.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaScheduleService.updateOaSchedule(schedule));
    }
}
