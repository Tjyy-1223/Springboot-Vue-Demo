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

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange"
        row-key="id" default-expand-all>
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="名称" width="100"></el-table-column>
      <el-table-column prop="path" label="路径" width="100"></el-table-column>
      <el-table-column prop="icon" label="图标" align="center">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述"></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button type="success" @click="addChildren(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增子菜单</el-button>
          <el-button type="warning" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" @click="handleDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>

    </el-table>


    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径" >
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" >
          <el-select clearable v-model="form.icon" placeholder="请选择">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value">{{item.name}}</i>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="描述" >
          <el-input v-model="form.description" autocomplete="off"></el-input>
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
      pageSize: 8,
      username:"",
      dialogFormVisible:false,
      form:{},
      multipleSelection:[],
      options:[],
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/menu",{
        params:{
          name: this.name
        }
      }).
      then(res => {
        this.tableData = res
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
      request.post("/menu",this.form).then(res=>{
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
      request.get("/menu/icons").then(res=>{
        this.options = res
      })
    },
    handleDel(id){
      request.delete("/menu/"+id).then(res=>{
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
      request.delete("/menu/del/batch",{data:ids}).then(res=>{
        if (res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    exp(){
      window.open("http://localhost:9090/menu/export")
    },
    addChildren(pid){
      this.dialogFormVisible = true
      this.form = {}
      if (pid){
        this.form.pid = pid
      }
    }
  }
}
</script>

<style scoped>
.headerBg{
  background-color: #cccccc   !important;
}
</style>