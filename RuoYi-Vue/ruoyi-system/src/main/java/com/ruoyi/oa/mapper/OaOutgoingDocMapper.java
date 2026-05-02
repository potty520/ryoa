package com.ruoyi.oa.mapper;

import java.util.List;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.domain.OaOutgoingDoc;

/**
 * 发文登记Mapper接口
 *
 * @author ruoyi
 */
public interface OaOutgoingDocMapper
{
    /**
     * 查询发文信息
     *
     * @param docId 发文ID
     * @return 发文信息
     */
    public OaOutgoingDoc selectOaOutgoingDocById(Long docId);

    /**
     * 查询发文列表
     *
     * @param oaOutgoingDoc 发文信息
     * @return 发文集合
     */
    public List<OaOutgoingDoc> selectOaOutgoingDocList(OaOutgoingDoc oaOutgoingDoc);

    /**
     * 新增发文
     *
     * @param oaOutgoingDoc 发文信息
     * @return 结果
     */
    public int insertOaOutgoingDoc(OaOutgoingDoc oaOutgoingDoc);

    /**
     * 修改发文
     *
     * @param oaOutgoingDoc 发文信息
     * @return 结果
     */
    public int updateOaOutgoingDoc(OaOutgoingDoc oaOutgoingDoc);

    /**
     * 删除发文信息
     *
     * @param docId 发文ID
     * @return 结果
     */
    public int deleteOaOutgoingDocById(Long docId);

    /**
     * 批量删除发文信息
     *
     * @param docIds 需要删除的发文ID
     * @return 结果
     */
    public int deleteOaOutgoingDocByIds(Long[] docIds);

    /**
     * 更新发文状态
     *
     * @param docId 发文ID
     * @param status 状态
     * @param currentStep 当前步骤
     * @param currentNode 当前环节
     * @return 结果
     */
    public int updateDocStatus(Long docId, String status, Integer currentStep, String currentNode);

    /**
     * 获取下一个流程步骤
     *
     * @param flowId 流程ID
     * @param currentStep 当前步骤
     * @return 下一步骤信息
     */
    public OaDocFlowStep selectNextFlowStep(Long flowId, Integer currentStep);

    /**
     * 统计引用指定流程的发文数量
     *
     * @param flowIds 流程ID数组
     * @return 数量
     */
    public int countByFlowIds(Long[] flowIds);
}
