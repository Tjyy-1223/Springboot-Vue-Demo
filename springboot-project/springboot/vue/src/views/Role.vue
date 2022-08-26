<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="input name" suffix-icon="el-icon-search" class="ml-5"></el-input>

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

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" ></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="description" label="描述" ></el-table-column>
      <el-table-column label="操作" width="300" align="center">
        <template slot-scope="scope">
          <el-button type="info" @click="selectMenu(scope.row.id)">分配菜单<i class="el-icon-menu"></i></el-button>
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
          :page-sizes="[8, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="120px">
        <el-form-item label="名称" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
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

    <el-dialog title="菜单分配" :visible.sync="menuDialogVis" width="30%" style="padding: 0 50px">
      <el-tree
          :props="props"
          :data="menuData"
          show-checkbox
          @check-change="handleCheckChange"
          node-key="id"
          :default-expanded-keys="expands"
          :default-checked-keys="checks"
          ref="tree"
      >
      </el-tree>


      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu()">确 定</el-button>
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
      name:"",
      dialogFormVisible:false,
      form:{},
      multipleSelection:[],
      menuDialogVis:false,
      menuData:[],
      props:{
        label:'name',
      },
      expands:[],
      checks:[],
      roleId:0
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/role/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).
      then(res => {
        this.tableData = res.records
        this.total = res.total
      })
    },
    reset(){
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
      request.post("/role",this.form).then(res=>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    saveRoleMenu(){
        request.post("/role/roleMenu/"+this.roleId,this.$refs.tree.getCheckedKeys()).then(res => {
            if (res){
              this.$message.success("绑定成功")
              this.menuDialogVis = false
            }else{
              this.$message.error("绑定失败")
            }
            this.menuDialogVis = false
        })
    },
    handleEdit(row){
      this.form = row
      this.dialogFormVisible = true
    },
    handleDel(id){
      request.delete("/role/"+id).then(res=>{
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
      request.delete("/role/del/batch",{data:ids}).then(res=>{
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
    },
    selectMenu(roleId){
        this.menuDialogVis = true
        this.roleId = roleId
        request.get("/menu",{params:{name: this.name}}).
        then(res => {
          this.menuData = res
          this.expands = this.menuData.map(v => v.id)
        })

      request.get("/role/roleMenu/"+roleId).
      then(res => {
        this.checks = res
      })
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },

    }
}
</script>

<style scoped>
.headerBg{
  background-color: #cccccc   !important;
}
</style>