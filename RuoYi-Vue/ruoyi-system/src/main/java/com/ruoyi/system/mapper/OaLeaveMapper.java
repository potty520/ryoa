package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OaLeave;

/**
 * 请假申请Mapper接口
 *
 * @author ruoyi
 */
public interface OaLeaveMapper
{
    /**
     * 查询请假申请信息
     *
     * @param leaveId 请假ID
     * @return 请假申请信息
     */
    public OaLeave selectOaLeaveById(Long leaveId);

    /**
     * 查询请假申请列表
     *
     * @param oaLeave 请假申请信息
     * @return 请假申请集合
     */
    public List<OaLeave> selectOaLeaveList(OaLeave oaLeave);

    /**
     * 查询用户的请假申请列表
     *
     * @param userId 用户ID
     * @return 请假申请集合
     */
    public List<OaLeave> selectOaLeaveListByUserId(Long userId);

    /**
     * 新增请假申请
     *
     * @param oaLeave 请假申请信息
     * @return 结果
     */
    public int insertOaLeave(OaLeave oaLeave);

    /**
     * 修改请假申请
     *
     * @param oaLeave 请假申请信息
     * @return 结果
     */
    public int updateOaLeave(OaLeave oaLeave);

    /**
     * 删除请假申请信息
     *
     * @param leaveId 请假ID
     * @return 结果
     */
    public int deleteOaLeaveById(Long leaveId);

    /**
     * 批量删除请假申请信息
     *
     * @param leaveIds 需要删除的请假ID
     * @return 结果
     */
    public int deleteOaLeaveByIds(Long[] leaveIds);

    /**
     * 更新审批状态
     *
     * @param leaveId 请假ID
     * @param status 审批状态
     * @return 结果
     */
    public int updateApprovalStatus(Long leaveId, String status);
}
