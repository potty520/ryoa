package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OaReimbursementApproveLog;

public interface OaReimbursementApproveLogMapper
{
    int insertApproveLog(OaReimbursementApproveLog log);

    List<OaReimbursementApproveLog> selectLogsByReimbId(Long reimbId);
}
