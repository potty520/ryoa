import request from "@/utils/request"

export function listSchedule(query) {
  return request({
    url: "/system/schedule/list",
    method: "get",
    params: query
  })
}

export function getSchedule(scheduleId) {
  return request({
    url: "/system/schedule/" + scheduleId,
    method: "get"
  })
}

export function addSchedule(data) {
  return request({
    url: "/system/schedule",
    method: "post",
    data: data
  })
}

export function updateSchedule(data) {
  return request({
    url: "/system/schedule",
    method: "put",
    data: data
  })
}

export function delSchedule(scheduleId) {
  return request({
    url: "/system/schedule/" + scheduleId,
    method: "delete"
  })
}
