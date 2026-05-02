package com.ruoyi.oa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 收文登记表 oa_incoming_doc
 *
 * @author ruoyi
 */
public class OaIncomingDoc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 收文ID */
    private Long docId;

    /** 收文编号 */
    private String docNum;

    /** 公文标题 */
    private String docTitle;

    /** 公文类型ID */
    private Long docTypeId;

    /** 公文类型名称 */
    private String docTypeName;

    /** 来文单位 */
    private String sourceUnit;

    /** 来文字号 */
    private String docCode;

    /** 来文日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date docDate;

    /** 紧急程度（1普通 2重要 3紧急） */
    private String docLevel;

    /** 页数 */
    private Integer pageCount;

    /** 附件URL */
    private String attachmentUrl;

    /** 密级（1普通 2秘密 3机密 4绝密） */
    private String secretLevel;

    /** 收文日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;

    /** 流程ID */
    private Long flowId;

    /** 当前步骤 */
    private Integer currentStep;

    /** 当前环节 */
    private String currentNode;

    /** 文档状态（0待签收 1待登记 2待审批 3审批中 4已办结 5已归档） */
    private String docStatus;

    public Long getDocId()
    {
        return docId;
    }

    public void setDocId(Long docId)
    {
        this.docId = docId;
    }

    @NotBlank(message = "收文编号不能为空")
    @Size(min = 0, max = 50, message = "收文编号不能超过50个字符")
    public String getDocNum()
    {
        return docNum;
    }

    public void setDocNum(String docNum)
    {
        this.docNum = docNum;
    }

    @NotBlank(message = "公文标题不能为空")
    @Size(min = 0, max = 200, message = "公文标题不能超过200个字符")
    public String getDocTitle()
    {
        return docTitle;
    }

    public void setDocTitle(String docTitle)
    {
        this.docTitle = docTitle;
    }

    public Long getDocTypeId()
    {
        return docTypeId;
    }

    public void setDocTypeId(Long docTypeId)
    {
        this.docTypeId = docTypeId;
    }

    public String getDocTypeName()
    {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName)
    {
        this.docTypeName = docTypeName;
    }

    @NotBlank(message = "来文单位不能为空")
    @Size(min = 0, max = 100, message = "来文单位不能超过100个字符")
    public String getSourceUnit()
    {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit)
    {
        this.sourceUnit = sourceUnit;
    }

    public String getDocCode()
    {
        return docCode;
    }

    public void setDocCode(String docCode)
    {
        this.docCode = docCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDocDate()
    {
        return docDate;
    }

    public void setDocDate(Date docDate)
    {
        this.docDate = docDate;
    }

    public String getDocLevel()
    {
        return docLevel;
    }

    public void setDocLevel(String docLevel)
    {
        this.docLevel = docLevel;
    }

    public Integer getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(Integer pageCount)
    {
        this.pageCount = pageCount;
    }

    public String getAttachmentUrl()
    {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    public String getSecretLevel()
    {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel)
    {
        this.secretLevel = secretLevel;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getReceiveDate()
    {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate)
    {
        this.receiveDate = receiveDate;
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

    public String getCurrentNode()
    {
        return currentNode;
    }

    public void setCurrentNode(String currentNode)
    {
        this.currentNode = currentNode;
    }

    public String getDocStatus()
    {
        return docStatus;
    }

    public void setDocStatus(String docStatus)
    {
        this.docStatus = docStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("docId", getDocId())
            .append("docNum", getDocNum())
            .append("docTitle", getDocTitle())
            .append("docTypeId", getDocTypeId())
            .append("docTypeName", getDocTypeName())
            .append("sourceUnit", getSourceUnit())
            .append("docCode", getDocCode())
            .append("docDate", getDocDate())
            .append("docLevel", getDocLevel())
            .append("pageCount", getPageCount())
            .append("attachmentUrl", getAttachmentUrl())
            .append("secretLevel", getSecretLevel())
            .append("receiveDate", getReceiveDate())
            .append("flowId", getFlowId())
            .append("currentStep", getCurrentStep())
            .append("currentNode", getCurrentNode())
            .append("docStatus", getDocStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
