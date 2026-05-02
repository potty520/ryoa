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
import com.ruoyi.system.domain.OaAnnouncement;
import com.ruoyi.system.service.IOaAnnouncementService;

/**
 * OA公告管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/announcement")
public class OaAnnouncementController extends BaseController
{
    @Autowired
    private IOaAnnouncementService oaAnnouncementService;

    /**
     * 获取公告列表
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaAnnouncement oaAnnouncement)
    {
        startPage();
        List<OaAnnouncement> list = oaAnnouncementService.selectOaAnnouncementList(oaAnnouncement);
        return getDataTable(list);
    }

    /**
     * 获取已发布的公告列表（前端展示）
     */
    @GetMapping("/published/list")
    public TableDataInfo publishedList(OaAnnouncement oaAnnouncement)
    {
        startPage();
        List<OaAnnouncement> list = oaAnnouncementService.selectPublishedAnnouncementList(oaAnnouncement);
        return getDataTable(list);
    }

    /**
     * 导出公告列表
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:export')")
    @Log(title = "公告管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(OaAnnouncement oaAnnouncement)
    {
        List<OaAnnouncement> list = oaAnnouncementService.selectOaAnnouncementList(oaAnnouncement);
    }

    /**
     * 获取公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:query')")
    @GetMapping("/{annId}")
    public AjaxResult getInfo(@PathVariable("annId") Long annId)
    {
        OaAnnouncement announcement = oaAnnouncementService.selectOaAnnouncementById(annId);
        if (announcement != null)
        {
            oaAnnouncementService.incrementViewCount(annId);
        }
        return success(announcement);
    }

    /**
     * 新增公告
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:add')")
    @Log(title = "公告管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaAnnouncement oaAnnouncement)
    {
        oaAnnouncement.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaAnnouncementService.insertOaAnnouncement(oaAnnouncement));
    }

    /**
     * 修改公告
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:edit')")
    @Log(title = "公告管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaAnnouncement oaAnnouncement)
    {
        oaAnnouncement.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaAnnouncementService.updateOaAnnouncement(oaAnnouncement));
    }

    /**
     * 删除公告
     */
    @PreAuthorize("@ss.hasPermi('oa:announcement:remove')")
    @Log(title = "公告管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{annIds}")
    public AjaxResult remove(@PathVariable Long[] annIds)
    {
        return toAjax(oaAnnouncementService.deleteOaAnnouncementByIds(annIds));
    }

    /**
     * 获取未读公告数量
     */
    @GetMapping("/unread/count")
    public AjaxResult getUnreadCount()
    {
        Long userId = SecurityUtils.getUserId();
        int count = oaAnnouncementService.selectUnreadCount(userId);
        return success(count);
    }
}
