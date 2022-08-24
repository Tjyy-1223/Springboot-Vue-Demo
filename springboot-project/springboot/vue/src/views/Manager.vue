<template>
  <div style="height: 100%">
    <el-container style="height: 100vh">

      <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);height: 100%;box-shadow: 2px 0 6px rgb(0 21 41/35%);">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
      </el-aside>

      <el-container>

        <el-header style="border: 1px solid">
            <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <el-main>
<!--           表示当前页面的子路由会在router-view中展示-->
            <router-view></router-view>
        </el-main>

      </el-container>
    </el-container>
  </div>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  data(){
      const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
      };

      return {
        msg: "hello tjyy",
        collapseBtnClass:'el-icon-s-fold',
        isCollapse:false,
        sideWidth: 200,
        logoTextShow:true,
      }
  },
  created() {
    // 请求分页查询数据
    // this.load()
  },
  components: {
    Aside,
    Header
  },
  methods:{
    collapse(){
      this.isCollapse = !this.isCollapse
      if(this.isCollapse) {
        this.logoTextShow = false
        this.dialogFormVisible = false
        this.sideWidth = 60
      }else{
        this.logoTextShow = true
        this.sideWidth = 200
      }
    }
  }
}
</script>

<style>

</style>
