package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.OaReimbursement;
import com.ruoyi.system.service.IOaReimbursementService;

/**
 * ???????????
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/reimbursement")
public class OaReimbursementController extends BaseController
{
    @Autowired
    private IOaReimbursementService oaReimbursementService;

    @PreAuthorize("@ss.hasPermi('oa:reimb:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaReimbursement oaReimbursement)
    {
        startPage();
        List<OaReimbursement> list = oaReimbursementService.selectOaReimbursementList(oaReimbursement);
        return getDataTable(list);
    }

    @Log(title = "????????", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('oa:reimb:list')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, OaReimbursement oaReimbursement)
    {
        List<OaReimbursement> list = oaReimbursementService.selectOaReimbursementList(oaReimbursement);
        ExcelUtil<OaReimbursement> util = new ExcelUtil<OaReimbursement>(OaReimbursement.class);
        util.exportExcel(response, list, "????????????");
    }

    @GetMapping("/my/list")
    public TableDataInfo myList(OaReimbursement oaReimbursement)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        List<OaReimbursement> list = oaReimbursementService.selectOaReimbursementListByUserId(userId);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:query')")
    @GetMapping("/{reimbId}")
    public AjaxResult getInfo(@PathVariable("reimbId") Long reimbId)
    {
        return success(oaReimbursementService.selectOaReimbursementByReimbId(reimbId));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:add')")
    @Log(title = "????????", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaReimbursement oaReimbursement)
    {
        oaReimbursement.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaReimbursementService.insertOaReimbursement(oaReimbursement));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:edit')")
    @Log(title = "????????", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaReimbursement oaReimbursement)
    {
        oaReimbursement.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaReimbursementService.updateOaReimbursement(oaReimbursement));
    }

    @PreAuthorize("@ss.hasAnyPermi('oa:reimb:add,oa:reimb:edit')")
    @Log(title = "????????", businessType = BusinessType.UPDATE)
    @PostMapping("/saveWithDetails")
    public AjaxResult saveWithDetails(@RequestBody OaReimbursement oaReimbursement)
    {
        return toAjax(oaReimbursementService.saveWithDetails(oaReimbursement));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:remove')")
    @Log(title = "????????", businessType = BusinessType.DELETE)
    @DeleteMapping("/{reimbIds}")
    public AjaxResult remove(@PathVariable Long[] reimbIds)
    {
        return toAjax(oaReimbursementService.deleteOaReimbursementByReimbIds(reimbIds));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:edit')")
    @Log(title = "????????", businessType = BusinessType.UPDATE)
    @PutMapping("/withdraw/{reimbId}")
    public AjaxResult withdraw(@PathVariable Long reimbId)
    {
        return toAjax(oaReimbursementService.withdrawReimbursement(reimbId));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:edit')")
    @Log(title = "????????", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{reimbId}")
    public AjaxResult submit(@PathVariable Long reimbId)
    {
        return toAjax(oaReimbursementService.submitReimbursement(reimbId));
    }

    @PreAuthorize("@ss.hasPermi('oa:reimb:approve')")
    @Log(title = "????????", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{reimbId}")
    public AjaxResult approve(@PathVariable Long reimbId,
                              @RequestParam String approvalResult,
                              @RequestParam(required = false) String approvalRemark)
    {
        if (!"1".equals(approvalResult) && !"2".equals(approvalResult))
        {
            return error("???????????????");
        }
        return toAjax(oaReimbursementService.approveReimbursement(reimbId, approvalResult, approvalRemark));
    }
}
