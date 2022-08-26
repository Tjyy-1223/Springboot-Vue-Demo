import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manager.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manager'),
    redirect:"/home",
    children:[
      {path: 'user', name: 'User', component: () => import('../views/User.vue'),},
      {path: 'role', name: '角色管理', component: () => import('../views/Role.vue'),},
      {path: 'menu', name: '菜单管理', component: () => import('../views/Menu.vue'),},
      {path: 'home', name: 'Home', component: () => import('../views/Home.vue'),},
      {path: 'file', name: '文件管理', component: () => import('../views/File.vue'),}
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path:'/login',
    name:'Login',
    component:() => import('../views/Login.vue')
  },
  {
    path:'/register',
    name:'Register',
    component:() => import('../views/Register')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  localStorage.setItem("currentPathName",to.name) //设置当前路由名称
  store.commit("setPath") //触发store的数据更新
  next()  //放行路由
})

export default router
