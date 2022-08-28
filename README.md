# Springboot-Vue Demo

#### 介绍
学习使用Springboot-Vue搭建后台管理系统 - demo

学习视频地址：https://www.bilibili.com/video/BV1U44y1W77D?p=1&vd_source=9a47b26e060b09ee3b6edc173c4809ea

Demo呈现效果（部分功能没有完善）

![image-20220828092520270](https://gitee.com/tianjiangyu/springboot-vue/blob/master/assets/image-20220828092520270.png)

```
安装依赖 npm install
启动服务 npm run serve
编译打包 npm run build
```



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





#### 8-9 Springboot实现分页查询

+ 可以使用MP的分页功能快速实现

+ 与前端vue分页功能的交互实现
+ 解决Vue-fetch时8080端口和9090端口间的跨域问题
+ Vue前端获取数据 与 页面展示数据的 v-model绑定
+ username搜索按钮实现





#### 10 Vue实现增删改查

+ 封装axios对后台进行数据请求

+ 配置request.js文件
+ 新增按钮功能实现
+ 编辑和删除按钮功能实现
+ 批量删除按钮功能实现(删除弹出框的使用)





#### 11 Springboot MP代码生成器

+ 引入pom的配置文件

```
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.5.1</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>

<dependency>
    <groupId>org.apache.velocity</groupId>
    <artifactId>velocity-engine-core</artifactId>
    <version>2.0</version>
</dependency>
```

+ 编写utils.CodeGenerator.java进行配置
+ 注意备份后再使用自动生成
+ 自定义Controller生成规范：使用controller.java.vm文件





#### 12 Vue使用路由（切换栏的实现）

+ 更改主页面为Manager.vue，作为整体的管理界面
+ 更改router中的index.js配置

+ vue界面的拆分、引入，分模块化设计
+ vuex实现动态路径配置

```
1）配置 store/index.js
2）配置 main.js
3）配置 router/index.js 路由守卫
4）header.vue中进行使用
```





#### 13 Springboot+Vue实现导入导出

+ 实现将数据导入excel表或者从excel表加载数据
+ 引入pom文件：hotool和poi-ooxml
+ 编写UserController.java文件实现导入导出
+ 导入导出 vue按钮与后端java功能进行绑定





#### 14 Springboot和Vue实现用户登录

+ 编写login.vue登录界面并配置router.index接口
+ 登录方法login的后端代码编写开发
+ 登录按钮的绑定，以及验证成功的页面跳转

```
request.post("/user/login",this.user)
```

+ 退出按钮进行绑定

```
<router-link to="/login" style="text-decoration: none">退出</router-link>
```





#### 15 Springboot和Vue实现注册和异常处理功能

+ 构建common.Result统一封装返回数据格式
+ 设计自定义异常 - 使用全局异常处理器 处理自己抛出的自定义异常
+ 实现注册功能 - 新增一个注册界面用来注册（配置路由跳转）
+ 编写后端register相关代码 完成注册功能(注册功能跳过 与之前的做法相同)

+ 个人信息借口编写（跳过，与之前步骤相同）





#### 16 Springboot集成JWT（JSON WEB TOKEN）

+ 学习session原理和JWT原理

Json web token (JWT), 是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准。该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景，**可以在有效期间内一直登录**。

优点：

+ 简洁，数据量小，传输速度快
+ 自包含：负载中可以包含用户需要的信息，避免了多次查询数据库
+ 因为以JSON加密保存在客户端，任何语言都可以支持
+ 不需要在服务端保存会话信息，特别适合分布式微服务

实现步骤：

+ 导入pom配置

```
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.10.0</version>
</dependency>
```

+ utils/TokenUtils中编写生成token的相关代码
+ login的登录返回时 - 将UserDto的token返回给客户端
+ request.js中配置token

```
config.headers['token'] = user.token;  // 设置请求头
```

+ 设计拦截器 interceptor/JwInterceptor
+ 配置config/InterceptorConfig

如果直接new JwInterceptor()，则JwInterceptor不是有JAVA Bean进行管理，不能对其中的Bean对象进行自动注入。需要使用如下方法：

```
		@Bean
    public JwInterceptor jwtInterceptor(){
        return new JwInterceptor();
    }
```

+ 在request.js的response拦截器中接口响应后统一处理结果
+ TokenUtils.java文件中 - 展示如何在后端代码中获取jwt中的token数据

#### 



#### 17 Springboot文件上传

+ FileController - 编写文件上传相关接口
+ 设计新的文件存储实体类MyFile，和相对应的数据库表sys_file
+ 编写上传文件接口
+ 编写下载文件接口（md5防止文件重复存储）
+ 编写File.vue接口，用于上传文件 (切换栏新增文件管理功能)
+ 编写Controller中其他功能接口



#### 18-19 vue集成Echarts的使用方式 

需要时来看视频学习一点



#### 20 权限部分讲解 上

+ 设计sys_role数据库表，并使用代码生成器生成代码
+ 设计sys_menu数据库表，并使用代码生成器生成代码

+ 前端界面：分配菜单和选中菜单（树形）
+ 前端界面：将菜单页面 - 转变成树形表格设计

+ vue + springboot ： 前后端 菜单管理中 父菜单与子菜单的相互交互





#### 21 权限部分讲解 中

+ 设计sys_dict数据库表并生成相应的代码
+ 菜单管理页面 - 图标栏的正确展示图标

+ 设计数据库表role_menu ： 用于链接用户和菜单管理的的相关性

+ 后段代码编写：插入用户和菜单的选择关系





#### 22 权限部分讲解 下（skip）

+ 不同权限用户的不同动态路由，只显示可以访问到的相对应的页面

+ 只有课程设计才会用到的部分（先跳过）





#### 23 权限部分讲解 续（skip）

+ 调整bug
+ 关于重置路由的讲解



#### 24-25 服务器安装部署教程

+ 如何将项目部署在阿里云上 centos7
+ 有了自己的项目再看



#### 26 Springboot实现1对1、1对多、多对多关联



#### 27 用户前台页面设计与实现（从0搭建一个网页框架）

+ 如何对一个空白页面开始布局 - demo演示
+ 菜单栏切换的简单设计



#### 28 Springboot集成Redis



#### 29 高德地图集成显示



#### 30 Vue集成视频播放显示



#### 31 集成MarkDown和多级评论
