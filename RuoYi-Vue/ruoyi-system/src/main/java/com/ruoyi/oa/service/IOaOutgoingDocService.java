package com.ruoyi.oa.service;

import java.util.List;
import com.ruoyi.oa.domain.OaOutgoingDoc;

/**
 * 发文登记Service接口
 *
 * @author ruoyi
 */
public interface IOaOutgoingDocService
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
     * 提交发文到下一步流程
     *
     * @param docId 发文ID
     * @return 结果
     */
    public int submitToNextStep(Long docId);

    /**
     * 办理发文（审核/审批）
     *
     * @param docId 发文ID
     * @param approvalResult 审批结果
     * @param comment 审批意见
     * @return 结果
     */
    public int approveDoc(Long docId, String approvalResult, String comment);

    /**
     * 归档发文（仅已发布可归档）
     *
     * @param docId 发文ID
     * @return 结果
     */
    public int archiveDoc(Long docId);
}
