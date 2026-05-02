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
import com.ruoyi.oa.domain.OaIncomingDoc;
import com.ruoyi.oa.service.IOaIncomingDocService;

/**
 * 收文登记管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/oa/incoming")
public class OaIncomingDocController extends BaseController
{
    @Autowired
    private IOaIncomingDocService oaIncomingDocService;

    /**
     * 获取收文列表
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaIncomingDoc oaIncomingDoc)
    {
        startPage();
        List<OaIncomingDoc> list = oaIncomingDocService.selectOaIncomingDocList(oaIncomingDoc);
        return getDataTable(list);
    }

    /**
     * 获取收文详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:query')")
    @GetMapping("/{docId}")
    public AjaxResult getInfo(@PathVariable("docId") Long docId)
    {
        return success(oaIncomingDocService.selectOaIncomingDocById(docId));
    }

    /**
     * 新增收文
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:add')")
    @Log(title = "收文登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaIncomingDoc oaIncomingDoc)
    {
        oaIncomingDoc.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaIncomingDocService.insertOaIncomingDoc(oaIncomingDoc));
    }

    /**
     * 修改收文
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:edit')")
    @Log(title = "收文登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaIncomingDoc oaIncomingDoc)
    {
        oaIncomingDoc.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaIncomingDocService.updateOaIncomingDoc(oaIncomingDoc));
    }

    /**
     * 删除收文
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:remove')")
    @Log(title = "收文登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{docIds}")
    public AjaxResult remove(@PathVariable Long[] docIds)
    {
        return toAjax(oaIncomingDocService.deleteOaIncomingDocByIds(docIds));
    }

    /**
     * 提交收文到下一步
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:edit')")
    @Log(title = "收文登记", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{docId}")
    public AjaxResult submit(@PathVariable Long docId)
    {
        return toAjax(oaIncomingDocService.submitToNextStep(docId));
    }

    /**
     * 办理收文（审批）
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:approve')")
    @Log(title = "收文办理", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{docId}")
    public AjaxResult approve(
        @PathVariable Long docId,
        @RequestParam String approvalResult,
        @RequestParam(required = false) String comment)
    {
        return toAjax(oaIncomingDocService.approveDoc(docId, approvalResult, comment));
    }

    /**
     * 签收收文
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:edit')")
    @Log(title = "收文签收", businessType = BusinessType.UPDATE)
    @PutMapping("/receive/{docId}")
    public AjaxResult receive(@PathVariable Long docId)
    {
        return toAjax(oaIncomingDocService.updateDocStatus(docId, "1", 1, "已签收"));
    }

    /**
     * 归档收文
     */
    @PreAuthorize("@ss.hasPermi('oa:incoming:edit')")
    @Log(title = "收文归档", businessType = BusinessType.UPDATE)
    @PutMapping("/archive/{docId}")
    public AjaxResult archive(@PathVariable Long docId)
    {
        return toAjax(oaIncomingDocService.archiveDoc(docId));
    }
}
