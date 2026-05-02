<template>
  <div class="app-container">
    <el-form :model="queryParams" :inline="true" size="small">
      <el-form-item label="����">
        <el-input v-model="queryParams.annTitle" placeholder="���������" clearable @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item label="״̬">
        <el-select v-model="queryParams.status" placeholder="ȫ��" clearable style="width: 120px">
          <el-option label="�ݸ�" value="0" />
          <el-option label="�ѷ���" value="1" />
          <el-option label="������" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList">����</el-button>
        <el-button size="mini" @click="resetQuery">����</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="handleAdd" v-hasPermi="['oa:announcement:add']">����</el-button></el-col>
      <el-col :span="1.5"><el-button type="success" plain size="mini" :disabled="single" @click="handleEdit" v-hasPermi="['oa:announcement:edit']">�޸�</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['oa:announcement:remove']">ɾ��</el-button></el-col>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="onSelect">
      <el-table-column type="selection" width="55" />
      <el-table-column label="ID" prop="annId" width="80" />
      <el-table-column label="����" prop="annTitle" min-width="220" />
      <el-table-column label="����" width="120"><template slot-scope="s">{{ typeLabel(s.row.annType) }}</template></el-table-column>
      <el-table-column label="״̬" width="100"><template slot-scope="s">{{ statusLabel(s.row.status) }}</template></el-table-column>
      <el-table-column label="����ʱ��" width="170"><template slot-scope="s">{{ parseTime(s.row.publishStart) }}</template></el-table-column>
      <el-table-column label="����" width="140">
        <template slot-scope="s">
          <el-button type="text" size="mini" @click="handleEdit(s.row)" v-hasPermi="['oa:announcement:edit']">�޸�</el-button>
          <el-button type="text" size="mini" @click="handleDelete(s.row)" v-hasPermi="['oa:announcement:remove']">ɾ��</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="780px">
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="����" prop="annTitle"><el-input v-model="form.annTitle" maxlength="200" /></el-form-item>
        <el-form-item label="����" prop="annType">
          <el-select v-model="form.annType" style="width: 100%">
            <el-option label="��˾����" value="1" />
            <el-option label="���Ź���" value="2" />
            <el-option label="ϵͳ����" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="״̬" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">�ݸ�</el-radio>
            <el-radio label="1">�ѷ���</el-radio>
            <el-radio label="2">������</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="����" prop="annContent"><editor v-model="form.annContent" :min-height="180" /></el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click="submitForm">ȷ��</el-button>
        <el-button @click="open = false">ȡ��</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAnnouncement, getAnnouncement, addAnnouncement, updateAnnouncement, delAnnouncement } from "@/api/system/announcement"

export default {
  name: "OaAnnouncement",
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      title: "",
      list: [],
      ids: [],
      single: true,
      multiple: true,
      queryParams: { pageNum: 1, pageSize: 10, annTitle: undefined, status: undefined },
      form: {},
      rules: {
        annTitle: [{ required: true, message: "���ⲻ��Ϊ��", trigger: "blur" }],
        annType: [{ required: true, message: "���Ͳ���Ϊ��", trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    typeLabel(v) {
      return { "1": "��˾����", "2": "���Ź���", "3": "ϵͳ����" }[v] || v
    },
    statusLabel(v) {
      return { "0": "�ݸ�", "1": "�ѷ���", "2": "������" }[v] || v
    },
    getList() {
      this.loading = true
      listAnnouncement(this.queryParams).then((r) => {
        this.list = r.rows
        this.total = r.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, annTitle: undefined, status: undefined }
      this.getList()
    },
    onSelect(s) {
      this.ids = s.map((i) => i.annId)
      this.single = s.length !== 1
      this.multiple = s.length === 0
    },
    handleAdd() {
      this.form = { annId: undefined, annTitle: undefined, annType: "1", status: "0", annContent: "" }
      this.title = "��������"
      this.open = true
    },
    handleEdit(row) {
      const id = row.annId || this.ids[0]
      getAnnouncement(id).then((r) => {
        this.form = r.data
        this.title = "�޸Ĺ���"
        this.open = true
      })
    },
    submitForm() {
      this.$refs.form.validate((v) => {
        if (!v) return
        const req = this.form.annId ? updateAnnouncement(this.form) : addAnnouncement(this.form)
        req.then(() => {
          this.$modal.msgSuccess(this.form.annId ? "�޸ĳɹ�" : "�����ɹ�")
          this.open = false
          this.getList()
        })
      })
    },
    handleDelete(row) {
      const ids = row.annId ? [row.annId] : this.ids
      this.$modal.confirm(`ȷ��ɾ����${ids.join(", ")}��`).then(() => delAnnouncement(ids)).then(() => {
        this.$modal.msgSuccess("ɾ���ɹ�")
        this.getList()
      }).catch(() => {})
    }
  }
}
</script>
