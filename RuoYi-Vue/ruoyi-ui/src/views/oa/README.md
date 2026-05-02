# OA Reuse Guide

## quickStatsMixin

Location: `src/views/oa/mixins/quickStats.js`

Purpose: provide quick filters (`pending`, `archived`, `all`) with count badges, request fallback, and deduplicated stats refresh.

### Required page config

Provide these fields in page `data()`:

- `quickStatsListApi(query)`: list API function, response must contain `total`
- `quickPendingStatus`: status value for pending items
- `quickArchivedStatus`: status value for archived items
- `queryParams`: must include `pageNum`, `pageSize`, `docStatus`

### Optional config

- `statsKeyFields`: fields used to decide whether stats should refresh, default is `["docId", "docNum", "docTitle"]`

### Example

```javascript
import quickStatsMixin from "./mixins/quickStats"
import { DOC_STATUS_CONFIG } from "./constants/docStatusConfig"
import { listIncoming } from "@/api/oa/incoming"

export default {
  mixins: [quickStatsMixin],
  data() {
    return {
      queryParams: { pageNum: 1, pageSize: 10, docStatus: undefined },
      quickPendingStatus: DOC_STATUS_CONFIG.incoming.quickStats.pending,
      quickArchivedStatus: DOC_STATUS_CONFIG.incoming.quickStats.archived,
      quickStatsListApi: listIncoming
    }
  },
  methods: {
    getList() {
      listIncoming(this.queryParams).then((r) => {
        this.rows = r.rows
        this.total = r.total
        this.refreshQuickStatsIfNeeded()
      })
    }
  }
}
```

## FAQ

### Why do counts not change when switching pages?

Expected behavior. Stats refresh only when query conditions change, not when only `pageNum` or `pageSize` changes.

### Which fields trigger stats refresh?

Default: `["docId", "docNum", "docTitle"]`.  
If your page has extra filters, override `statsKeyFields`.

### Why count is `-` or `0`?

- `-`: stats request is loading (`statsLoading = true`)
- `0`: no data, or request failed and fallback value is used
