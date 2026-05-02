package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * OA公告表 oa_announcement
 *
 * @author ruoyi
 */
public class OaAnnouncement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long annId;

    /** 公告标题 */
    private String annTitle;

    /** 公告类型（1公司公告 2部门公告 3系统公告） */
    private String annType;

    /** 公告内容 */
    private String annContent;

    /** 重要程度（1普通 2重要 3紧急） */
    private String annLevel;

    /** 附件URL */
    private String attachmentUrl;

    /** 发布时间 */
    private Date publishStart;

    /** 结束时间 */
    private Date publishEnd;

    /** 是否置顶（0否 1是） */
    private String isTop;

    /** 是否公开（0否 1是） */
    private String isPublic;

    /** 阅读次数 */
    private Integer viewCount;

    /** 状态（0草稿 1已发布 2已下架） */
    private String status;

    /** 关键词搜索 */
    private String searchValue;

    public Long getAnnId()
    {
        return annId;
    }

    public void setAnnId(Long annId)
    {
        this.annId = annId;
    }

    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 200, message = "公告标题不能超过200个字符")
    public String getAnnTitle()
    {
        return annTitle;
    }

    public void setAnnTitle(String annTitle)
    {
        this.annTitle = annTitle;
    }

    public String getAnnType()
    {
        return annType;
    }

    public void setAnnType(String annType)
    {
        this.annType = annType;
    }

    public String getAnnContent()
    {
        return annContent;
    }

    public void setAnnContent(String annContent)
    {
        this.annContent = annContent;
    }

    public String getAnnLevel()
    {
        return annLevel;
    }

    public void setAnnLevel(String annLevel)
    {
        this.annLevel = annLevel;
    }

    public String getAttachmentUrl()
    {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl)
    {
        this.attachmentUrl = attachmentUrl;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPublishStart()
    {
        return publishStart;
    }

    public void setPublishStart(Date publishStart)
    {
        this.publishStart = publishStart;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getPublishEnd()
    {
        return publishEnd;
    }

    public void setPublishEnd(Date publishEnd)
    {
        this.publishEnd = publishEnd;
    }

    public String getIsTop()
    {
        return isTop;
    }

    public void setIsTop(String isTop)
    {
        this.isTop = isTop;
    }

    public String getIsPublic()
    {
        return isPublic;
    }

    public void setIsPublic(String isPublic)
    {
        this.isPublic = isPublic;
    }

    public Integer getViewCount()
    {
        return viewCount;
    }

    public void setViewCount(Integer viewCount)
    {
        this.viewCount = viewCount;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("annId", getAnnId())
            .append("annTitle", getAnnTitle())
            .append("annType", getAnnType())
            .append("annContent", getAnnContent())
            .append("annLevel", getAnnLevel())
            .append("attachmentUrl", getAttachmentUrl())
            .append("publishStart", getPublishStart())
            .append("publishEnd", getPublishEnd())
            .append("isTop", getIsTop())
            .append("isPublic", getIsPublic())
            .append("viewCount", getViewCount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
