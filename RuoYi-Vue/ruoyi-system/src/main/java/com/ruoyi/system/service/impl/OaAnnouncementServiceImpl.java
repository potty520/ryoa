package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OaAnnouncementMapper;
import com.ruoyi.system.domain.OaAnnouncement;
import com.ruoyi.system.service.IOaAnnouncementService;

import com.ruoyi.common.utils.SecurityUtils;

/**
 * OA公告Service业务层处理
 *
 * @author ruoyi
 */
@Service
public class OaAnnouncementServiceImpl implements IOaAnnouncementService
{
    @Autowired
    private OaAnnouncementMapper oaAnnouncementMapper;

    /**
     * 查询OA公告信息
     *
     * @param annId 公告ID
     * @return OA公告信息
     */
    @Override
    public OaAnnouncement selectOaAnnouncementById(Long annId)
    {
        return oaAnnouncementMapper.selectOaAnnouncementById(annId);
    }

    /**
     * 查询OA公告列表
     *
     * @param oaAnnouncement 公告信息
     * @return 公告集合
     */
    @Override
    public List<OaAnnouncement> selectOaAnnouncementList(OaAnnouncement oaAnnouncement)
    {
        return oaAnnouncementMapper.selectOaAnnouncementList(oaAnnouncement);
    }

    /**
     * 查询已发布的公告列表
     *
     * @param oaAnnouncement 公告信息
     * @return 公告集合
     */
    @Override
    public List<OaAnnouncement> selectPublishedAnnouncementList(OaAnnouncement oaAnnouncement)
    {
        return oaAnnouncementMapper.selectPublishedAnnouncementList(oaAnnouncement);
    }

    /**
     * 新增OA公告
     *
     * @param oaAnnouncement 公告信息
     * @return 结果
     */
    @Override
    public int insertOaAnnouncement(OaAnnouncement oaAnnouncement)
    {
        if (oaAnnouncement.getCreateBy() == null || oaAnnouncement.getCreateBy().isEmpty())
        {
            oaAnnouncement.setCreateBy(SecurityUtils.getUsername());
        }
        return oaAnnouncementMapper.insertOaAnnouncement(oaAnnouncement);
    }

    /**
     * 修改OA公告
     *
     * @param oaAnnouncement 公告信息
     * @return 结果
     */
    @Override
    public int updateOaAnnouncement(OaAnnouncement oaAnnouncement)
    {
        if (oaAnnouncement.getUpdateBy() == null || oaAnnouncement.getUpdateBy().isEmpty())
        {
            oaAnnouncement.setUpdateBy(SecurityUtils.getUsername());
        }
        return oaAnnouncementMapper.updateOaAnnouncement(oaAnnouncement);
    }

    /**
     * 删除OA公告信息
     *
     * @param annId 公告ID
     * @return 结果
     */
    @Override
    public int deleteOaAnnouncementById(Long annId)
    {
        return oaAnnouncementMapper.deleteOaAnnouncementById(annId);
    }

    /**
     * 批量删除OA公告信息
     *
     * @param annIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteOaAnnouncementByIds(Long[] annIds)
    {
        return oaAnnouncementMapper.deleteOaAnnouncementByIds(annIds);
    }

    /**
     * 增加阅读次数
     *
     * @param annId 公告ID
     * @return 结果
     */
    @Override
    public int incrementViewCount(Long annId)
    {
        return oaAnnouncementMapper.incrementViewCount(annId);
    }

    /**
     * 查询用户未读公告数量
     *
     * @param userId 用户ID
     * @return 未读数量
     */
    @Override
    public int selectUnreadCount(Long userId)
    {
        return oaAnnouncementMapper.selectUnreadCount(userId);
    }

    /**
     * 标记公告为已读
     *
     * @param annId 公告ID
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int markAsRead(Long annId, Long userId)
    {
        return 0;
    }
}
