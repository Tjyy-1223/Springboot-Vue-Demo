<template>
  <div class="wrapper">
    <div style="margin: 200px auto;background-color: #fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>注册界面</b></div>

      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item prop="comfirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password2"></el-input>
        </el-form-item>


        <el-form-item style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">注册</el-button>
          <el-button type="warning" size="small" autocomplete="off">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
export default {
  name: "Login",
  data(){
    return{
      user:{}
    }
  },
  methods:{
    login(){
        request.post("/user/login",this.user).then(res => {
          if(res.code === '200'){
            localStorage.setItem("user",JSON.stringify(res.data)) // 存储用户信息到浏览器
            this.$router.push("/")
            this.$message.success("登录成功")
          }else{
            this.$message.error(res.msg)
          }
        })
    }
  }
}
</script>

<style scoped>
  .wrapper{
    height: 100vh;
    background-image: linear-gradient(to bottom right,#FC4668,#3F5EFB);
    overflow: hidden;
  }
</style>