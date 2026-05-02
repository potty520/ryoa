import request from '@/utils/request'

// 查询OA公告列表
export function listAnnouncement(query) {
  return request({
    url: '/system/announcement/list',
    method: 'get',
    params: query
  })
}

// 查询已发布的公告列表
export function listPublishedAnnouncement(query) {
  return request({
    url: '/system/announcement/published/list',
    method: 'get',
    params: query
  })
}

// 查询公告详细
export function getAnnouncement(annId) {
  return request({
    url: '/system/announcement/' + annId,
    method: 'get'
  })
}

// 新增公告
export function addAnnouncement(data) {
  return request({
    url: '/system/announcement',
    method: 'post',
    data: data
  })
}

// 修改公告
export function updateAnnouncement(data) {
  return request({
    url: '/system/announcement',
    method: 'put',
    data: data
  })
}

// 删除公告
export function delAnnouncement(annId) {
  return request({
    url: '/system/announcement/' + annId,
    method: 'delete'
  })
}

// 获取未读公告数量
export function getUnreadCount() {
  return request({
    url: '/system/announcement/unread/count',
    method: 'get'
  })
}
