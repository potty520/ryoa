<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="发文编号">
        <el-input v-model="queryParams.docNum" placeholder="请输入发文编号" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="queryParams.docTitle" placeholder="请输入标题" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="queryParams.docStatus" placeholder="请选择状态" clearable style="width: 160px">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="handleAdd" v-hasPermi="['oa:outgoing:add']">新增</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain size="mini" :disabled="single" @click="handleEdit" v-hasPermi="['oa:outgoing:edit']">修改</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['oa:outgoing:remove']">删除</el-button></el-col>
      <el-col :span="8">
        <el-button-group>
          <el-button size="mini" :loading="statsLoading" :type="queryParams.docStatus === quickPendingStatus ? 'primary' : 'default'" @click="applyQuickFilter(quickPendingStatus)">待我处理({{ statsLoading ? "-" : quickStats.pending }})</el-button>
          <el-button size="mini" :loading="statsLoading" :type="queryParams.docStatus === quickArchivedStatus ? 'primary' : 'default'" @click="applyQuickFilter(quickArchivedStatus)">已归档({{ statsLoading ? "-" : quickStats.archived }})</el-button>
          <el-button size="mini" :type="!queryParams.docStatus ? 'primary' : 'default'" @click="applyQuickFilter(undefined)">全部</el-button>
        </el-button-group>
      </el-col>
    </el-row>

    <el-table ref="docTable" v-loading="loading" :data="docList" highlight-current-row @selection-change="onSelect">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="docId" width="80" />
      <el-table-column label="发文编号" prop="docNum" width="140" />
      <el-table-column label="标题" prop="docTitle" min-width="220" />
      <el-table-column label="主送单位" prop="mainUnit" min-width="180" />
      <el-table-column label="状态" width="120">
        <template slot-scope="s">{{ statusLabel(s.row.docStatus) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="320">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="handleEdit(s.row)" v-hasPermi="['oa:outgoing:edit']">修改</el-button>
          <el-button type="text" size="mini" @click="handleDelete(s.row)" v-hasPermi="['oa:outgoing:remove']">删除</el-button>
          <el-button v-if="['0','1'].includes(s.row.docStatus)" type="text" size="mini" @click="handleSubmitFlow(s.row)" v-hasPermi="['oa:outgoing:edit']">提交</el-button>
          <el-button v-if="s.row.docStatus === '2'" type="text" size="mini" @click="handleApprove(s.row, '1')" v-hasPermi="['oa:outgoing:approve']">通过</el-button>
          <el-button v-if="s.row.docStatus === '2'" type="text" size="mini" @click="handleApprove(s.row, '2')" v-hasPermi="['oa:outgoing:approve']">驳回</el-button>
          <el-button v-if="s.row.docStatus === '2'" type="text" size="mini" @click="handleApprove(s.row, '3')" v-hasPermi="['oa:outgoing:approve']">退回</el-button>
          <el-button v-if="s.row.docStatus === '3'" type="text" size="mini" @click="handleArchive(s.row)" v-hasPermi="['oa:outgoing:edit']">归档</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="700px">
      <el-form ref="form" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="发文编号">
          <el-input v-model="form.docNum" maxlength="50" />
        </el-form-item>
        <el-form-item label="标题" prop="docTitle">
          <el-input v-model="form.docTitle" maxlength="200" />
        </el-form-item>
        <el-form-item label="主送单位">
          <el-input v-model="form.mainUnit" maxlength="200" />
        </el-form-item>
        <el-form-item label="抄送单位">
          <el-input v-model="form.copyUnit" maxlength="200" />
        </el-form-item>
        <el-form-item label="发文字号">
          <el-input v-model="form.docCode" maxlength="100" />
        </el-form-item>
        <el-form-item label="紧急程度">
          <el-select v-model="form.docLevel" style="width: 100%">
            <el-option label="普通" value="1" />
            <el-option label="重要" value="2" />
            <el-option label="紧急" value="3" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOutgoing, getOutgoing, addOutgoing, updateOutgoing, delOutgoing, submitOutgoing, approveOutgoing, archiveOutgoing } from "@/api/oa/outgoing"
import quickStatsMixin from "../mixins/quickStats"
import { DOC_STATUS_CONFIG } from "../constants/docStatusConfig"

export default {
  name: "OaOutgoing",
  mixins: [quickStatsMixin],
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      title: "",
      docList: [],
      ids: [],
      single: true,
      multiple: true,
      queryParams: { pageNum: 1, pageSize: 10, docId: undefined, docNum: undefined, docTitle: undefined, docStatus: undefined },
      form: {},
      quickPendingStatus: DOC_STATUS_CONFIG.outgoing.quickStats.pending,
      quickArchivedStatus: DOC_STATUS_CONFIG.outgoing.quickStats.archived,
      quickStatsListApi: listOutgoing,
      rules: {
        docTitle: [{ required: true, message: "标题不能为空", trigger: "blur" }]
      },
      statusOptions: DOC_STATUS_CONFIG.outgoing.options
    }
  },
  created() {
    const routeDocId = this.$route.query.docId
    if (routeDocId) {
      this.queryParams.docId = Number(routeDocId)
    }
    this.getList()
  },
  methods: {
    resetForm() {
      this.form = { docId: undefined, docNum: undefined, docTitle: undefined, mainUnit: undefined, copyUnit: undefined, docCode: undefined, docLevel: "1" }
    },
    statusLabel(v) {
      return (this.statusOptions.find((x) => x.value === v) || {}).label || v
    },
    onSelect(selection) {
      this.ids = selection.map((i) => i.docId)
      this.single = selection.length !== 1
      this.multiple = selection.length === 0
    },
    handleAdd() {
      this.resetForm()
      this.title = "新增发文"
      this.open = true
    },
    handleEdit(row) {
      const docId = row.docId || this.ids[0]
      getOutgoing(docId).then((r) => {
        this.form = r.data
        this.title = "修改发文"
        this.open = true
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        const req = this.form.docId ? updateOutgoing(this.form) : addOutgoing(this.form)
        req.then(() => {
          this.$modal.msgSuccess(this.form.docId ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.docId ? [row.docId] : this.ids
      this.$modal.confirm(`确认删除发文编号为 ${ids.join(", ")} 的记录吗？`).then(() => delOutgoing(ids)).then(() => {
        this.$modal.msgSuccess("删除成功")
        this.getList()
      }).catch(() => {})
    },
    handleSubmitFlow(row) {
      this.$modal.confirm(`确认提交发文「${row.docTitle}」到下一环节吗？`).then(() => submitOutgoing(row.docId)).then(() => {
        this.$modal.msgSuccess("提交成功")
        this.getList()
      }).catch(() => {})
    },
    handleApprove(row, result) {
      const actionMap = { "1": "通过", "2": "驳回", "3": "退回" }
      const actionText = actionMap[result] || "审批"
      this.$prompt(`请输入审批意见（可选）`, `${actionText}确认`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: ""
      }).then(({ value }) => {
        return approveOutgoing(row.docId, result, value || "")
      }).then(() => {
        this.$modal.msgSuccess(`${actionText}成功`)
        this.getList()
      }).catch(() => {})
    },
    handleArchive(row) {
      this.$modal.confirm(`确认归档发文「${row.docTitle}」吗？`).then(() => archiveOutgoing(row.docId)).then(() => {
        this.$modal.msgSuccess("归档成功")
        this.getList()
      }).catch(() => {})
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, docId: undefined, docNum: undefined, docTitle: undefined, docStatus: undefined }
      this.getList()
    },
    getList() {
      this.loading = true
      listOutgoing(this.queryParams).then((r) => {
        this.docList = r.rows
        this.total = r.total
        this.refreshQuickStatsIfNeeded()
        if (this.queryParams.docId) {
          const target = this.docList.find((x) => x.docId === this.queryParams.docId)
          if (target) {
            this.$nextTick(() => {
              this.$refs.docTable && this.$refs.docTable.setCurrentRow(target)
            })
          }
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    }
  }
}
</script>
