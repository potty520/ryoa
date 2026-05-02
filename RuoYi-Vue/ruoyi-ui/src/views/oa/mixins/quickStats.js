/**
 * Reusable quick filter stats logic.
 *
 * Required page fields:
 * - quickStatsListApi(query): list API, response should contain total
 * - quickPendingStatus: pending status value
 * - quickArchivedStatus: archived status value
 * - queryParams: should contain pageNum/pageSize/docStatus
 *
 * Optional:
 * - statsKeyFields: fields used to decide if stats should refresh
 */
export default {
  data() {
    return {
      quickStats: { pending: 0, archived: 0 },
      statsLoading: false,
      lastStatsKey: ""
    }
  },
  methods: {
    validateQuickStatsConfig() {
      const validApi = typeof this.quickStatsListApi === "function"
      const validPending = this.quickPendingStatus !== undefined && this.quickPendingStatus !== null
      const validArchived = this.quickArchivedStatus !== undefined && this.quickArchivedStatus !== null
      if (validApi && validPending && validArchived) {
        return true
      }
      if (process.env.NODE_ENV !== "production") {
        console.warn("[quickStatsMixin] missing required config", {
          hasQuickStatsListApi: validApi,
          quickPendingStatus: this.quickPendingStatus,
          quickArchivedStatus: this.quickArchivedStatus
        })
      }
      return false
    },
    applyQuickFilter(status) {
      this.queryParams.pageNum = 1
      this.queryParams.docStatus = status
      this.getList()
    },
    buildStatsKey() {
      const fields = this.statsKeyFields || ["docId", "docNum", "docTitle"]
      return fields.map((key) => this.queryParams[key]).join("|")
    },
    loadQuickStats() {
      if (!this.validateQuickStatsConfig()) {
        this.quickStats.pending = 0
        this.quickStats.archived = 0
        return
      }
      const baseParams = { ...this.queryParams, pageNum: 1, pageSize: 1 }
      this.statsLoading = true
      Promise.allSettled([
        this.quickStatsListApi({ ...baseParams, docStatus: this.quickPendingStatus }),
        this.quickStatsListApi({ ...baseParams, docStatus: this.quickArchivedStatus })
      ]).then((results) => {
        this.quickStats.pending = results[0].status === "fulfilled" ? (results[0].value.total || 0) : 0
        this.quickStats.archived = results[1].status === "fulfilled" ? (results[1].value.total || 0) : 0
      }).finally(() => {
        this.statsLoading = false
      })
    },
    refreshQuickStatsIfNeeded() {
      const statsKey = this.buildStatsKey()
      if (statsKey !== this.lastStatsKey) {
        this.lastStatsKey = statsKey
        this.loadQuickStats()
      }
    }
  }
}
