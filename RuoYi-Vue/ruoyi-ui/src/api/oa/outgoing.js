import request from "@/utils/request"

export function listOutgoing(query) {
  return request({
    url: "/oa/outgoing/list",
    method: "get",
    params: query
  })
}

export function getOutgoing(docId) {
  return request({
    url: "/oa/outgoing/" + docId,
    method: "get"
  })
}

export function addOutgoing(data) {
  return request({
    url: "/oa/outgoing",
    method: "post",
    data: data
  })
}

export function updateOutgoing(data) {
  return request({
    url: "/oa/outgoing",
    method: "put",
    data: data
  })
}

export function delOutgoing(docId) {
  return request({
    url: "/oa/outgoing/" + docId,
    method: "delete"
  })
}

export function submitOutgoing(docId) {
  return request({
    url: "/oa/outgoing/submit/" + docId,
    method: "put"
  })
}

export function approveOutgoing(docId, approvalResult, comment) {
  return request({
    url: "/oa/outgoing/approve/" + docId,
    method: "put",
    params: { approvalResult, comment }
  })
}

export function archiveOutgoing(docId) {
  return request({
    url: "/oa/outgoing/archive/" + docId,
    method: "put"
  })
}
