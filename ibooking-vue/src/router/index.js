
import Vue from 'vue'
import VueRouter from 'vue-router'
//3、使用路由插件
// 在vue中，使用使用vue的插件，都需要调用Vue.use()
Vue.use(VueRouter)
//4、创建路由规则数组
const routes = [
  {
    path: "/",
    name: "home",
    component: () => import('@/components/MyLogin.vue')
  },
  {
    path: "/layout",
    name: "layout",
    component: () => import('@/components/layout-page.vue')
  },
  // 登录
  {
    path: "/login",
    name: "login",
    component: () => import('@/components/MyLogin.vue')
  },
  // 注册
  {
    path: "/register",
    name: "register",
    component: () => import('@/components/MyRegister.vue')
  },
  // 预约管理
   {
    path: "/task",
    name: "task",
    component: () => import('@/views/user-management.vue')
  },
  // 预约信息
  {
    path: "/account",
    name: "account",
    component: () => import('@/views/account-management.vue')
  },
  // 退预约管理
  {
    path: "/unbooking",
    name: "unbooking",
    component: () => import('@/views/unbooking-management.vue')
  },
  // 管理员管理自习室
  {
    path: "/roommanage",
    name: "roommanage",
    component: () => import('@/views/manager-roommanage.vue')
  },

  // 404 页面
  {
    path: '*',
    name: '404',
    component: resolve => require(['@/views/404-page.vue'], resolve),
},
]
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location){
  return originalPush.call(this,location).catch(err =>err)
}
//5、创建路由对象 -  传入规则
const router = new VueRouter({
  routes
})
export default router
