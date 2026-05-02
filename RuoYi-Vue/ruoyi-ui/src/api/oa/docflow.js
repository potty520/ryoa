import request from "@/utils/request"

export function listDocflow(query) {
  return request({
    url: "/oa/docflow/list",
    method: "get",
    params: query
  })
}

export function getDocflow(flowId) {
  return request({
    url: "/oa/docflow/" + flowId,
    method: "get"
  })
}

export function addDocflow(data) {
  return request({
    url: "/oa/docflow",
    method: "post",
    data: data
  })
}

export function updateDocflow(data) {
  return request({
    url: "/oa/docflow",
    method: "put",
    data: data
  })
}

export function delDocflow(flowId) {
  return request({
    url: "/oa/docflow/" + flowId,
    method: "delete"
  })
}

export function saveFlowWithSteps(data) {
  return request({
    url: "/oa/docflow/saveWithSteps",
    method: "post",
    data: data
  })
}

export function listFlowSteps(flowId) {
  return request({
    url: "/oa/docflow/steps/" + flowId,
    method: "get"
  })
}
