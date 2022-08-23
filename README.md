# Springboot-Vue

#### 介绍
Springboot-Vue搭建后台管理系统



#### 1 创建项目并运行

```
保证有node 和 vue-cli

vue create vue 
cd vue
npm run serve 
```





#### 2 使用ElementUI快速搭建

+ assets目录下配置global.css全局样式
+ main.js中引入global.css全局样式
+ 设置全局stype height=100%
+ 逐层构建整体布局（熟悉模块规划）





#### 3 Vue整体布局完善

+ 调整目录高度为100%  el-container	 style="height: 100vh"
+ 更换menu颜色： el-menu    background-color="rgb(48,65,86)"
+ 增加menu字体颜色和高亮设置
+ 完善菜单栏的收缩功能和展开功能(根据ElementUI官网进行修改)

+ 增加菜单栏标题以及图表 - 后台管理系统，并绑定vue v-show

+ 增加边栏的阴影效果





#### 4 Vue主体布局完善

+ 添加分页管理项
+ 添加搜索框以及其搜索按钮
+ 新增、批量删除、导入、导出按钮设计
+ 编辑和删除按钮设计





#### 5 Springboot框架搭建

+ 搭建springboot项目
+ 配置相关pom文件和yml配置文件
+ vue与springboot同时启动





#### 6 SpringBoot集成Mybatis实现数据查询

+ 创建数据库表user2
+ 创建对应user2的实体类 User
+ 构建对应的Mapper、Service等实现代码
+ 完成Mybatis数据查询功能





#### 7 Springboot使用增删改查

使用Mybatis Plus可以大幅度简化代码量

+ postman工具的使用
+ 使用mybatis注解完成增删改查
+ 扫描mapper xml文件





#### 8 Springboot实现分页查询

+ 可以使用MP的分页功能快速实现

+ 与前端vue分页功能的交互实现
+ 解决Vue-fetch时8080端口和9090端口间的跨域问题
+ Vue前端获取数据 与 页面展示数据的 v-model绑定
+ username搜索按钮实现



