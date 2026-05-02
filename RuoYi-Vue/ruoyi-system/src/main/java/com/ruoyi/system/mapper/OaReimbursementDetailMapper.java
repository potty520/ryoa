package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OaReimbursementDetail;

public interface OaReimbursementDetailMapper
{
    List<OaReimbursementDetail> selectDetailsByReimbId(Long reimbId);

    int deleteDetailsByReimbId(Long reimbId);

    int batchInsertDetails(List<OaReimbursementDetail> details);
}
