export const DOC_STATUS_CONFIG = {
  incoming: {
    options: [
      { value: "0", label: "\u5f85\u7b7e\u6536" },
      { value: "1", label: "\u5f85\u767b\u8bb0" },
      { value: "2", label: "\u5f85\u5ba1\u6279" },
      { value: "3", label: "\u5ba1\u6279\u4e2d" },
      { value: "4", label: "\u5df2\u529e\u7ed3" },
      { value: "5", label: "\u5df2\u5f52\u6863" }
    ],
    quickStats: {
      pending: "3",
      archived: "5"
    }
  },
  outgoing: {
    options: [
      { value: "0", label: "\u8349\u7a3f" },
      { value: "1", label: "\u5f85\u5ba1\u6838" },
      { value: "2", label: "\u5ba1\u6838\u4e2d" },
      { value: "3", label: "\u5df2\u53d1\u5e03" },
      { value: "4", label: "\u5df2\u5f52\u6863" }
    ],
    quickStats: {
      pending: "2",
      archived: "4"
    }
  }
}
