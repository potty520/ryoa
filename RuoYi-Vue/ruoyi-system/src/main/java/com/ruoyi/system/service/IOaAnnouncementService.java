package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OaAnnouncement;

/**
 * OA公告Service接口
 *
 * @author ruoyi
 */
public interface IOaAnnouncementService
{
    /**
     * 查询OA公告信息
     *
     * @param annId 公告ID
     * @return OA公告信息
     */
    public OaAnnouncement selectOaAnnouncementById(Long annId);

    /**
     * 查询OA公告列表
     *
     * @param oaAnnouncement 公告信息
     * @return 公告集合
     */
    public List<OaAnnouncement> selectOaAnnouncementList(OaAnnouncement oaAnnouncement);

    /**
     * 查询已发布的公告列表（用于前端展示）
     *
     * @param oaAnnouncement 公告信息
     * @return 公告集合
     */
    public List<OaAnnouncement> selectPublishedAnnouncementList(OaAnnouncement oaAnnouncement);

    /**
     * 新增OA公告
     *
     * @param oaAnnouncement 公告信息
     * @return 结果
     */
    public int insertOaAnnouncement(OaAnnouncement oaAnnouncement);

    /**
     * 修改OA公告
     *
     * @param oaAnnouncement 公告信息
     * @return 结果
     */
    public int updateOaAnnouncement(OaAnnouncement oaAnnouncement);

    /**
     * 删除OA公告信息
     *
     * @param annId 公告ID
     * @return 结果
     */
    public int deleteOaAnnouncementById(Long annId);

    /**
     * 批量删除OA公告信息
     *
     * @param annIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteOaAnnouncementByIds(Long[] annIds);

    /**
     * 增加阅读次数
     *
     * @param annId 公告ID
     * @return 结果
     */
    public int incrementViewCount(Long annId);

    /**
     * 查询用户未读公告数量
     *
     * @param userId 用户ID
     * @return 未读数量
     */
    public int selectUnreadCount(Long userId);

    /**
     * 标记公告为已读
     *
     * @param annId 公告ID
     * @param userId 用户ID
     * @return 结果
     */
    public int markAsRead(Long annId, Long userId);
}
