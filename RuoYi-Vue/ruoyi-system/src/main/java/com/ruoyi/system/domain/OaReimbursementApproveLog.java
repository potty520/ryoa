package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OaReimbursementApproveLog
{
    private Long logId;

    private Long reimbId;

    private String approveResult;

    private String approveRemark;

    private String approveBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getReimbId()
    {
        return reimbId;
    }

    public void setReimbId(Long reimbId)
    {
        this.reimbId = reimbId;
    }

    public String getApproveResult()
    {
        return approveResult;
    }

    public void setApproveResult(String approveResult)
    {
        this.approveResult = approveResult;
    }

    public String getApproveRemark()
    {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark)
    {
        this.approveRemark = approveRemark;
    }

    public String getApproveBy()
    {
        return approveBy;
    }

    public void setApproveBy(String approveBy)
    {
        this.approveBy = approveBy;
    }

    public Date getApproveTime()
    {
        return approveTime;
    }

    public void setApproveTime(Date approveTime)
    {
        this.approveTime = approveTime;
    }
}
