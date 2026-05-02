package com.ruoyi.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ??????????? oa_reimbursement_detail
 *
 * @author ruoyi
 */
public class OaReimbursementDetail extends BaseEntity
{
    private Long detailId;

    private Long reimbId;

    private String expenseType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expenseDate;

    private BigDecimal expenseAmount;

    private String expenseDesc;

    private String receiptUrl;

    public Long getDetailId()
    {
        return detailId;
    }

    public void setDetailId(Long detailId)
    {
        this.detailId = detailId;
    }

    public Long getReimbId()
    {
        return reimbId;
    }

    public void setReimbId(Long reimbId)
    {
        this.reimbId = reimbId;
    }

    public String getExpenseType()
    {
        return expenseType;
    }

    public void setExpenseType(String expenseType)
    {
        this.expenseType = expenseType;
    }

    public Date getExpenseDate()
    {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    public BigDecimal getExpenseAmount()
    {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDesc()
    {
        return expenseDesc;
    }

    public void setExpenseDesc(String expenseDesc)
    {
        this.expenseDesc = expenseDesc;
    }

    public String getReceiptUrl()
    {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl)
    {
        this.receiptUrl = receiptUrl;
    }
}
