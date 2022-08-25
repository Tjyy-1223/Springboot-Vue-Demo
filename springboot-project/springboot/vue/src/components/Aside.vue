<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%" background-color="rgb(48,65,86)"
           text-color="#fff" overflow-x:hidden active-text-color="#ffd04b"
           :collapse-transition="false" :collapse="isCollapse" router>

    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px" >
      <b style="color: aliceblue" v-show="logoTextShow">后台管理系统</b>
    </div>

    <el-menu-item index="/home">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span>主页</span>
      </template>
    </el-menu-item>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span>系统管理</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="/file">
        <i class="el-icon-document"></i>
        <span slot="title">文件管理</span>
      </el-menu-item>
<!--      <el-menu-item-group>-->
<!--        <template slot="title">分组一</template>-->
<!--        <el-menu-item index="2-2">选项2</el-menu-item>-->
<!--      </el-menu-item-group>-->
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse:Boolean,
    logoTextShow:Boolean
  }
}
</script>

<style scoped>

</style>