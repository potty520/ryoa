import request from "@/utils/request"

export function listReimbursement(query) {
  return request({
    url: "/system/reimbursement/list",
    method: "get",
    params: query
  })
}

export function listMyReimbursement(query) {
  return request({
    url: "/system/reimbursement/my/list",
    method: "get",
    params: query
  })
}

export function getReimbursement(reimbId) {
  return request({
    url: "/system/reimbursement/" + reimbId,
    method: "get"
  })
}

export function addReimbursement(data) {
  return request({
    url: "/system/reimbursement",
    method: "post",
    data: data
  })
}

export function updateReimbursement(data) {
  return request({
    url: "/system/reimbursement",
    method: "put",
    data: data
  })
}

export function saveReimbursementWithDetails(data) {
  return request({
    url: "/system/reimbursement/saveWithDetails",
    method: "post",
    data: data
  })
}

export function delReimbursement(reimbId) {
  return request({
    url: "/system/reimbursement/" + reimbId,
    method: "delete"
  })
}

export function submitReimbursement(reimbId) {
  return request({
    url: "/system/reimbursement/submit/" + reimbId,
    method: "put"
  })
}

export function withdrawReimbursement(reimbId) {
  return request({
    url: "/system/reimbursement/withdraw/" + reimbId,
    method: "put"
  })
}

export function approveReimbursement(reimbId, approvalResult, approvalRemark) {
  return request({
    url: "/system/reimbursement/approve/" + reimbId,
    method: "put",
    params: { approvalResult, approvalRemark }
  })
}
