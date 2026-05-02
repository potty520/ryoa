package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OaScheduleMapper;
import com.ruoyi.system.domain.OaSchedule;
import com.ruoyi.system.service.IOaScheduleService;

import com.ruoyi.common.utils.SecurityUtils;

/**
 * 日程安排Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaScheduleServiceImpl implements IOaScheduleService
{
    @Autowired
    private OaScheduleMapper oaScheduleMapper;

    /**
     * 查询日程信息
     *
     * @param scheduleId 日程ID
     * @return 日程信息
     */
    @Override
    public OaSchedule selectOaScheduleById(Long scheduleId)
    {
        return oaScheduleMapper.selectOaScheduleById(scheduleId);
    }

    /**
     * 查询日程列表
     *
     * @param oaSchedule 日程信息
     * @return 日程集合
     */
    @Override
    public List<OaSchedule> selectOaScheduleList(OaSchedule oaSchedule)
    {
        return oaScheduleMapper.selectOaScheduleList(oaSchedule);
    }

    /**
     * 查询用户的日程列表
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日程集合
     */
    @Override
    public List<OaSchedule> selectUserScheduleList(Long userId, String startTime, String endTime)
    {
        return oaScheduleMapper.selectUserScheduleList(userId, startTime, endTime);
    }

    /**
     * 新增日程
     *
     * @param oaSchedule 日程信息
     * @return 结果
     */
    @Override
    public int insertOaSchedule(OaSchedule oaSchedule)
    {
        if (oaSchedule.getCreateBy() == null || oaSchedule.getCreateBy().isEmpty())
        {
            oaSchedule.setCreateBy(SecurityUtils.getUsername());
        }
        if (oaSchedule.getUserId() == null)
        {
            oaSchedule.setUserId(SecurityUtils.getUserId());
        }
        return oaScheduleMapper.insertOaSchedule(oaSchedule);
    }

    /**
     * 修改日程
     *
     * @param oaSchedule 日程信息
     * @return 结果
     */
    @Override
    public int updateOaSchedule(OaSchedule oaSchedule)
    {
        if (oaSchedule.getUpdateBy() == null || oaSchedule.getUpdateBy().isEmpty())
        {
            oaSchedule.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaScheduleMapper.updateOaSchedule(oaSchedule);
    }

    /**
     * 删除日程信息
     *
     * @param scheduleId 日程ID
     * @return 结果
     */
    @Override
    public int deleteOaScheduleById(Long scheduleId)
    {
        return oaScheduleMapper.deleteOaScheduleById(scheduleId);
    }

    /**
     * 批量删除日程信息
     *
     * @param scheduleIds 需要删除的日程ID
     * @return 结果
     */
    @Override
    public int deleteOaScheduleByIds(Long[] scheduleIds)
    {
        return oaScheduleMapper.deleteOaScheduleByIds(scheduleIds);
    }
}
