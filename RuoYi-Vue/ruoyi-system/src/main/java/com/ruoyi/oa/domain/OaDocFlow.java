package com.ruoyi.oa.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 公文流程配置表 oa_doc_flow
 *
 * @author ruoyi
 */
public class OaDocFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程ID */
    private Long flowId;

    /** 流程名称 */
    private String flowName;

    /** 流程类别（1收文流程 2发文流程） */
    private String flowCategory;

    /** 是否默认（0否 1是） */
    private String isDefault;

    /** 是否启用（0否 1是） */
    private String isActive;

    /** 流程步骤列表 */
    private List<OaDocFlowStep> steps;

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Long flowId)
    {
        this.flowId = flowId;
    }

    @NotBlank(message = "流程名称不能为空")
    @Size(min = 0, max = 100, message = "流程名称不能超过100个字符")
    public String getFlowName()
    {
        return flowName;
    }

    public void setFlowName(String flowName)
    {
        this.flowName = flowName;
    }

    public String getFlowCategory()
    {
        return flowCategory;
    }

    public void setFlowCategory(String flowCategory)
    {
        this.flowCategory = flowCategory;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public String getIsActive()
    {
        return isActive;
    }

    public void setIsActive(String isActive)
    {
        this.isActive = isActive;
    }

    public List<OaDocFlowStep> getSteps()
    {
        return steps;
    }

    public void setSteps(List<OaDocFlowStep> steps)
    {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("flowId", getFlowId())
            .append("flowName", getFlowName())
            .append("flowCategory", getFlowCategory())
            .append("isDefault", getIsDefault())
            .append("isActive", getIsActive())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
