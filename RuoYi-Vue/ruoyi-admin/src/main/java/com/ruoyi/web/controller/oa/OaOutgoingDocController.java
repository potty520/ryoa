package com.ruoyi.web.controller.oa;

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
import com.ruoyi.oa.domain.OaOutgoingDoc;
import com.ruoyi.oa.service.IOaOutgoingDocService;

/**
 * 发文登记管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/oa/outgoing")
public class OaOutgoingDocController extends BaseController
{
    @Autowired
    private IOaOutgoingDocService oaOutgoingDocService;

    /**
     * 获取发文列表
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaOutgoingDoc oaOutgoingDoc)
    {
        startPage();
        List<OaOutgoingDoc> list = oaOutgoingDocService.selectOaOutgoingDocList(oaOutgoingDoc);
        return getDataTable(list);
    }

    /**
     * 获取发文详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:query')")
    @GetMapping("/{docId}")
    public AjaxResult getInfo(@PathVariable("docId") Long docId)
    {
        return success(oaOutgoingDocService.selectOaOutgoingDocById(docId));
    }

    /**
     * 新增发文
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:add')")
    @Log(title = "发文登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaOutgoingDoc oaOutgoingDoc)
    {
        oaOutgoingDoc.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaOutgoingDocService.insertOaOutgoingDoc(oaOutgoingDoc));
    }

    /**
     * 修改发文
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:edit')")
    @Log(title = "发文登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaOutgoingDoc oaOutgoingDoc)
    {
        oaOutgoingDoc.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaOutgoingDocService.updateOaOutgoingDoc(oaOutgoingDoc));
    }

    /**
     * 删除发文
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:remove')")
    @Log(title = "发文登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds)
    {
        return toAjax(oaOutgoingDocService.deleteOaOutgoingDocByIds(docIds));
    }

    /**
     * 提交发文到下一步
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:edit')")
    @Log(title = "发文登记", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{docId}")
    public AjaxResult submit(@PathVariable Long docId)
    {
        return toAjax(oaOutgoingDocService.submitToNextStep(docId));
    }

    /**
     * 办理发文（审批）
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:approve')")
    @Log(title = "发文办理", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{docId}")
    public AjaxResult approve(
        @PathVariable Long docId,
        @RequestParam String approvalResult,
        @RequestParam(required = false) String comment)
    {
        return toAjax(oaOutgoingDocService.approveDoc(docId, approvalResult, comment));
    }

    /**
     * 归档发文
     */
    @PreAuthorize("@ss.hasPermi('oa:outgoing:edit')")
    @Log(title = "发文归档", businessType = BusinessType.UPDATE)
    @PutMapping("/archive/{docId}")
    public AjaxResult archive(@PathVariable Long docId)
    {
        return toAjax(oaOutgoingDocService.archiveDoc(docId));
    }
}
