import request from "@/utils/request"

export function listIncoming(query) {
  return request({
    url: "/oa/incoming/list",
    method: "get",
    params: query
  })
}

export function getIncoming(docId) {
  return request({
    url: "/oa/incoming/" + docId,
    method: "get"
  })
}

export function addIncoming(data) {
  return request({
    url: "/oa/incoming",
    method: "post",
    data: data
  })
}

export function updateIncoming(data) {
  return request({
    url: "/oa/incoming",
    method: "put",
    data: data
  })
}

export function delIncoming(docId) {
  return request({
    url: "/oa/incoming/" + docId,
    method: "delete"
  })
}

export function submitIncoming(docId) {
  return request({
    url: "/oa/incoming/submit/" + docId,
    method: "put"
  })
}

export function approveIncoming(docId, approvalResult, comment) {
  return request({
    url: "/oa/incoming/approve/" + docId,
    method: "put",
    params: { approvalResult, comment }
  })
}

export function receiveIncoming(docId) {
  return request({
    url: "/oa/incoming/receive/" + docId,
    method: "put"
  })
}

export function archiveIncoming(docId) {
  return request({
    url: "/oa/incoming/archive/" + docId,
    method: "put"
  })
}
