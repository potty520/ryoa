package com.ruoyi.oa.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.oa.mapper.OaIncomingDocMapper;
import com.ruoyi.oa.domain.OaIncomingDoc;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.service.IOaIncomingDocService;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.exception.ServiceException;

/**
 * 收文登记Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaIncomingDocServiceImpl implements IOaIncomingDocService
{
    @Autowired
    private OaIncomingDocMapper oaIncomingDocMapper;

    /**
     * 查询收文信息
     *
     * @param docId 收文ID
     * @return 收文信息
     */
    @Override
    public OaIncomingDoc selectOaIncomingDocById(Long docId)
    {
        return oaIncomingDocMapper.selectOaIncomingDocById(docId);
    }

    /**
     * 查询收文列表
     *
     * @param oaIncomingDoc 收文信息
     * @return 收文集合
     */
    @Override
    public List<OaIncomingDoc> selectOaIncomingDocList(OaIncomingDoc oaIncomingDoc)
    {
        return oaIncomingDocMapper.selectOaIncomingDocList(oaIncomingDoc);
    }

    /**
     * 新增收文
     *
     * @param oaIncomingDoc 收文信息
     * @return 结果
     */
    @Override
    public int insertOaIncomingDoc(OaIncomingDoc oaIncomingDoc)
    {
        if (oaIncomingDoc.getCreateBy() == null || oaIncomingDoc.getCreateBy().isEmpty())
        {
            oaIncomingDoc.setCreateBy(SecurityUtils.getUsername());
        }
        return oaIncomingDocMapper.insertOaIncomingDoc(oaIncomingDoc);
    }

    /**
     * 修改收文
     *
     * @param oaIncomingDoc 收文信息
     * @return 结果
     */
    @Override
    public int updateOaIncomingDoc(OaIncomingDoc oaIncomingDoc)
    {
        if (oaIncomingDoc.getUpdateBy() == null || oaIncomingDoc.getUpdateBy().isEmpty())
        {
            oaIncomingDoc.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaIncomingDocMapper.updateOaIncomingDoc(oaIncomingDoc);
    }

    /**
     * 删除收文信息
     *
     * @param docId 收文ID
     * @return 结果
     */
    @Override
    public int deleteOaIncomingDocById(Long docId)
    {
        return oaIncomingDocMapper.deleteOaIncomingDocById(docId);
    }

    /**
     * 批量删除收文信息
     *
     * @param docIds 需要删除的收文ID
     * @return 结果
     */
    @Override
    public int deleteOaIncomingDocByIds(Long[] docIds)
    {
        return oaIncomingDocMapper.deleteOaIncomingDocByIds(docIds);
    }

    /**
     * 提交收文到下一步流程
     *
     * @param docId 收文ID
     * @return 结果
     */
    @Override
    @Transactional
    public int submitToNextStep(Long docId)
    {
        OaIncomingDoc doc = oaIncomingDocMapper.selectOaIncomingDocById(docId);
        if (doc == null)
        {
            return 0;
        }

        OaDocFlowStep nextStep = oaIncomingDocMapper.selectNextFlowStep(doc.getFlowId(), doc.getCurrentStep());

        if (nextStep == null)
        {
            return oaIncomingDocMapper.updateDocStatus(docId, "4", doc.getCurrentStep(), doc.getCurrentNode());
        }

        return oaIncomingDocMapper.updateDocStatus(docId, "3", nextStep.getStepOrder(), nextStep.getStepName());
    }

    /**
     * 办理收文
     *
     * @param docId 收文ID
     * @param approvalResult 审批结果
     * @param comment 审批意见
     * @return 结果
     */
    @Override
    @Transactional
    public int approveDoc(Long docId, String approvalResult, String comment)
    {
        OaIncomingDoc doc = oaIncomingDocMapper.selectOaIncomingDocById(docId);
        if (doc == null)
        {
            return 0;
        }

        if ("2".equals(approvalResult))
        {
            return oaIncomingDocMapper.updateDocStatus(docId, "2", doc.getCurrentStep(), doc.getCurrentNode());
        }
        else if ("3".equals(approvalResult))
        {
            return oaIncomingDocMapper.updateDocStatus(docId, "2", 1, "重新登记");
        }
        else
        {
            return submitToNextStep(docId);
        }
    }

    /**
     * 更新收文状态
     *
     * @param docId 收文ID
     * @param status 状态
     * @param currentStep 当前步骤
     * @param currentNode 当前环节
     * @return 结果
     */
    @Override
    public int updateDocStatus(Long docId, String status, Integer currentStep, String currentNode)
    {
        return oaIncomingDocMapper.updateDocStatus(docId, status, currentStep, currentNode);
    }

    @Override
    public int archiveDoc(Long docId)
    {
        OaIncomingDoc doc = oaIncomingDocMapper.selectOaIncomingDocById(docId);
        if (doc == null)
        {
            return 0;
        }
        if (!"4".equals(doc.getDocStatus()))
        {
            throw new ServiceException("仅已办结收文可归档");
        }
        return oaIncomingDocMapper.updateDocStatus(docId, "5", doc.getCurrentStep(), doc.getCurrentNode());
    }
}
