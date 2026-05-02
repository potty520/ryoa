package com.ruoyi.oa.service;

import java.util.List;
import com.ruoyi.oa.domain.OaIncomingDoc;

/**
 * 收文登记Service接口
 *
 * @author ruoyi
 */
public interface IOaIncomingDocService
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
     * 提交收文到下一步流程
     *
     * @param docId 收文ID
     * @return 结果
     */
    public int submitToNextStep(Long docId);

    /**
     * 办理收文（审核/审批）
     *
     * @param docId 收文ID
     * @param approvalResult 审批结果（1通过 2拒绝 3退回）
     * @param comment 审批意见
     * @return 结果
     */
    public int approveDoc(Long docId, String approvalResult, String comment);

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
     * 归档收文（仅已办结可归档）
     *
     * @param docId 收文ID
     * @return 结果
     */
    public int archiveDoc(Long docId);
}
