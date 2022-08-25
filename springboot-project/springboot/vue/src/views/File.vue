<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px"  placeholder="input name" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
      <el-input style="width: 200px"  placeholder="input email" suffix-icon="el-icon-message" class="ml-5"></el-input>
      <el-input style="width: 200px"  placeholder="input address" suffix-icon="el-icon-position" class="ml-5"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
    </div>


    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload"  :show-file-list="false"
                 :on-success="handleFileUploadSuccess" style="display: inline-block" :headers="headerObj">
        <el-button type="primary">上传文件<i class="el-icon-circle-plus-outline"></i></el-button>
      </el-upload>


      <el-popconfirm class="ml-5" confirm-button-text="确定" cancel-button-text="我再想想"
                     icon="el-icon-info" icon-color="red" title="您确定删除吗" @confirm="delBatch"
      >
        <el-button type="primary" slot="reference">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

    </div>

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称" width="100"></el-table-column>
      <el-table-column prop="type" label="文件类型" width="100"></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"></el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">点击下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
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

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "File",
  data(){
    return {
      tableData:[],
      name:'',
      mutilplySelection:[],
      total: 0,
      pageNum: 1,
      pageSize: 8,
      headerObj:{
        Authorization: window.localStorage.getItem("token")
      }
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/file/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).
      then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset(){
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
    save(){
      request.post("/file/upload").then(res=>{
        if (res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleDel(id){
      request.delete("/file/"+id).then(res=>{
        if (res.code === '200'){
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
      request.delete("/file/del/batch",{data:ids}).then(res=>{
        if (res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleFileUploadSuccess(res){
      console.log(res)
    },
    download(url){
      window.open(url)
    },
    changeEnable(row){
      request.post("/file/update",row).then(res=>{
        if (res.code === '200'){
          this.$message.success("操作成功");
        }
      })
    }
  }
}
</script>

<style scoped>

</style>