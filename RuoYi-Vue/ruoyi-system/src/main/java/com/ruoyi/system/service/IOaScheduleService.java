package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OaSchedule;

/**
 * 日程安排Service接口
 *
 * @author ruoyi
 */
public interface IOaScheduleService
{
    /**
     * 查询日程信息
     *
     * @param scheduleId 日程ID
     * @return 日程信息
     */
    public OaSchedule selectOaScheduleById(Long scheduleId);

    /**
     * 查询日程列表
     *
     * @param oaSchedule 日程信息
     * @return 日程集合
     */
    public List<OaSchedule> selectOaScheduleList(OaSchedule oaSchedule);

    /**
     * 查询用户的日程列表
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 日程集合
     */
    public List<OaSchedule> selectUserScheduleList(Long userId, String startTime, String endTime);

    /**
     * 新增日程
     *
     * @param oaSchedule 日程信息
     * @return 结果
     */
    public int insertOaSchedule(OaSchedule oaSchedule);

    /**
     * 修改日程
     *
     * @param oaSchedule 日程信息
     * @return 结果
     */
    public int updateOaSchedule(OaSchedule oaSchedule);

    /**
     * 删除日程信息
     *
     * @param scheduleId 日程ID
     * @return 结果
     */
    public int deleteOaScheduleById(Long scheduleId);

    /**
     * 批量删除日程信息
     *
     * @param scheduleIds 需要删除的日程ID
     * @return 结果
     */
    public int deleteOaScheduleByIds(Long[] scheduleIds);
}
