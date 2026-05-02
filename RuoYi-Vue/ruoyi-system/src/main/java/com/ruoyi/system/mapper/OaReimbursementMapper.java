package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.OaReimbursement;

/**
 * ��������Mapper�ӿ�
 *
 * @author ruoyi
 */
public interface OaReimbursementMapper
{
    OaReimbursement selectOaReimbursementByReimbId(Long reimbId);

    List<OaReimbursement> selectOaReimbursementList(OaReimbursement oaReimbursement);

    List<OaReimbursement> selectOaReimbursementListByUserId(@Param("userId") Long userId);

    int insertOaReimbursement(OaReimbursement oaReimbursement);

    int updateOaReimbursement(OaReimbursement oaReimbursement);

    int deleteOaReimbursementByReimbId(Long reimbId);

    int deleteOaReimbursementByReimbIds(Long[] reimbIds);
}
