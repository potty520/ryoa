import request from "@/utils/request"

export function listLeave(query) {
  return request({
    url: "/system/leave/list",
    method: "get",
    params: query
  })
}

export function listMyLeave(query) {
  return request({
    url: "/system/leave/my/list",
    method: "get",
    params: query
  })
}

export function getLeave(leaveId) {
  return request({
    url: "/system/leave/" + leaveId,
    method: "get"
  })
}

export function addLeave(data) {
  return request({
    url: "/system/leave",
    method: "post",
    data: data
  })
}

export function updateLeave(data) {
  return request({
    url: "/system/leave",
    method: "put",
    data: data
  })
}

export function delLeave(leaveId) {
  return request({
    url: "/system/leave/" + leaveId,
    method: "delete"
  })
}

export function submitLeave(leaveId) {
  return request({
    url: "/system/leave/submit/" + leaveId,
    method: "put"
  })
}

export function withdrawLeave(leaveId) {
  return request({
    url: "/system/leave/withdraw/" + leaveId,
    method: "put"
  })
}

export function approveLeave(leaveId, approvalResult) {
  return request({
    url: "/system/leave/approve/" + leaveId,
    method: "put",
    params: { approvalResult }
  })
}
