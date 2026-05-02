package com.ruoyi.oa.service.impl;

import java.util.List;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.oa.mapper.OaDocFlowMapper;
import com.ruoyi.oa.mapper.OaDocFlowStepMapper;
import com.ruoyi.oa.mapper.OaIncomingDocMapper;
import com.ruoyi.oa.mapper.OaOutgoingDocMapper;
import com.ruoyi.oa.domain.OaDocFlow;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.service.IOaDocFlowService;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * 公文流程配置Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaDocFlowServiceImpl implements IOaDocFlowService
{
    @Autowired
    private OaDocFlowMapper oaDocFlowMapper;

    @Autowired
    private OaDocFlowStepMapper oaDocFlowStepMapper;

    @Autowired
    private OaIncomingDocMapper oaIncomingDocMapper;

    @Autowired
    private OaOutgoingDocMapper oaOutgoingDocMapper;

    /**
     * 查询公文流程信息
     *
     * @param flowId 流程ID
     * @return 公文流程信息
     */
    @Override
    public OaDocFlow selectOaDocFlowById(Long flowId)
    {
        return oaDocFlowMapper.selectOaDocFlowById(flowId);
    }

    /**
     * 查询公文流程列表
     *
     * @param oaDocFlow 公文流程信息
     * @return 公文流程集合
     */
    @Override
    public List<OaDocFlow> selectOaDocFlowList(OaDocFlow oaDocFlow)
    {
        return oaDocFlowMapper.selectOaDocFlowList(oaDocFlow);
    }

    /**
     * 新增公文流程
     *
     * @param oaDocFlow 公文流程信息
     * @return 结果
     */
    @Override
    public int insertOaDocFlow(OaDocFlow oaDocFlow)
    {
        if (oaDocFlow.getCreateBy() == null || oaDocFlow.getCreateBy().isEmpty())
        {
            oaDocFlow.setCreateBy(SecurityUtils.getUsername());
        }
        return oaDocFlowMapper.insertOaDocFlow(oaDocFlow);
    }

    /**
     * 修改公文流程
     *
     * @param oaDocFlow 公文流程信息
     * @return 结果
     */
    @Override
    public int updateOaDocFlow(OaDocFlow oaDocFlow)
    {
        if (oaDocFlow.getUpdateBy() == null || oaDocFlow.getUpdateBy().isEmpty())
        {
            oaDocFlow.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaDocFlowMapper.updateOaDocFlow(oaDocFlow);
    }

    /**
     * 删除公文流程信息
     *
     * @param flowId 流程ID
     * @return 结果
     */
    @Override
    public int deleteOaDocFlowById(Long flowId)
    {
        return deleteOaDocFlowByIds(new Long[] { flowId });
    }

    /**
     * 批量删除公文流程信息
     *
     * @param flowIds 需要删除的流程ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteOaDocFlowByIds(Long[] flowIds)
    {
        int incomingRefCount = oaIncomingDocMapper.countByFlowIds(flowIds);
        if (incomingRefCount > 0)
        {
            throw new ServiceException("该流程已被收文数据引用，不能删除");
        }

        int outgoingRefCount = oaOutgoingDocMapper.countByFlowIds(flowIds);
        if (outgoingRefCount > 0)
        {
            throw new ServiceException("该流程已被发文数据引用，不能删除");
        }

        for (Long flowId : flowIds)
        {
            oaDocFlowStepMapper.deleteStepsByFlowId(flowId);
        }

        return oaDocFlowMapper.deleteOaDocFlowByIds(flowIds);
    }

    /**
     * 获取流程的所有步骤
     *
     * @param flowId 流程ID
     * @return 步骤列表
     */
    @Override
    public List<OaDocFlowStep> selectFlowSteps(Long flowId)
    {
        return oaDocFlowStepMapper.selectOaDocFlowStepList(flowId);
    }

    /**
     * 保存流程及步骤
     *
     * @param flow 流程信息
     * @param steps 步骤列表
     * @return 结果
     */
    @Override
    @Transactional
    public int saveFlowWithSteps(OaDocFlow flow, List<OaDocFlowStep> steps)
    {
        if ("1".equals(flow.getIsDefault()) && "0".equals(flow.getIsActive()))
        {
            throw new ServiceException("默认流程必须为启用状态");
        }

        if ("1".equals(flow.getIsDefault()))
        {
            Long excludeFlowId = flow.getFlowId();
            int defaultCount = oaDocFlowMapper.countDefaultFlowByCategory(flow.getFlowCategory(), excludeFlowId);
            if (defaultCount > 0)
            {
                throw new ServiceException("同一流程类别只能设置一个默认流程");
            }
        }

        if (steps == null || steps.isEmpty())
        {
            throw new ServiceException("请至少配置一个流程步骤");
        }

        steps.sort(Comparator.comparingInt(step -> step.getStepOrder() == null ? Integer.MAX_VALUE : step.getStepOrder()));
        for (int i = 0; i < steps.size(); i++)
        {
            OaDocFlowStep step = steps.get(i);
            int expectedOrder = i + 1;
            if (step.getStepOrder() == null || step.getStepOrder() != expectedOrder)
            {
                throw new ServiceException("流程步骤序号必须从1开始且连续");
            }
            step.setStepOrder(expectedOrder);
        }

        Set<String> stepNameSet = new HashSet<>();
        for (OaDocFlowStep step : steps)
        {
            String stepName = step.getStepName() == null ? "" : step.getStepName().trim();
            if (stepName.isEmpty())
            {
                throw new ServiceException("流程步骤名称不能为空");
            }
            if (!stepNameSet.add(stepName))
            {
                throw new ServiceException("流程步骤名称不能重复");
            }
            step.setStepName(stepName);
        }

        int result = 0;

        if (flow.getFlowId() != null && flow.getFlowId() > 0)
        {
            flow.setUpdateBy(SecurityUtils.getUsername());
            result = oaDocFlowMapper.updateOaDocFlow(flow);
            oaDocFlowStepMapper.deleteStepsByFlowId(flow.getFlowId());
        }
        else
        {
            flow.setCreateBy(SecurityUtils.getUsername());
            result = oaDocFlowMapper.insertOaDocFlow(flow);
        }

        if (steps != null && !steps.isEmpty())
        {
            for (OaDocFlowStep step : steps)
            {
                step.setFlowId(flow.getFlowId());
            }
            oaDocFlowStepMapper.batchInsertOaDocFlowStep(steps);
        }

        return result;
    }

    /**
     * 获取默认流程
     *
     * @param flowCategory 流程类别
     * @return 默认流程
     */
    @Override
    public OaDocFlow selectDefaultFlow(String flowCategory)
    {
        return oaDocFlowMapper.selectDefaultFlow(flowCategory);
    }
}
