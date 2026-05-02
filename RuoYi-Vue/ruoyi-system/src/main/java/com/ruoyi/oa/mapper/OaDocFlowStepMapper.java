package com.ruoyi.oa.mapper;

import java.util.List;
import com.ruoyi.oa.domain.OaDocFlowStep;

/**
 * 公文流程步骤Mapper接口
 *
 * @author ruoyi
 */
public interface OaDocFlowStepMapper
{
    /**
     * 查询流程步骤信息
     *
     * @param stepId 步骤ID
     * @return 流程步骤信息
     */
    public OaDocFlowStep selectOaDocFlowStepById(Long stepId);

    /**
     * 查询流程步骤列表
     *
     * @param flowId 流程ID
     * @return 流程步骤集合
     */
    public List<OaDocFlowStep> selectOaDocFlowStepList(Long flowId);

    /**
     * 新增流程步骤
     *
     * @param oaDocFlowStep 流程步骤信息
     * @return 结果
     */
    public int insertOaDocFlowStep(OaDocFlowStep oaDocFlowStep);

    /**
     * 修改流程步骤
     *
     * @param oaDocFlowStep 流程步骤信息
     * @return 结果
     */
    public int updateOaDocFlowStep(OaDocFlowStep oaDocFlowStep);

    /**
     * 删除流程步骤信息
     *
     * @param stepId 步骤ID
     * @return 结果
     */
    public int deleteOaDocFlowStepById(Long stepId);

    /**
     * 批量删除流程步骤信息
     *
     * @param stepIds 需要删除的步骤ID
     * @return 结果
     */
    public int deleteOaDocFlowStepByIds(Long[] stepIds);

    /**
     * 删除流程的所有步骤
     *
     * @param flowId 流程ID
     * @return 结果
     */
    public int deleteStepsByFlowId(Long flowId);

    /**
     * 批量插入流程步骤
     *
     * @param steps 步骤列表
     * @return 结果
     */
    public int batchInsertOaDocFlowStep(List<OaDocFlowStep> steps);
}
