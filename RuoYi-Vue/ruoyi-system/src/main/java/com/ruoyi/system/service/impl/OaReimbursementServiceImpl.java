package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.OaReimbursement;
import com.ruoyi.system.domain.OaReimbursementApproveLog;
import com.ruoyi.system.domain.OaReimbursementDetail;
import com.ruoyi.system.mapper.OaReimbursementApproveLogMapper;
import com.ruoyi.system.mapper.OaReimbursementDetailMapper;
import com.ruoyi.system.mapper.OaReimbursementMapper;
import com.ruoyi.system.service.IOaReimbursementService;

/**
 * ????????Service??????
 *
 * @author ruoyi
 */
@Service
public class OaReimbursementServiceImpl implements IOaReimbursementService
{
    @Autowired
    private OaReimbursementMapper oaReimbursementMapper;

    @Autowired
    private OaReimbursementDetailMapper oaReimbursementDetailMapper;

    @Autowired
    private OaReimbursementApproveLogMapper oaReimbursementApproveLogMapper;

    @Override
    public OaReimbursement selectOaReimbursementByReimbId(Long reimbId)
    {
        OaReimbursement reimb = oaReimbursementMapper.selectOaReimbursementByReimbId(reimbId);
        if (reimb != null)
        {
            reimb.setDetails(oaReimbursementDetailMapper.selectDetailsByReimbId(reimbId));
            reimb.setApproveLogs(oaReimbursementApproveLogMapper.selectLogsByReimbId(reimbId));
        }
        return reimb;
    }

    @Override
    public List<OaReimbursement> selectOaReimbursementList(OaReimbursement oaReimbursement)
    {
        return oaReimbursementMapper.selectOaReimbursementList(oaReimbursement);
    }

    @Override
    public List<OaReimbursement> selectOaReimbursementListByUserId(Long userId)
    {
        return oaReimbursementMapper.selectOaReimbursementListByUserId(userId);
    }

    @Override
    public int insertOaReimbursement(OaReimbursement oaReimbursement)
    {
        oaReimbursement.setApplyTime(new Date());
        oaReimbursement.setCurrentStep(1);
        oaReimbursement.setApprovalStatus("0");
        oaReimbursement.setPaymentStatus("0");
        return oaReimbursementMapper.insertOaReimbursement(oaReimbursement);
    }

    @Override
    public int updateOaReimbursement(OaReimbursement oaReimbursement)
    {
        OaReimbursement existing = requireExists(oaReimbursement.getReimbId());
        checkOwner(existing, "?????????????????");
        if (!"0".equals(existing.getApprovalStatus()))
        {
            throw new ServiceException("????????????????????");
        }
        return oaReimbursementMapper.updateOaReimbursement(oaReimbursement);
    }

    @Override
    @Transactional
    public int saveWithDetails(OaReimbursement oaReimbursement)
    {
        if (oaReimbursement.getDetails() == null || oaReimbursement.getDetails().isEmpty())
        {
            throw new ServiceException("????????????????????");
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (OaReimbursementDetail detail : oaReimbursement.getDetails())
        {
            if (detail.getExpenseType() == null || detail.getExpenseType().trim().isEmpty())
            {
                throw new ServiceException("??????????????");
            }
            if (detail.getExpenseDate() == null)
            {
                throw new ServiceException("??????????????");
            }
            if (detail.getExpenseAmount() == null || detail.getExpenseAmount().signum() <= 0)
            {
                throw new ServiceException("????????????0");
            }
            sum = sum.add(detail.getExpenseAmount());
        }
        if (oaReimbursement.getTotalAmount() == null || oaReimbursement.getTotalAmount().signum() <= 0)
        {
            oaReimbursement.setTotalAmount(sum);
        }

        int result;
        if (oaReimbursement.getReimbId() == null)
        {
            oaReimbursement.setCreateBy(SecurityUtils.getUsername());
            result = insertOaReimbursement(oaReimbursement);
        }
        else
        {
            oaReimbursement.setUpdateBy(SecurityUtils.getUsername());
            result = updateOaReimbursement(oaReimbursement);
            oaReimbursementDetailMapper.deleteDetailsByReimbId(oaReimbursement.getReimbId());
        }

        for (OaReimbursementDetail detail : oaReimbursement.getDetails())
        {
            detail.setReimbId(oaReimbursement.getReimbId());
            detail.setCreateBy(SecurityUtils.getUsername());
        }
        oaReimbursementDetailMapper.batchInsertDetails(oaReimbursement.getDetails());
        return result;
    }

    @Override
    public int deleteOaReimbursementByReimbIds(Long[] reimbIds)
    {
        for (Long reimbId : reimbIds)
        {
            OaReimbursement existing = requireExists(reimbId);
            checkOwner(existing, "??????????????????");
            if (!"0".equals(existing.getApprovalStatus()) && !"3".equals(existing.getApprovalStatus()))
            {
                throw new ServiceException("??????????????????????????");
            }
        }
        for (Long reimbId : reimbIds)
        {
            oaReimbursementDetailMapper.deleteDetailsByReimbId(reimbId);
        }
        return oaReimbursementMapper.deleteOaReimbursementByReimbIds(reimbIds);
    }

    @Override
    public int deleteOaReimbursementByReimbId(Long reimbId)
    {
        OaReimbursement existing = requireExists(reimbId);
        checkOwner(existing, "??????????????????");
        if (!"0".equals(existing.getApprovalStatus()) && !"3".equals(existing.getApprovalStatus()))
        {
            throw new ServiceException("??????????????????????????");
        }
        oaReimbursementDetailMapper.deleteDetailsByReimbId(reimbId);
        return oaReimbursementMapper.deleteOaReimbursementByReimbId(reimbId);
    }

    @Override
    public int submitReimbursement(Long reimbId)
    {
        OaReimbursement existing = requireExists(reimbId);
        checkOwner(existing, "?????????????????");
        if (!"0".equals(existing.getApprovalStatus()) && !"3".equals(existing.getApprovalStatus()))
        {
            throw new ServiceException("???????????");
        }
        OaReimbursement update = new OaReimbursement();
        update.setReimbId(reimbId);
        update.setApprovalStatus("0");
        update.setCurrentStep(1);
        update.setUpdateBy(SecurityUtils.getUsername());
        return oaReimbursementMapper.updateOaReimbursement(update);
    }

    @Override
    public int withdrawReimbursement(Long reimbId)
    {
        OaReimbursement existing = requireExists(reimbId);
        checkOwner(existing, "?????????????????");
        if (!"0".equals(existing.getApprovalStatus()))
        {
            throw new ServiceException("????????????????????");
        }
        OaReimbursement update = new OaReimbursement();
        update.setReimbId(reimbId);
        update.setApprovalStatus("3");
        update.setUpdateBy(SecurityUtils.getUsername());
        return oaReimbursementMapper.updateOaReimbursement(update);
    }

    @Override
    @Transactional
    public int approveReimbursement(Long reimbId, String approvalResult, String approvalRemark)
    {
        OaReimbursement existing = requireExists(reimbId);
        if (!"0".equals(existing.getApprovalStatus()))
        {
            throw new ServiceException("?????????????????????");
        }
        OaReimbursement update = new OaReimbursement();
        update.setReimbId(reimbId);
        update.setApprovalStatus(approvalResult);
        update.setUpdateBy(SecurityUtils.getUsername());
        update.setApproveBy(SecurityUtils.getUsername());
        update.setApproveTime(new Date());
        update.setApproveRemark(approvalRemark);
        if ("1".equals(approvalResult))
        {
            update.setPaymentStatus("1");
            update.setPaymentTime(new Date());
        }
        int result = oaReimbursementMapper.updateOaReimbursement(update);

        OaReimbursementApproveLog log = new OaReimbursementApproveLog();
        log.setReimbId(reimbId);
        log.setApproveResult(approvalResult);
        log.setApproveRemark(approvalRemark);
        log.setApproveBy(SecurityUtils.getUsername());
        log.setApproveTime(new Date());
        oaReimbursementApproveLogMapper.insertApproveLog(log);
        return result;
    }

    private OaReimbursement requireExists(Long reimbId)
    {
        OaReimbursement existing = oaReimbursementMapper.selectOaReimbursementByReimbId(reimbId);
        if (existing == null)
        {
            throw new ServiceException("????????????");
        }
        return existing;
    }

    private void checkOwner(OaReimbursement existing, String message)
    {
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId()))
        {
            return;
        }
        if (existing.getCreateBy() == null || !existing.getCreateBy().equals(SecurityUtils.getUsername()))
        {
            throw new ServiceException(message);
        }
    }
}
