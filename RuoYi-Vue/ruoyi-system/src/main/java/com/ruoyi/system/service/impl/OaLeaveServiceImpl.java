package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.mapper.OaLeaveMapper;
import com.ruoyi.system.domain.OaLeave;
import com.ruoyi.system.service.IOaLeaveService;

import com.ruoyi.common.utils.SecurityUtils;

/**
 * 请假申请Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaLeaveServiceImpl implements IOaLeaveService
{
    @Autowired
    private OaLeaveMapper oaLeaveMapper;

    /**
     * 查询请假申请信息
     *
     * @param leaveId 请假ID
     * @return 请假申请信息
     */
    @Override
    public OaLeave selectOaLeaveById(Long leaveId)
    {
        return oaLeaveMapper.selectOaLeaveById(leaveId);
    }

    /**
     * 查询请假申请列表
     *
     * @param oaLeave 请假申请信息
     * @return 请假申请集合
     */
    @Override
    public List<OaLeave> selectOaLeaveList(OaLeave oaLeave)
    {
        return oaLeaveMapper.selectOaLeaveList(oaLeave);
    }

    /**
     * 查询用户的请假申请列表
     *
     * @param userId 用户ID
     * @return 请假申请集合
     */
    @Override
    public List<OaLeave> selectOaLeaveListByUserId(Long userId)
    {
        return oaLeaveMapper.selectOaLeaveListByUserId(userId);
    }

    /**
     * 新增请假申请
     *
     * @param oaLeave 请假申请信息
     * @return 结果
     */
    @Override
    public int insertOaLeave(OaLeave oaLeave)
    {
        if (oaLeave.getCreateBy() == null || oaLeave.getCreateBy().isEmpty())
        {
            oaLeave.setCreateBy(SecurityUtils.getUsername());
        }
        return oaLeaveMapper.insertOaLeave(oaLeave);
    }

    /**
     * 修改请假申请
     *
     * @param oaLeave 请假申请信息
     * @return 结果
     */
    @Override
    public int updateOaLeave(OaLeave oaLeave)
    {
        checkOwner(oaLeave.getLeaveId());
        if (oaLeave.getUpdateBy() == null || oaLeave.getUpdateBy().isEmpty())
        {
            oaLeave.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaLeaveMapper.updateOaLeave(oaLeave);
    }

    /**
     * 删除请假申请信息
     *
     * @param leaveId 请假ID
     * @return 结果
     */
    @Override
    public int deleteOaLeaveById(Long leaveId)
    {
        checkOwner(leaveId);
        return oaLeaveMapper.deleteOaLeaveById(leaveId);
    }

    /**
     * 批量删除请假申请信息
     *
     * @param leaveIds 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteOaLeaveByIds(Long[] leaveIds)
    {
        for (Long leaveId : leaveIds)
        {
            checkOwner(leaveId);
        }
        return oaLeaveMapper.deleteOaLeaveByIds(leaveIds);
    }

    /**
     * 更新审批状态
     *
     * @param leaveId 请假ID
     * @param status 审批状态
     * @return 结果
     */
    @Override
    public int updateApprovalStatus(Long leaveId, String status)
    {
        return oaLeaveMapper.updateApprovalStatus(leaveId, status);
    }

    @Override
    public int submitLeave(Long leaveId)
    {
        checkOwner(leaveId);
        return oaLeaveMapper.updateApprovalStatus(leaveId, "0");
    }

    @Override
    public int withdrawLeave(Long leaveId)
    {
        checkOwner(leaveId);
        OaLeave leave = oaLeaveMapper.selectOaLeaveById(leaveId);
        if (leave == null)
        {
            throw new ServiceException("请假记录不存在");
        }
        if (!"0".equals(leave.getApprovalStatus()))
        {
            throw new ServiceException("仅审批中请假可撤回");
        }
        return oaLeaveMapper.updateApprovalStatus(leaveId, "3");
    }

    @Override
    public int approveLeave(Long leaveId, String approvalResult)
    {
        OaLeave leave = oaLeaveMapper.selectOaLeaveById(leaveId);
        if (leave == null)
        {
            throw new ServiceException("请假记录不存在");
        }
        if (!"0".equals(leave.getApprovalStatus()))
        {
            throw new ServiceException("仅审批中请假可审批");
        }
        return oaLeaveMapper.updateApprovalStatus(leaveId, approvalResult);
    }

    private void checkOwner(Long leaveId)
    {
        if (leaveId == null || SecurityUtils.isAdmin())
        {
            return;
        }
        OaLeave leave = oaLeaveMapper.selectOaLeaveById(leaveId);
        if (leave == null)
        {
            throw new ServiceException("请假记录不存在");
        }
        if (!SecurityUtils.getUsername().equals(leave.getCreateBy()))
        {
            throw new ServiceException("仅创建人可操作该请假记录");
        }
    }
}
