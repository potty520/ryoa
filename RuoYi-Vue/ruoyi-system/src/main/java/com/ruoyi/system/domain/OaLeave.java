package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 请假申请表 oa_leave
 *
 * @author ruoyi
 */
public class OaLeave extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 请假ID */
    private Long leaveId;

    /** 请假类型（1年假 2事假 3病假 4婚假 5产假 6丧假） */
    private String leaveType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 请假天数 */
    private BigDecimal leaveDays;

    /** 请假原因 */
    private String leaveReason;

    /** 申请时间 */
    private Date applyTime;

    /** 流程ID */
    private Long flowId;

    /** 当前步骤 */
    private Integer currentStep;

    /** 审批状态（0审批中 1已通过 2已拒绝 3已撤回） */
    private String approvalStatus;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    public Long getLeaveId()
    {
        return leaveId;
    }

    public void setLeaveId(Long leaveId)
    {
        this.leaveId = leaveId;
    }

    @NotBlank(message = "请假类型不能为空")
    public String getLeaveType()
    {
        return leaveType;
    }

    public void setLeaveType(String leaveType)
    {
        this.leaveType = leaveType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @NotBlank(message = "请假天数不能为空")
    public BigDecimal getLeaveDays()
    {
        return leaveDays;
    }

    public void setLeaveDays(BigDecimal leaveDays)
    {
        this.leaveDays = leaveDays;
    }

    @NotBlank(message = "请假原因不能为空")
    @Size(min = 0, max = 500, message = "请假原因不能超过500个字符")
    public String getLeaveReason()
    {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason)
    {
        this.leaveReason = leaveReason;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getApplyTime()
    {
        return applyTime;
    }

    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Long flowId)
    {
        this.flowId = flowId;
    }

    public Integer getCurrentStep()
    {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep)
    {
        this.currentStep = currentStep;
    }

    public String getApprovalStatus()
    {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus)
    {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String getCreateBy()
    {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime()
    {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("leaveId", getLeaveId())
            .append("leaveType", getLeaveType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("leaveDays", getLeaveDays())
            .append("leaveReason", getLeaveReason())
            .append("applyTime", getApplyTime())
            .append("flowId", getFlowId())
            .append("currentStep", getCurrentStep())
            .append("approvalStatus", getApprovalStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
