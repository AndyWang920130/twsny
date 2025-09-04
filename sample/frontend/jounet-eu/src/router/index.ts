import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import HelloWorld from '@/components/HelloWorld.vue'
import UserProfile from '@/components/user/UserProfile.vue'
import ChangePassword from '@/components/user/ChangePassword.vue'

import { getWithExpiry } from '@/apis/localStorager'
import { LoginConfig } from '@/types/CommonD'
import UserSetting from '@/components/user/UserSetting.vue'
import LoginBack from '@/components/login/LoginBack.vue'

const customRoutes = [
     {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/loginback',
      name: 'loginback',
      component: LoginBack,
    },
    {
      path: '/home',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/hello',
      name: 'hello',
      component: HelloWorld,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
      permission: ["role_operationAdmin"]
    },
    {
      path: '/user/profile',
      name: 'userProfile',
      component: UserProfile,
    },
     {
      path: '/user/passwordChange',
      name: 'passwordChange',
      component: ChangePassword,
    },
    {
      path: '/user/userSetting',
      name: 'userSetting',
      component: UserSetting,
    },
  ]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: customRoutes,
})

// 用 localStorage 判断是否登录, 未登录则重定向
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!getWithExpiry(LoginConfig.localStorageKey) // 第一个 !：把值转换为布尔值并取反; 第二个 !：再取反一次，相当于只做了类型转换
  const publicPaths = ['/login', '/loginback'] // 允许匿名访问的路径
  // console.log(to.path)
  if (!publicPaths.includes(to.path) && !isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router
