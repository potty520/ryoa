<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="�������">
        <el-select v-model="queryParams.leaveType" placeholder="ȫ��" clearable style="width: 140px">
          <el-option v-for="item in leaveTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="����״̬">
        <el-select v-model="queryParams.approvalStatus" placeholder="ȫ��" clearable style="width: 140px">
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
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
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="handleAdd" v-hasPermi="['oa:leave:add']">����</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain size="mini" :disabled="single" @click="handleEdit" v-hasPermi="['oa:leave:edit']">�޸�</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['oa:leave:remove']">ɾ��</el-button></el-col>
    </el-row>

    <el-table v-loading="loading" :data="leaveList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="leaveId" width="80" />
      <el-table-column label="�������" width="100">
        <template slot-scope="s">{{ leaveTypeLabel(s.row.leaveType) }}</template>
      </el-table-column>
      <el-table-column label="��ʼʱ��" width="170">
        <template slot-scope="s">{{ parseTime(s.row.startTime) }}</template>
      </el-table-column>
      <el-table-column label="����ʱ��" width="170">
        <template slot-scope="s">{{ parseTime(s.row.endTime) }}</template>
      </el-table-column>
      <el-table-column label="����" prop="leaveDays" width="80" />
      <el-table-column label="���ԭ��" prop="leaveReason" min-width="200" show-overflow-tooltip />
      <el-table-column label="״̬" width="100">
        <template slot-scope="s">{{ statusLabel(s.row.approvalStatus) }}</template>
      </el-table-column>
      <el-table-column label="����" width="280">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="handleEdit(s.row)" v-hasPermi="['oa:leave:edit']">�޸�</el-button>
          <el-button type="text" size="mini" @click="handleDelete(s.row)" v-hasPermi="['oa:leave:remove']">ɾ��</el-button>
          <el-button v-if="s.row.approvalStatus !== '0'" type="text" size="mini" @click="handleSubmit(s.row)" v-hasPermi="['oa:leave:edit']">�ύ</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleWithdraw(s.row)" v-hasPermi="['oa:leave:edit']">����</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleApprove(s.row, '1')" v-hasPermi="['oa:leave:approve']">ͨ��</el-button>
          <el-button v-if="s.row.approvalStatus === '0'" type="text" size="mini" @click="handleApprove(s.row, '2')" v-hasPermi="['oa:leave:approve']">����</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="720px">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="�������" prop="leaveType">
              <el-select v-model="form.leaveType" style="width: 100%">
                <el-option v-for="item in leaveTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="�������" prop="leaveDays">
              <el-input-number v-model="form.leaveDays" :min="0.5" :step="0.5" :precision="1" controls-position="right" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="12">
          <el-col :span="12">
            <el-form-item label="��ʼʱ��" prop="startTime">
              <el-date-picker v-model="form.startTime" type="datetime" placeholder="ѡ��ʼʱ��" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="����ʱ��" prop="endTime">
              <el-date-picker v-model="form.endTime" type="datetime" placeholder="ѡ�����ʱ��" value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="���ԭ��" prop="leaveReason">
          <el-input v-model="form.leaveReason" type="textarea" :rows="4" maxlength="500" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="submitForm">ȷ ��</el-button>
        <el-button @click="open = false">ȡ ��</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listLeave, listMyLeave, getLeave, addLeave, updateLeave, delLeave, submitLeave, withdrawLeave, approveLeave } from "@/api/system/leave"

export default {
  name: "OaLeave",
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      title: "",
      onlyMine: false,
      leaveList: [],
      ids: [],
      single: true,
      multiple: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        leaveType: undefined,
        approvalStatus: undefined
      },
      form: {},
      rules: {
        leaveType: [{ required: true, message: "������Ͳ���Ϊ��", trigger: "change" }],
        startTime: [{ required: true, message: "��ʼʱ�䲻��Ϊ��", trigger: "change" }],
        endTime: [{ required: true, message: "����ʱ�䲻��Ϊ��", trigger: "change" }],
        leaveDays: [{ required: true, message: "�����������Ϊ��", trigger: "change" }],
        leaveReason: [{ required: true, message: "���ԭ����Ϊ��", trigger: "blur" }]
      },
      leaveTypeOptions: [
        { value: "1", label: "���" },
        { value: "2", label: "�¼�" },
        { value: "3", label: "����" },
        { value: "4", label: "���" },
        { value: "5", label: "����" },
        { value: "6", label: "ɥ��" }
      ],
      statusOptions: [
        { value: "0", label: "������" },
        { value: "1", label: "��ͨ��" },
        { value: "2", label: "�Ѿܾ�" },
        { value: "3", label: "�ѳ���" }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    leaveTypeLabel(value) {
      return (this.leaveTypeOptions.find((x) => x.value === value) || {}).label || value
    },
    statusLabel(value) {
      return (this.statusOptions.find((x) => x.value === value) || {}).label || value
    },
    resetFormData() {
      this.form = {
        leaveId: undefined,
        leaveType: "1",
        startTime: undefined,
        endTime: undefined,
        leaveDays: 1,
        leaveReason: undefined
      }
    },
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.leaveId)
      this.single = selection.length !== 1
      this.multiple = selection.length === 0
    },
    getList() {
      this.loading = true
      const req = this.onlyMine ? listMyLeave(this.queryParams) : listLeave(this.queryParams)
      req.then((res) => {
        this.leaveList = res.rows
        this.total = res.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, leaveType: undefined, approvalStatus: undefined }
      this.onlyMine = false
      this.getList()
    },
    handleAdd() {
      this.resetFormData()
      this.title = "�������"
      this.open = true
    },
    handleEdit(row) {
      const leaveId = row.leaveId || this.ids[0]
      getLeave(leaveId).then((res) => {
        this.form = res.data
        this.title = "�޸����"
        this.open = true
      })
    },
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (!valid) return
        if (this.form.endTime < this.form.startTime) {
          this.$modal.msgError("����ʱ�䲻�����ڿ�ʼʱ��")
          return
        }
        const req = this.form.leaveId ? updateLeave(this.form) : addLeave(this.form)
        req.then(() => {
          this.$modal.msgSuccess(this.form.leaveId ? "�޸ĳɹ�" : "�����ɹ�")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.leaveId ? [row.leaveId] : this.ids
      this.$modal.confirm(`ȷ��ɾ����ټ�¼ ${ids.join(", ")} ��`).then(() => delLeave(ids)).then(() => {
        this.$modal.msgSuccess("ɾ���ɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleSubmit(row) {
      this.$modal.confirm(`ȷ���ύ��١�${row.leaveReason || row.leaveId}����`).then(() => submitLeave(row.leaveId)).then(() => {
        this.$modal.msgSuccess("�ύ�ɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleWithdraw(row) {
      this.$modal.confirm(`ȷ�ϳ�����١�${row.leaveReason || row.leaveId}����`).then(() => withdrawLeave(row.leaveId)).then(() => {
        this.$modal.msgSuccess("���سɹ�")
        this.getList()
      }).catch(() => {})
    },
    handleApprove(row, result) {
      const actionText = result === "1" ? "ͨ��" : "����"
      this.$modal.confirm(`ȷ��${actionText}�������`).then(() => approveLeave(row.leaveId, result)).then(() => {
        this.$modal.msgSuccess(`${actionText}�ɹ�`)
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>
