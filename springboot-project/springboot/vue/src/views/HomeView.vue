<template>
  <div style="height: 100%">
    <el-container style="height: 100vh">

      <el-aside width="sideWidth + 'px'"
                style="
                background-color: rgb(238, 241, 246);
                height: 100%;
                box-shadow: 2px 0 6px rgb(0 21 41/35%);
                "
      >
        <el-menu :default-openeds="['1', '3']" style="min-height: 100%"
                  background-color="rgb(48,65,86)"
                 text-color="#fff"
                 overflow-x:hidden
                 active-text-color="#ffd04b"
                 :collapse-transition="false"
                 :collapse="isCollapse"
        >
          <div style="height: 60px;line-height: 60px;text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px" >
            <b style="color: aliceblue" v-show="logoTextShow">后台管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span>导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>

        <el-header style="font-size: 12px ; border: 1px solid;line-height: 60px ; display:flex">
          <div style="flex:1;font-size: 18px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>

          <el-dropdown style="width: 100px; cursor: pointer">
            <span>王小虎</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
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

          <el-table :data="tableData" border stripe header-cell-class-name="headerBg" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="100"></el-table-column>
            <el-table-column prop="nickname" label="昵称" width="100"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="eamil" label="邮箱"></el-table-column>
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

        </el-main>

      </el-container>
    </el-container>
  </div>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";

export default {
  name: 'HomeView',
  data(){
      const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
      };

      return {
        tableData: [],
        total: 0,
        pageNum: 1,
        pageSize: 2,
        username:"",
        msg: "hello tjyy",
        collapseBtnClass:'el-icon-s-fold',
        isCollapse:false,
        sideWidth: 200,
        logoTextShow:true,
        dialogFormVisible:false,
        form:{},
        multipleSelection:[]
      }
  },
  created() {
    // 请求分页查询数据
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
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse) {
        this.logoTextShow = false
        this.dialogFormVisible = false
      }else{
        this.logoTextShow = true
      }
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
    }
  }
}
</script>

<style>
.headerBg{
  background-color: #cccccc   !important;
}
</style>
