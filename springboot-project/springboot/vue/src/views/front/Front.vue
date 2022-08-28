<template>
  <div>
    <div style="display: flex;height: 60px;line-height: 60px;border-bottom: 1px solid #ccc">
      <div style="width: 200px">欢迎来到xx系统</div>
      <div style="flex:1">
        <ul style="list-style: none">
          <li class="item"><a href="/login">菜单 1</a></li>
          <li class="item">菜单 2</li>
          <li class="item">菜单 3</li>
          <li class="item">菜单 4</li>
        </ul>
      </div>
      <div style="width: 200px">
        头像拦
      </div>
    </div>

    <div style="width: 1000px;margin: 0 auto">
      <router-view></router-view>
    </div>

  </div>
</template>

<script>
export default {
  name: "Front"
}
</script>

<style scoped>
.item{
  display: inline-block;
  width: 100px;
}
.item:hover{
  background-color: #FC4668;
}
</style>