package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OaReimbursement;

/**
 * ????????Service???
 *
 * @author ruoyi
 */
public interface IOaReimbursementService
{
    OaReimbursement selectOaReimbursementByReimbId(Long reimbId);

    List<OaReimbursement> selectOaReimbursementList(OaReimbursement oaReimbursement);

    List<OaReimbursement> selectOaReimbursementListByUserId(Long userId);

    int insertOaReimbursement(OaReimbursement oaReimbursement);

    int updateOaReimbursement(OaReimbursement oaReimbursement);

    int saveWithDetails(OaReimbursement oaReimbursement);

    int deleteOaReimbursementByReimbIds(Long[] reimbIds);

    int deleteOaReimbursementByReimbId(Long reimbId);

    int submitReimbursement(Long reimbId);

    int withdrawReimbursement(Long reimbId);

    int approveReimbursement(Long reimbId, String approvalResult, String approvalRemark);
}
