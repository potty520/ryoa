import request from "@/utils/request"

export function listTodo(query) {
  return request({
    url: "/oa/todo/list",
    method: "get",
    params: query
  })
}

export function getTodoSummary() {
  return request({
    url: "/oa/todo/summary",
    method: "get"
  })
}
