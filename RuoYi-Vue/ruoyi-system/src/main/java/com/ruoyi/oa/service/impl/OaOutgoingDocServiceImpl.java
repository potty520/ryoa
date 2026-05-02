package com.ruoyi.oa.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.oa.mapper.OaOutgoingDocMapper;
import com.ruoyi.oa.domain.OaOutgoingDoc;
import com.ruoyi.oa.domain.OaDocFlowStep;
import com.ruoyi.oa.service.IOaOutgoingDocService;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.exception.ServiceException;

/**
 * 发文登记Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaOutgoingDocServiceImpl implements IOaOutgoingDocService
{
    @Autowired
    private OaOutgoingDocMapper oaOutgoingDocMapper;

    /**
     * 查询发文信息
     *
     * @param docId 发文ID
     * @return 发文信息
     */
    @Override
    public OaOutgoingDoc selectOaOutgoingDocById(Long docId)
    {
        return oaOutgoingDocMapper.selectOaOutgoingDocById(docId);
    }

    /**
     * 查询发文列表
     *
     * @param oaOutgoingDoc 发文信息
     * @return 发文集合
     */
    @Override
    public List<OaOutgoingDoc> selectOaOutgoingDocList(OaOutgoingDoc oaOutgoingDoc)
    {
        return oaOutgoingDocMapper.selectOaOutgoingDocList(oaOutgoingDoc);
    }

    /**
     * 新增发文
     *
     * @param oaOutgoingDoc 发文信息
     * @return 结果
     */
    @Override
    public int insertOaOutgoingDoc(OaOutgoingDoc oaOutgoingDoc)
    {
        if (oaOutgoingDoc.getCreateBy() == null || oaOutgoingDoc.getCreateBy().isEmpty())
        {
            oaOutgoingDoc.setCreateBy(SecurityUtils.getUsername());
        }
        return oaOutgoingDocMapper.insertOaOutgoingDoc(oaOutgoingDoc);
    }

    /**
     * 修改发文
     *
     * @param oaOutgoingDoc 发文信息
     * @return 结果
     */
    @Override
    public int updateOaOutgoingDoc(OaOutgoingDoc oaOutgoingDoc)
    {
        if (oaOutgoingDoc.getUpdateBy() == null || oaOutgoingDoc.getUpdateBy().isEmpty())
        {
            oaOutgoingDoc.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaOutgoingDocMapper.updateOaOutgoingDoc(oaOutgoingDoc);
    }

    /**
     * 删除发文信息
     *
     * @param docId 发文ID
     * @return 结果
     */
    @Override
    public int deleteOaOutgoingDocById(Long docId)
    {
        return oaOutgoingDocMapper.deleteOaOutgoingDocById(docId);
    }

    /**
     * 批量删除发文信息
     *
     * @param docIds 需要删除的发文ID
     * @return 结果
     */
    @Override
    public int deleteOaOutgoingDocByIds(Long[] docIds)
    {
        return oaOutgoingDocMapper.deleteOaOutgoingDocByIds(docIds);
    }

    /**
     * 提交发文到下一步流程
     *
     * @param docId 发文ID
     * @return 结果
     */
    @Override
    @Transactional
    public int submitToNextStep(Long docId)
    {
        OaOutgoingDoc doc = oaOutgoingDocMapper.selectOaOutgoingDocById(docId);
        if (doc == null)
        {
            return 0;
        }

        OaDocFlowStep nextStep = oaOutgoingDocMapper.selectNextFlowStep(doc.getFlowId(), doc.getCurrentStep());

        if (nextStep == null)
        {
            int result = oaOutgoingDocMapper.updateDocStatus(docId, "3", doc.getCurrentStep(), doc.getCurrentNode());
            if (result > 0)
            {
                OaOutgoingDoc updateDoc = new OaOutgoingDoc();
                updateDoc.setDocId(docId);
                updateDoc.setPublishDate(new Date());
                oaOutgoingDocMapper.updateOaOutgoingDoc(updateDoc);
            }
            return result;
        }

        return oaOutgoingDocMapper.updateDocStatus(docId, "2", nextStep.getStepOrder(), nextStep.getStepName());
    }

    /**
     * 办理发文
     *
     * @param docId 发文ID
     * @param approvalResult 审批结果
     * @param comment 审批意见
     * @return 结果
     */
    @Override
    @Transactional
    public int approveDoc(Long docId, String approvalResult, String comment)
    {
        OaOutgoingDoc doc = oaOutgoingDocMapper.selectOaOutgoingDocById(docId);
        if (doc == null)
        {
            return 0;
        }

        if ("2".equals(approvalResult))
        {
            return oaOutgoingDocMapper.updateDocStatus(docId, "1", doc.getCurrentStep(), doc.getCurrentNode());
        }
        else if ("3".equals(approvalResult))
        {
            return oaOutgoingDocMapper.updateDocStatus(docId, "0", 1, "重新起草");
        }
        else
        {
            return submitToNextStep(docId);
        }
    }

    @Override
    public int archiveDoc(Long docId)
    {
        OaOutgoingDoc doc = oaOutgoingDocMapper.selectOaOutgoingDocById(docId);
        if (doc == null)
        {
            return 0;
        }
        if (!"3".equals(doc.getDocStatus()))
        {
            throw new ServiceException("仅已发布发文可归档");
        }
        return oaOutgoingDocMapper.updateDocStatus(docId, "4", doc.getCurrentStep(), doc.getCurrentNode());
    }
}
