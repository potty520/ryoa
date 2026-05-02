package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ????????? oa_reimbursement
 *
 * @author ruoyi
 */
public class OaReimbursement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ????ID */
    @Excel(name = "????ID")
    private Long reimbId;

    /** ???????? */
    @Excel(name = "????????", readConverterExp = "1=???��?,2=?????,3=??????,4=????,5=????")
    private String reimbType;

    /** ???????? */
    @Excel(name = "????????")
    private BigDecimal totalAmount;

    /** ???????? */
    @Excel(name = "????????")
    private String reimbReason;

    /** ??????? */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "???????", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /** ????ID */
    private Long flowId;

    /** ??????? */
    private Integer currentStep;

    /** ????????0?????? 1????? 2???? 3?????? */
    @Excel(name = "??????", readConverterExp = "0=??????,1=?????,2=????,3=?????")
    private String approvalStatus;

    /** ???????0????? 1??????? */
    @Excel(name = "?????", readConverterExp = "0=��???,1=?????")
    private String paymentStatus;

    /** ?????? */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "??????", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date paymentTime;

    @Excel(name = "??????")
    private String approveBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "???????", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    @Excel(name = "????????")
    private String approveRemark;

    private List<OaReimbursementDetail> details;

    private List<OaReimbursementApproveLog> approveLogs;

    public Long getReimbId()
    {
        return reimbId;
    }

    public void setReimbId(Long reimbId)
    {
        this.reimbId = reimbId;
    }

    @NotBlank(message = "??????????????")
    public String getReimbType()
    {
        return reimbType;
    }

    public void setReimbType(String reimbType)
    {
        this.reimbType = reimbType;
    }

    @NotNull(message = "?????????????")
    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    @NotBlank(message = "??????????????")
    @Size(min = 0, max = 500, message = "??????????????500?????")
    public String getReimbReason()
    {
        return reimbReason;
    }

    public void setReimbReason(String reimbReason)
    {
        this.reimbReason = reimbReason;
    }

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

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentTime()
    {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
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

    public String getApproveRemark()
    {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark)
    {
        this.approveRemark = approveRemark;
    }

    public List<OaReimbursementDetail> getDetails()
    {
        return details;
    }

    public void setDetails(List<OaReimbursementDetail> details)
    {
        this.details = details;
    }

    public List<OaReimbursementApproveLog> getApproveLogs()
    {
        return approveLogs;
    }

    public void setApproveLogs(List<OaReimbursementApproveLog> approveLogs)
    {
        this.approveLogs = approveLogs;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("reimbId", getReimbId())
            .append("reimbType", getReimbType())
            .append("totalAmount", getTotalAmount())
            .append("reimbReason", getReimbReason())
            .append("applyTime", getApplyTime())
            .append("flowId", getFlowId())
            .append("currentStep", getCurrentStep())
            .append("approvalStatus", getApprovalStatus())
            .append("paymentStatus", getPaymentStatus())
            .append("paymentTime", getPaymentTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
