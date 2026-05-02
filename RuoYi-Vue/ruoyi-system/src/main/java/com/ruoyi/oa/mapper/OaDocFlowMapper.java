package com.ruoyi.oa.mapper;

import java.util.List;
import com.ruoyi.oa.domain.OaDocFlow;

/**
 * 公文流程配置Mapper接口
 *
 * @author ruoyi
 */
public interface OaDocFlowMapper
{
    /**
     * 查询公文流程信息
     *
     * @param flowId 流程ID
     * @return 公文流程信息
     */
    public OaDocFlow selectOaDocFlowById(Long flowId);

    /**
     * 查询公文流程列表
     *
     * @param oaDocFlow 公文流程信息
     * @return 公文流程集合
     */
    public List<OaDocFlow> selectOaDocFlowList(OaDocFlow oaDocFlow);

    /**
     * 新增公文流程
     *
     * @param oaDocFlow 公文流程信息
     * @return 结果
     */
    public int insertOaDocFlow(OaDocFlow oaDocFlow);

    /**
     * 修改公文流程
     *
     * @param oaDocFlow 公文流程信息
     * @return 结果
     */
    public int updateOaDocFlow(OaDocFlow oaDocFlow);

    /**
     * 删除公文流程信息
     *
     * @param flowId 流程ID
     * @return 结果
     */
    public int deleteOaDocFlowById(Long flowId);

    /**
     * 批量删除公文流程信息
     *
     * @param flowIds 需要删除的流程ID
     * @return 结果
     */
    public int deleteOaDocFlowByIds(Long[] flowIds);

    /**
     * 获取默认流程
     *
     * @param flowCategory 流程类别
     * @return 默认流程
     */
    public OaDocFlow selectDefaultFlow(String flowCategory);

    /**
     * 统计同类别默认流程数量（排除指定流程）
     *
     * @param flowCategory 流程类别
     * @param excludeFlowId 排除的流程ID
     * @return 数量
     */
    public int countDefaultFlowByCategory(String flowCategory, Long excludeFlowId);
}
