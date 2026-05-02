<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="流程名称">
        <el-input v-model="queryParams.flowName" placeholder="请输入流程名称" clearable />
      </el-form-item>
      <el-form-item label="流程类别">
        <el-select v-model="queryParams.flowCategory" placeholder="全部" clearable style="width: 120px">
          <el-option label="收文流程" value="1" />
          <el-option label="发文流程" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否默认">
        <el-select v-model="queryParams.isDefault" placeholder="全部" clearable style="width: 100px">
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否启用">
        <el-select v-model="queryParams.isActive" placeholder="全部" clearable style="width: 100px">
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="handleAdd" v-hasPermi="['oa:docflow:add']">新增</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain size="mini" :disabled="single" @click="handleEdit" v-hasPermi="['oa:docflow:edit']">修改</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['oa:docflow:remove']">删除</el-button></el-col>
    </el-row>

    <el-table v-loading="loading" :data="flowList" @selection-change="onSelect">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="flowId" width="80" />
      <el-table-column label="流程名称" prop="flowName" min-width="220" />
      <el-table-column label="流程类别" width="120">
        <template slot-scope="s">{{ s.row.flowCategory === "2" ? "发文流程" : "收文流程" }}</template>
      </el-table-column>
      <el-table-column label="默认" width="80">
        <template slot-scope="s">{{ s.row.isDefault === "1" ? "是" : "否" }}</template>
      </el-table-column>
      <el-table-column label="启用" width="80">
        <template slot-scope="s">{{ s.row.isActive === "1" ? "是" : "否" }}</template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="handleEdit(s.row)" v-hasPermi="['oa:docflow:edit']">修改</el-button>
          <el-button type="text" size="mini" @click="handleDelete(s.row)" v-hasPermi="['oa:docflow:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="960px">
      <el-form ref="form" :model="form" :rules="rules" label-width="96px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="流程名称" prop="flowName">
              <el-input v-model="form.flowName" maxlength="100" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="流程类别">
              <el-radio-group v-model="form.flowCategory">
                <el-radio label="1">收文流程</el-radio>
                <el-radio label="2">发文流程</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="是否默认">
              <el-radio-group v-model="form.isDefault">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否启用">
              <el-radio-group v-model="form.isActive">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-divider content-position="left">流程步骤</el-divider>
      <div class="mb8">
        <el-button size="mini" type="primary" plain @click="addStep">新增步骤</el-button>
      </div>
      <el-table :data="steps" border>
        <el-table-column label="序号" width="90">
          <template slot-scope="s">
            <el-input-number v-model="s.row.stepOrder" :min="1" :controls="false" size="mini" style="width: 70px" />
          </template>
        </el-table-column>
        <el-table-column label="步骤名称" min-width="180">
          <template slot-scope="s">
            <el-input v-model="s.row.stepName" size="mini" />
          </template>
        </el-table-column>
        <el-table-column label="步骤类型" width="130">
          <template slot-scope="s">
            <el-select v-model="s.row.stepType" size="mini">
              <el-option label="起草" value="1" />
              <el-option label="审核" value="2" />
              <el-option label="审批" value="3" />
              <el-option label="签发" value="4" />
              <el-option label="核稿" value="5" />
              <el-option label="分发" value="6" />
              <el-option label="归档" value="7" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="审批人" min-width="160">
          <template slot-scope="s">
            <el-input v-model="s.row.approverName" size="mini" />
          </template>
        </el-table-column>
        <el-table-column label="必经" width="80">
          <template slot-scope="s">
            <el-switch v-model="s.row.isRequired" active-value="1" inactive-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="退回" width="80">
          <template slot-scope="s">
            <el-switch v-model="s.row.canBack" active-value="1" inactive-value="0" />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="s">
            <el-button type="text" size="mini" :disabled="s.$index === 0" @click="moveStepUp(s.$index)">上移</el-button>
            <el-button type="text" size="mini" :disabled="s.$index === steps.length - 1" @click="moveStepDown(s.$index)">下移</el-button>
            <el-button type="text" size="mini" @click="removeStep(s.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDocflow, getDocflow, delDocflow, saveFlowWithSteps } from "@/api/oa/docflow"

export default {
  name: "OaDocflow",
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      title: "",
      flowList: [],
      ids: [],
      single: true,
      multiple: true,
      queryParams: { pageNum: 1, pageSize: 10, flowName: undefined, flowCategory: undefined, isDefault: undefined, isActive: undefined },
      form: {},
      steps: [],
      rules: {
        flowName: [{ required: true, message: "流程名称不能为空", trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    normalizeStepOrders() {
      this.steps = [...this.steps]
        .sort((a, b) => (Number(a.stepOrder) || 0) - (Number(b.stepOrder) || 0))
        .map((step, index) => ({ ...step, stepOrder: index + 1 }))
    },
    resetForm() {
      this.form = { flowId: undefined, flowName: undefined, flowCategory: "1", isDefault: "0", isActive: "1" }
      this.steps = []
    },
    onSelect(selection) {
      this.ids = selection.map((i) => i.flowId)
      this.single = selection.length !== 1
      this.multiple = selection.length === 0
    },
    handleAdd() {
      this.resetForm()
      this.title = "新增流程"
      this.open = true
    },
    handleEdit(row) {
      const flowId = row.flowId || this.ids[0]
      getDocflow(flowId).then((r) => {
        this.form = r.flow || {}
        this.steps = r.steps || []
        this.normalizeStepOrders()
        this.title = "修改流程"
        this.open = true
      })
    },
    addStep() {
      this.steps.push({ stepOrder: this.steps.length + 1, stepName: "", stepType: "2", approverName: "", isRequired: "1", canBack: "0" })
      this.normalizeStepOrders()
    },
    removeStep(index) {
      this.steps.splice(index, 1)
      this.normalizeStepOrders()
    },
    moveStepUp(index) {
      if (index <= 0) return
      const temp = this.steps[index - 1]
      this.$set(this.steps, index - 1, this.steps[index])
      this.$set(this.steps, index, temp)
      this.normalizeStepOrders()
    },
    moveStepDown(index) {
      if (index >= this.steps.length - 1) return
      const temp = this.steps[index + 1]
      this.$set(this.steps, index + 1, this.steps[index])
      this.$set(this.steps, index, temp)
      this.normalizeStepOrders()
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        if (!this.steps.length) {
          this.$modal.msgError("请至少添加一个流程步骤")
          return
        }
        this.normalizeStepOrders()
        const invalid = this.steps.some((s) => !s.stepName || !s.stepType)
        if (invalid) {
          this.$modal.msgError("步骤名称和步骤类型不能为空")
          return
        }
        const nameSet = new Set()
        let duplicateName = ""
        for (const step of this.steps) {
          const key = (step.stepName || "").trim()
          if (!key) continue
          if (nameSet.has(key)) {
            duplicateName = key
            break
          }
          nameSet.add(key)
        }
        if (duplicateName) {
          this.$modal.msgError(`步骤名称“${duplicateName}”重复，请修改后再保存`)
          return
        }
        if (this.form.isDefault === "1" && this.form.isActive === "0") {
          this.$modal.msgError("默认流程必须为启用状态")
          return
        }
        if (this.form.isDefault === "1") {
          const conflict = this.flowList.find((item) =>
            item.flowCategory === this.form.flowCategory &&
            item.isDefault === "1" &&
            item.flowId !== this.form.flowId
          )
          if (conflict) {
            this.$modal.msgError("同一流程类别只能设置一个默认流程")
            return
          }
        }
        const data = { ...this.form, steps: this.steps }
        saveFlowWithSteps(data).then(() => {
          this.$modal.msgSuccess(this.form.flowId ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.flowId ? [row.flowId] : this.ids
      this.$modal.confirm(`确认删除流程 ${ids.join(", ")} 吗？`).then(() => delDocflow(ids)).then(() => {
        this.$modal.msgSuccess("删除成功")
        this.getList()
      }).catch(() => {})
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, flowName: undefined, flowCategory: undefined, isDefault: undefined, isActive: undefined }
      this.getList()
    },
    getList() {
      this.loading = true
      listDocflow(this.queryParams).then((r) => {
        this.flowList = r.rows
        this.total = r.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    }
  }
}
</script>
