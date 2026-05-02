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
import com.ruoyi.oa.domain.OaDocFlow;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.service.IOaDocFlowService;

/**
 * 公文流程配置管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/oa/docflow")
public class OaDocFlowController extends BaseController
{
    @Autowired
    private IOaDocFlowService oaDocFlowService;

    /**
     * 获取流程列表
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaDocFlow oaDocFlow)
    {
        startPage();
        List<OaDocFlow> list = oaDocFlowService.selectOaDocFlowList(oaDocFlow);
        return getDataTable(list);
    }

    /**
     * 获取流程详细信息
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:query')")
    @GetMapping("/{flowId}")
    public AjaxResult getInfo(@PathVariable("flowId") Long flowId)
    {
        OaDocFlow flow = oaDocFlowService.selectOaDocFlowById(flowId);
        List<OaDocFlowStep> steps = oaDocFlowService.selectFlowSteps(flowId);
        return success().put("flow", flow).put("steps", steps);
    }

    /**
     * 新增流程
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:add')")
    @Log(title = "公文流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaDocFlow oaDocFlow)
    {
        oaDocFlow.setCreateBy(SecurityUtils.getUsername());
        return toAjax(oaDocFlowService.insertOaDocFlow(oaDocFlow));
    }

    /**
     * 修改流程
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:edit')")
    @Log(title = "公文流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaDocFlow oaDocFlow)
    {
        oaDocFlow.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(oaDocFlowService.updateOaDocFlow(oaDocFlow));
    }

    /**
     * 删除流程
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:remove')")
    @Log(title = "公文流程配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{flowIds}")
    public AjaxResult remove(@PathVariable Long[] flowIds)
    {
        return toAjax(oaDocFlowService.deleteOaDocFlowByIds(flowIds));
    }

    /**
     * 保存流程及步骤
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:edit')")
    @Log(title = "公文流程配置", businessType = BusinessType.UPDATE)
    @PostMapping("/saveWithSteps")
    public AjaxResult saveWithSteps(@RequestBody OaDocFlow flow)
    {
        return toAjax(oaDocFlowService.saveFlowWithSteps(flow, flow.getSteps()));
    }

    /**
     * 获取流程的步骤列表
     */
    @PreAuthorize("@ss.hasPermi('oa:docflow:query')")
    @GetMapping("/steps/{flowId}")
    public AjaxResult getSteps(@PathVariable("flowId") Long flowId)
    {
        return success(oaDocFlowService.selectFlowSteps(flowId));
    }

    /**
     * 获取默认流程
     */
    @GetMapping("/default/{category}")
    public AjaxResult getDefaultFlow(@PathVariable("category") String category)
    {
        return success(oaDocFlowService.selectDefaultFlow(category));
    }
}
