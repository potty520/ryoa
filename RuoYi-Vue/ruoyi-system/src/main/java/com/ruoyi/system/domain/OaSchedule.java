package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 日程安排表 oa_schedule
 *
 * @author ruoyi
 */
public class OaSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日程ID */
    private Long scheduleId;

    /** 日程标题 */
    private String scheduleTitle;

    /** 日程类型（1日程 2会议 3约会） */
    private String scheduleType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 日程内容 */
    private String scheduleContent;

    /** 地点 */
    private String location;

    /** 是否提醒（0否 1是） */
    private String remindFlag;

    /** 提前提醒分钟数 */
    private Integer remindTime;

    /** 状态（0正常 1已取消） */
    private String status;

    /** 用户ID */
    private Long userId;

    public Long getScheduleId()
    {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId)
    {
        this.scheduleId = scheduleId;
    }

    @NotBlank(message = "日程标题不能为空")
    @Size(min = 0, max = 100, message = "日程标题不能超过100个字符")
    public String getScheduleTitle()
    {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle)
    {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleType()
    {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType)
    {
        this.scheduleType = scheduleType;
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

    public String getScheduleContent()
    {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent)
    {
        this.scheduleContent = scheduleContent;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getRemindFlag()
    {
        return remindFlag;
    }

    public void setRemindFlag(String remindFlag)
    {
        this.remindFlag = remindFlag;
    }

    public Integer getRemindTime()
    {
        return remindTime;
    }

    public void setRemindTime(Integer remindTime)
    {
        this.remindTime = remindTime;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("scheduleId", getScheduleId())
            .append("scheduleTitle", getScheduleTitle())
            .append("scheduleType", getScheduleType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("scheduleContent", getScheduleContent())
            .append("location", getLocation())
            .append("remindFlag", getRemindFlag())
            .append("remindTime", getRemindTime())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
