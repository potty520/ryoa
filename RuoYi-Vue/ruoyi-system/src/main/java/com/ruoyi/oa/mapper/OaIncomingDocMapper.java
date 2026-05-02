package com.ruoyi.oa.mapper;

import java.util.List;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.domain.OaIncomingDoc;

/**
 * 收文登记Mapper接口
 *
 * @author ruoyi
 */
public interface OaIncomingDocMapper
{
    /**
     * 查询收文信息
     *
     * @param docId 收文ID
     * @return 收文信息
     */
    public OaIncomingDoc selectOaIncomingDocById(Long docId);

    /**
     * 查询收文列表
     *
     * @param oaIncomingDoc 收文信息
     * @return 收文集合
     */
    public List<OaIncomingDoc> selectOaIncomingDocList(OaIncomingDoc oaIncomingDoc);

    /**
     * 新增收文
     *
     * @param oaIncomingDoc 收文信息
     * @return 结果
     */
    public int insertOaIncomingDoc(OaIncomingDoc oaIncomingDoc);

    /**
     * 修改收文
     *
     * @param oaIncomingDoc 收文信息
     * @return 结果
     */
    public int updateOaIncomingDoc(OaIncomingDoc oaIncomingDoc);

    /**
     * 删除收文信息
     *
     * @param docId 收文ID
     * @return 结果
     */
    public int deleteOaIncomingDocById(Long docId);

    /**
     * 批量删除收文信息
     *
     * @param docIds 需要删除的收文ID
     * @return 结果
     */
    public int deleteOaIncomingDocByIds(Long[] docIds);

    /**
     * 更新收文状态
     *
     * @param docId 收文ID
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
     * 统计引用指定流程的收文数量
     *
     * @param flowIds 流程ID数组
     * @return 数量
     */
    public int countByFlowIds(Long[] flowIds);
}
