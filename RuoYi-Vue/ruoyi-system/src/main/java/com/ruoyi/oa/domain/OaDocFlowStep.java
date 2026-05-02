package com.ruoyi.oa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公文流程步骤表 oa_doc_flow_step
 *
 * @author ruoyi
 */
public class OaDocFlowStep extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 步骤ID */
    private Long stepId;

    /** 流程ID */
    private Long flowId;

    /** 步骤序号 */
    private Integer stepOrder;

    /** 步骤名称 */
    private String stepName;

    /** 步骤类型（1起草 2审核 3审批 4签发 5核稿 6分发 7归档） */
    private String stepType;

    /** 审批人类型（1指定人 2部门主管 3角色 4会签） */
    private String approverType;

    /** 审批人ID */
    private Long approverId;

    /** 审批人名称 */
    private String approverName;

    /** 办理时限（天） */
    private Integer timeLimit;

    /** 是否必须（0否 1是） */
    private String isRequired;

    /** 能否退回（0否 1是） */
    private String canBack;

    /** 可退回步骤ID */
    private Long backStepId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getStepId()
    {
        return stepId;
    }

    public void setStepId(Long stepId)
    {
        this.stepId = stepId;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Long flowId)
    {
        this.flowId = flowId;
    }

    public Integer getStepOrder()
    {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder)
    {
        this.stepOrder = stepOrder;
    }

    public String getStepName()
    {
        return stepName;
    }

    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    public String getStepType()
    {
        return stepType;
    }

    public void setStepType(String stepType)
    {
        this.stepType = stepType;
    }

    public String getApproverType()
    {
        return approverType;
    }

    public void setApproverType(String approverType)
    {
        this.approverType = approverType;
    }

    public Long getApproverId()
    {
        return approverId;
    }

    public void setApproverId(Long approverId)
    {
        this.approverId = approverId;
    }

    public String getApproverName()
    {
        return approverName;
    }

    public void setApproverName(String approverName)
    {
        this.approverName = approverName;
    }

    public Integer getTimeLimit()
    {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit)
    {
        this.timeLimit = timeLimit;
    }

    public String getIsRequired()
    {
        return isRequired;
    }

    public void setIsRequired(String isRequired)
    {
        this.isRequired = isRequired;
    }

    public String getCanBack()
    {
        return canBack;
    }

    public void setCanBack(String canBack)
    {
        this.canBack = canBack;
    }

    public Long getBackStepId()
    {
        return backStepId;
    }

    public void setBackStepId(Long backStepId)
    {
        this.backStepId = backStepId;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("stepId", getStepId())
            .append("flowId", getFlowId())
            .append("stepOrder", getStepOrder())
            .append("stepName", getStepName())
            .append("stepType", getStepType())
            .append("approverType", getApproverType())
            .append("approverId", getApproverId())
            .append("approverName", getApproverName())
            .append("timeLimit", getTimeLimit())
            .append("isRequired", getIsRequired())
            .append("canBack", getCanBack())
            .append("backStepId", getBackStepId())
            .toString();
    }
}
