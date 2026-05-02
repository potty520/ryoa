package com.ruoyi.oa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 公文类型表 oa_doc_type
 *
 * @author ruoyi
 */
public class OaDocType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型ID */
    private Long typeId;

    /** 类型代码 */
    private String typeCode;

    /** 类型名称 */
    private String typeName;

    /** 公文类别（1上行文 2平行文 3下行文） */
    private String docCategory;

    /** 默认流程ID */
    private Long flowId;

    /** 是否启用（0否 1是） */
    private String isActive;

    /** 排序号 */
    private Integer sortNum;

    public Long getTypeId()
    {
        return typeId;
    }

    public void setTypeId(Long typeId)
    {
        this.typeId = typeId;
    }

    @NotBlank(message = "类型代码不能为空")
    @Size(min = 0, max = 50, message = "类型代码不能超过50个字符")
    public String getTypeCode()
    {
        return typeCode;
    }

    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    @NotBlank(message = "类型名称不能为空")
    @Size(min = 0, max = 100, message = "类型名称不能超过100个字符")
    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getDocCategory()
    {
        return docCategory;
    }

    public void setDocCategory(String docCategory)
    {
        this.docCategory = docCategory;
    }

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Long flowId)
    {
        this.flowId = flowId;
    }

    public String getIsActive()
    {
        return isActive;
    }

    public void setIsActive(String isActive)
    {
        this.isActive = isActive;
    }

    public Integer getSortNum()
    {
        return sortNum;
    }

    public void setSortNum(Integer sortNum)
    {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeCode", getTypeCode())
            .append("typeName", getTypeName())
            .append("docCategory", getDocCategory())
            .append("flowId", getFlowId())
            .append("isActive", getIsActive())
            .append("sortNum", getSortNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
