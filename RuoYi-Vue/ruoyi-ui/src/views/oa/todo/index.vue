<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="待办类型">
        <el-select v-model="queryParams.todoType" placeholder="全部" clearable style="width: 120px">
          <el-option label="收文" value="incoming" />
          <el-option label="发文" value="outgoing" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="queryParams.status" placeholder="请输入状态值" clearable style="width: 140px" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="12" class="mb12">
      <el-col :span="8">
        <el-card shadow="hover">
          <div>总待办</div>
          <div style="font-size: 26px; font-weight: 600; margin-top: 8px">{{ summary.totalTodoCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div>收文待办</div>
          <div style="font-size: 26px; font-weight: 600; margin-top: 8px">{{ summary.incomingTodoCount || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div>发文待办</div>
          <div style="font-size: 26px; font-weight: 600; margin-top: 8px">{{ summary.outgoingTodoCount || 0 }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="todoList">
      <el-table-column label="类型" width="90">
        <template slot-scope="s">
          <el-tag size="mini" :type="s.row.todoType === 'incoming' ? 'warning' : 'success'">
            {{ s.row.todoType === "incoming" ? "收文" : "发文" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="编号" prop="docNum" width="180" />
      <el-table-column label="标题" prop="docTitle" min-width="260" />
      <el-table-column label="当前环节" prop="currentNode" width="160" />
      <el-table-column label="状态" prop="statusLabel" width="120" />
      <el-table-column label="创建时间" width="180">
        <template slot-scope="s">{{ parseTime(s.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="100">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="goDetail(s.row)">去处理</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listTodo, getTodoSummary } from "@/api/oa/todo"

export default {
  name: "OaTodo",
  data() {
    return {
      loading: false,
      summary: {},
      total: 0,
      todoList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        todoType: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getSummary()
    this.getList()
  },
  methods: {
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, todoType: undefined, status: undefined }
      this.getList()
    },
    getSummary() {
      getTodoSummary().then((r) => {
        this.summary = r.data || {}
      })
    },
    getList() {
      this.loading = true
      listTodo(this.queryParams).then((r) => {
        this.todoList = (r.data && r.data.rows) || []
        this.total = (r.data && r.data.total) || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    goDetail(row) {
      if (row.todoType === "incoming") {
        this.$router.push({ path: "/oa/incoming", query: { docId: row.docId } })
        return
      }
      this.$router.push({ path: "/oa/outgoing", query: { docId: row.docId } })
    }
  }
}
</script>
