package com.ruoyi.oa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 发文稿表 oa_outgoing_doc
 *
 * @author ruoyi
 */
public class OaOutgoingDoc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 发文ID */
    private Long docId;

    /** 发文编号 */
    private String docNum;

    /** 公文标题 */
    private String docTitle;

    /** 公文类型ID */
    private Long docTypeId;

    /** 公文类型名称 */
    private String docTypeName;

    /** 主送单位 */
    private String mainUnit;

    /** 抄送单位 */
    private String copyUnit;

    /** 发文字号 */
    private String docCode;

    /** 发文日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date docDate;

    /** 紧急程度（1普通 2重要 3紧急） */
    private String docLevel;

    /** 密级（1普通 2秘密 3机密 4绝密） */
    private String secretLevel;

    /** 附件URL */
    private String attachmentUrl;

    /** 公文内容 */
    private String docContent;

    /** 流程ID */
    private Long flowId;

    /** 当前步骤 */
    private Integer currentStep;

    /** 当前环节 */
    private String currentNode;

    /** 文档状态（0草稿 1待审核 2审核中 3已发布 4已归档） */
    private String docStatus;

    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    public Long getDocId()
    {
        return docId;
    }

    public void setDocId(Long docId)
    {
        this.docId = docId;
    }

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

    public String getMainUnit()
    {
        return mainUnit;
    }

    public void setMainUnit(String mainUnit)
    {
        this.mainUnit = mainUnit;
    }

    public String getCopyUnit()
    {
        return copyUnit;
    }

    public void setCopyUnit(String copyUnit)
    {
        this.copyUnit = copyUnit;
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

    public String getSecretLevel()
    {
        return secretLevel;
    }

    public void setSecretLevel(String secretLevel)
    {
        this.secretLevel = secretLevel;
    }

    public String getAttachmentUrl()
    {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    public String getDocContent()
    {
        return docContent;
    }

    public void setDocContent(String docContent)
    {
        this.docContent = docContent;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Date publishDate)
    {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("docId", getDocId())
            .append("docNum", getDocNum())
            .append("docTitle", getDocTitle())
            .append("docTypeId", getDocTypeId())
            .append("docTypeName", getDocTypeName())
            .append("mainUnit", getMainUnit())
            .append("copyUnit", getCopyUnit())
            .append("docCode", getDocCode())
            .append("docDate", getDocDate())
            .append("docLevel", getDocLevel())
            .append("secretLevel", getSecretLevel())
            .append("attachmentUrl", getAttachmentUrl())
            .append("docContent", getDocContent())
            .append("flowId", getFlowId())
            .append("currentStep", getCurrentStep())
            .append("currentNode", getCurrentNode())
            .append("docStatus", getDocStatus())
            .append("publishDate", getPublishDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
