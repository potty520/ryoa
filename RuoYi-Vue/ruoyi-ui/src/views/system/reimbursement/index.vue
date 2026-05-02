<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="��������">
        <el-select v-model="queryParams.reimbType" placeholder="ȫ��" clearable style="width: 140px">
          <el-option v-for="item in reimbTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="����״̬">
        <el-select v-model="queryParams.approvalStatus" placeholder="ȫ��" clearable style="width: 140px">
          <el-option v-for="item in approvalStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="֧��״̬">
        <el-select v-model="queryParams.paymentStatus" placeholder="ȫ��" clearable style="width: 140px">
          <el-option v-for="item in paymentStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="���ݷ�Χ">
        <el-switch v-model="onlyMine" active-text="�ҵ�" inactive-text="ȫ��" @change="getList" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList">����</el-button>
        <el-button size="mini" @click="resetQuery">����</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="handleAdd" v-hasPermi="['oa:reimb:add']">����</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain size="mini" :disabled="single" @click="handleEdit" v-hasPermi="['oa:reimb:edit']">�޸�</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['oa:reimb:remove']">ɾ��</el-button></el-col>
      <el-col :span="1.5"><el-button type="warning" plain size="mini" @click="handleExport" v-hasPermi="['oa:reimb:list']">����</el-button></el-col>
    </el-row>

    <el-table v-loading="loading" :data="reimbList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="reimbId" width="80" />
      <el-table-column label="��������" width="110">
        <template slot-scope="s">{{ reimbTypeLabel(s.row.reimbType) }}</template>
      </el-table-column>
      <el-table-column label="���" prop="totalAmount" width="110" />
      <el-table-column label="��������" prop="reimbReason" min-width="180" show-overflow-tooltip />
      <el-table-column label="����״̬" width="100">
        <template slot-scope="s">{{ approvalStatusLabel(s.row.approvalStatus) }}</template>
      </el-table-column>
      <el-table-column label="�������" prop="approveRemark" min-width="140" show-overflow-tooltip />
      <el-table-column label="֧��״̬" width="100">
        <template slot-scope="s">{{ paymentStatusLabel(s.row.paymentStatus) }}</template>
      </el-table-column>
      <el-table-column label="����ʱ��" width="170">
        <template slot-scope="s">{{ parseTime(s.row.applyTime) }}</template>
      </el-table-column>
      <el-table-column label="����" width="330">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="handleViewDetail(s.row)">��ϸ</el-button>
          <el-button type="text" size="mini" @click="handleEdit(s.row)" v-hasPermi="['oa:reimb:edit']">�޸�</el-button>
          <el-button type="text" size="mini" @click="handleDelete(s.row)" v-hasPermi="['oa:reimb:remove']">ɾ��</el-button>
          <el-button v-if="s.row.approvalStatus !== '0'" type="text" size="mini" @click="handleSubmit(s.row)" v-hasPermi="['oa:reimb:edit']">�ύ</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleWithdraw(s.row)" v-hasPermi="['oa:reimb:edit']">����</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleApprove(s.row, '1')" v-hasPermi="['oa:reimb:approve']">ͨ��</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleApprove(s.row, '2')" v-hasPermi="['oa:reimb:approve']">����</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="1080px">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="��������" prop="reimbType">
              <el-select v-model="form.reimbType" style="width: 100%">
                <el-option v-for="item in reimbTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="�������" prop="totalAmount">
              <el-input-number v-model="form.totalAmount" :min="0.01" :step="10" :precision="2" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="��������" prop="reimbReason">
          <el-input v-model="form.reimbReason" type="textarea" :rows="3" maxlength="500" show-word-limit />
        </el-form-item>
        <el-form-item label="��ע" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="2" maxlength="500" show-word-limit />
        </el-form-item>

        <el-divider content-position="left">������ϸ</el-divider>
        <el-button type="primary" plain size="mini" @click="handleAddDetail">������ϸ</el-button>
        <el-table :data="form.details" border style="width: 100%; margin-top: 10px">
          <el-table-column label="��������" min-width="130">
            <template slot-scope="s">
              <el-select v-model="s.row.expenseType" placeholder="��ѡ��" style="width: 100%">
                <el-option v-for="item in reimbTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="��������" min-width="150">
            <template slot-scope="s">
              <el-date-picker v-model="s.row.expenseDate" type="date" value-format="yyyy-MM-dd" placeholder="ѡ������" style="width: 100%" />
            </template>
          </el-table-column>
          <el-table-column label="���ý��" min-width="130">
            <template slot-scope="s">
              <el-input-number v-model="s.row.expenseAmount" :min="0.01" :step="10" :precision="2" style="width: 100%" @change="recalcTotalAmount" />
            </template>
          </el-table-column>
          <el-table-column label="��������" min-width="170">
            <template slot-scope="s">
              <el-input v-model="s.row.expenseDesc" maxlength="200" />
            </template>
          </el-table-column>
          <el-table-column label="�վ�" min-width="190">
            <template slot-scope="s">
              <image-upload v-model="s.row.receiptUrl" :limit="1" :fileSize="8" />
            </template>
          </el-table-column>
          <el-table-column label="����" width="90">
            <template slot-scope="s">
              <el-button type="text" size="mini" @click="handleRemoveDetail(s.$index)">ɾ��</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="submitForm">ȷ ��</el-button>
        <el-button @click="open = false">ȡ ��</el-button>
      </div>
    </el-dialog>

    <el-drawer title="������ϸ" :visible.sync="drawerOpen" size="55%">
      <div style="padding: 0 16px 16px 16px">
        <div style="margin-bottom: 10px">
          <el-button type="primary" plain size="mini" @click="exportDetailCsv">������ϸCSV</el-button>
        </div>
        <el-descriptions :column="2" border size="small" style="margin-bottom: 12px">
          <el-descriptions-item label="����ID">{{ viewData.reimbId }}</el-descriptions-item>
          <el-descriptions-item label="��������">{{ reimbTypeLabel(viewData.reimbType) }}</el-descriptions-item>
          <el-descriptions-item label="�������">{{ viewData.totalAmount }}</el-descriptions-item>
          <el-descriptions-item label="����ʱ��">{{ parseTime(viewData.applyTime) }}</el-descriptions-item>
          <el-descriptions-item label="����״̬">{{ approvalStatusLabel(viewData.approvalStatus) }}</el-descriptions-item>
          <el-descriptions-item label="֧��״̬">{{ paymentStatusLabel(viewData.paymentStatus) }}</el-descriptions-item>
          <el-descriptions-item label="������">{{ viewData.approveBy || "-" }}</el-descriptions-item>
          <el-descriptions-item label="����ʱ��">{{ parseTime(viewData.approveTime) || "-" }}</el-descriptions-item>
          <el-descriptions-item label="�������" :span="2">{{ viewData.approveRemark || "-" }}</el-descriptions-item>
          <el-descriptions-item label="��������" :span="2">{{ viewData.reimbReason }}</el-descriptions-item>
        </el-descriptions>
        <el-table :data="viewData.details || []" border>
          <el-table-column label="��������" min-width="120">
            <template slot-scope="s">{{ reimbTypeLabel(s.row.expenseType) }}</template>
          </el-table-column>
          <el-table-column label="��������" min-width="120">
            <template slot-scope="s">{{ parseTime(s.row.expenseDate, '{y}-{m}-{d}') }}</template>
          </el-table-column>
          <el-table-column label="���ý��" min-width="100" prop="expenseAmount" />
          <el-table-column label="��������" min-width="140" prop="expenseDesc" show-overflow-tooltip />
          <el-table-column label="�վ�" min-width="120">
            <template slot-scope="s">
              <el-image
                v-if="s.row.receiptUrl"
                style="width: 60px; height: 60px"
                :src="fileUrl(s.row.receiptUrl)"
                :preview-src-list="[fileUrl(s.row.receiptUrl)]"
                fit="cover"
              />
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
        <el-divider content-position="left">审批历史</el-divider>
        <el-table :data="viewData.approveLogs || []" border>
          <el-table-column label="审批结果" width="100">
            <template slot-scope="s">{{ approveResultLabel(s.row.approveResult) }}</template>
          </el-table-column>
          <el-table-column label="审批意见" min-width="180" prop="approveRemark" show-overflow-tooltip />
          <el-table-column label="审批人" width="120" prop="approveBy" />
          <el-table-column label="审批时间" width="180">
            <template slot-scope="s">{{ parseTime(s.row.approveTime) }}</template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  listReimbursement,
  listMyReimbursement,
  getReimbursement,
  saveReimbursementWithDetails,
  delReimbursement,
  submitReimbursement,
  withdrawReimbursement,
  approveReimbursement
} from "@/api/system/reimbursement"

export default {
  name: "OaReimbursement",
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      title: "",
      drawerOpen: false,
      viewData: {},
      onlyMine: false,
      reimbList: [],
      ids: [],
      single: true,
      multiple: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        reimbType: undefined,
        approvalStatus: undefined,
        paymentStatus: undefined
      },
      form: {},
      rules: {
        reimbType: [{ required: true, message: "�������Ͳ���Ϊ��", trigger: "change" }],
        totalAmount: [{ required: true, message: "��������Ϊ��", trigger: "change" }],
        reimbReason: [{ required: true, message: "�������ɲ���Ϊ��", trigger: "blur" }]
      },
      reimbTypeOptions: [
        { value: "1", label: "���÷�" },
        { value: "2", label: "��ͨ��" },
        { value: "3", label: "������" },
        { value: "4", label: "�칫��" },
        { value: "5", label: "����" }
      ],
      approvalStatusOptions: [
        { value: "0", label: "������" },
        { value: "1", label: "��ͨ��" },
        { value: "2", label: "�Ѿܾ�" },
        { value: "3", label: "�ѳ���" }
      ],
      paymentStatusOptions: [
        { value: "0", label: "δ֧��" },
        { value: "1", label: "��֧��" }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    reimbTypeLabel(value) {
      return (this.reimbTypeOptions.find((x) => x.value === value) || {}).label || value
    },
    approvalStatusLabel(value) {
      return (this.approvalStatusOptions.find((x) => x.value === value) || {}).label || value
    },
    paymentStatusLabel(value) {
      return (this.paymentStatusOptions.find((x) => x.value === value) || {}).label || value
    },
    approveResultLabel(value) {
      if (value === "1") return "通过"
      if (value === "2") return "驳回"
      return value || "-"
    },
    fileUrl(url) {
      if (!url) return ""
      if (url.startsWith("http://") || url.startsWith("https://")) return url
      return process.env.VUE_APP_BASE_API + url
    },
    resetFormData() {
      this.form = {
        reimbId: undefined,
        reimbType: "1",
        totalAmount: 0,
        reimbReason: undefined,
        remark: undefined,
        details: [this.newDetailRow()]
      }
    },
    newDetailRow() {
      return {
        expenseType: this.form && this.form.reimbType ? this.form.reimbType : "1",
        expenseDate: undefined,
        expenseAmount: 0,
        expenseDesc: undefined,
        receiptUrl: undefined
      }
    },
    handleAddDetail() {
      if (!this.form.details) this.form.details = []
      this.form.details.push(this.newDetailRow())
    },
    handleRemoveDetail(index) {
      this.form.details.splice(index, 1)
      this.recalcTotalAmount()
    },
    recalcTotalAmount() {
      const sum = (this.form.details || []).reduce((acc, item) => acc + Number(item.expenseAmount || 0), 0)
      this.form.totalAmount = Number(sum.toFixed(2))
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.reimbId)
      this.single = selection.length !== 1
      this.multiple = selection.length === 0
    },
    getList() {
      this.loading = true
      const req = this.onlyMine ? listMyReimbursement(this.queryParams) : listReimbursement(this.queryParams)
      req.then((res) => {
        this.reimbList = res.rows
        this.total = res.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, reimbType: undefined, approvalStatus: undefined, paymentStatus: undefined }
      this.onlyMine = false
      this.getList()
    },
    handleAdd() {
      this.resetFormData()
      this.title = "������������"
      this.open = true
    },
    handleViewDetail(row) {
      getReimbursement(row.reimbId).then((res) => {
        this.viewData = res.data || {}
        if (!this.viewData.details) this.viewData.details = []
        if (!this.viewData.approveLogs) this.viewData.approveLogs = []
        this.drawerOpen = true
      })
    },
    handleEdit(row) {
      const reimbId = row.reimbId || this.ids[0]
      getReimbursement(reimbId).then((res) => {
        this.form = res.data
        if (!this.form.details || this.form.details.length === 0) {
          this.form.details = [this.newDetailRow()]
        }
        this.recalcTotalAmount()
        this.title = "�޸ı�������"
        this.open = true
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        if (!this.form.details || this.form.details.length === 0) {
          this.$modal.msgError("����������һ��������ϸ")
          return
        }
        const invalid = this.form.details.some((d) => !d.expenseType || !d.expenseDate || !Number(d.expenseAmount))
        if (invalid) {
          this.$modal.msgError("��������д��ϸ�����͡����ںͽ��")
          return
        }
        this.recalcTotalAmount()
        saveReimbursementWithDetails(this.form).then(() => {
          this.$modal.msgSuccess(this.form.reimbId ? "�޸ĳɹ�" : "�����ɹ�")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.reimbId ? [row.reimbId] : this.ids
      this.$modal.confirm(`ȷ��ɾ��������¼ ${ids.join(", ")} ��`).then(() => delReimbursement(ids)).then(() => {
        this.$modal.msgSuccess("ɾ���ɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleSubmit(row) {
      this.$modal.confirm(`ȷ���ύ��������${row.reimbReason || row.reimbId}����`).then(() => submitReimbursement(row.reimbId)).then(() => {
        this.$modal.msgSuccess("�ύ�ɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleWithdraw(row) {
      this.$modal.confirm(`ȷ�ϳ��ر�������${row.reimbReason || row.reimbId}����`).then(() => withdrawReimbursement(row.reimbId)).then(() => {
        this.$modal.msgSuccess("���سɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleApprove(row, result) {
      const actionText = result === "1" ? "ͨ��" : "����"
      this.$prompt(`������${actionText}�������ѡ��`, `${actionText}������`, {
        confirmButtonText: "ȷ��",
        cancelButtonText: "ȡ��",
        inputType: "textarea",
        inputPlaceholder: "�������������"
      }).then(({ value }) => {
        return approveReimbursement(row.reimbId, result, value)
      }).then(() => {
        this.$modal.msgSuccess(`${actionText}�ɹ�`)
        this.getList()
      }).catch(() => {})
    },
    handleExport() {
      this.download("system/reimbursement/export", {
        ...this.queryParams
      }, `reimbursement_${new Date().getTime()}.xlsx`)
    },
    exportDetailCsv() {
      const details = this.viewData.details || []
      const headers = ["����ID", "��������", "��������", "���ý��", "��������", "�վ�URL"]
      const rows = details.map((item) => [
        this.viewData.reimbId || "",
        this.reimbTypeLabel(item.expenseType || ""),
        this.parseTime(item.expenseDate, "{y}-{m}-{d}") || "",
        item.expenseAmount || "",
        (item.expenseDesc || "").replace(/"/g, '""'),
        this.fileUrl(item.receiptUrl || "")
      ])
      const csv = [headers, ...rows].map((line) => line.map((v) => `"${v}"`).join(",")).join("\n")
      const blob = new Blob(["\ufeff" + csv], { type: "text/csv;charset=utf-8;" })
      const link = document.createElement("a")
      link.href = URL.createObjectURL(blob)
      link.download = `reimbursement_detail_${this.viewData.reimbId || new Date().getTime()}.csv`
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      URL.revokeObjectURL(link.href)
    }
  }
}
</script>
