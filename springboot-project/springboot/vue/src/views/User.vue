<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="input name" suffix-icon="el-icon-search" class="ml-5" v-model="username"></el-input>
      <el-input style="width: 200px"  placeholder="input email" suffix-icon="el-icon-message" class="ml-5"></el-input>
      <el-input style="width: 200px"  placeholder="input address" suffix-icon="el-icon-position" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>

      <el-popconfirm class="ml-5" confirm-button-text="确定" cancel-button-text="我再想想"
                     icon="el-icon-info" icon-color="red" title="您确定删除吗" @confirm="delBatch"
      >
        <el-button type="primary" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>


      <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i></el-button>
      <el-button type="primary">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名" width="100"></el-table-column>
      <el-table-column prop="nickname" label="昵称" width="100"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="warning" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="用户名" >
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" >
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" >
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "User",
  data(){
    return{
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      username:"",
      dialogFormVisible:false,
      form:{},
      multipleSelection:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/user/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username
        }
      }).
      then(res => {
        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
      this.username = ""
      this.email = ""
      this.address = ""
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    handAdd(){
      this.dialogFormVisible = true
      this.form={}
    },
    save(){
      request.post("/user",this.form).then(res=>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    handleDel(id){
      request.delete("/user/"+id).then(res=>{
        if (res){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){
      this.multipleSelection = val
      // console.log(val)
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      request.delete("/user/del/batch",{data:ids}).then(res=>{
        if (res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    }
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #cccccc   !important;
}
</style>