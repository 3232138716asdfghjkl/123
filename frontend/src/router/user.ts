
import type { RouteRecordRaw } from 'vue-router'


const routes : Array<RouteRecordRaw> = [
  {
    path: '/user',
    name: 'user',
    component: () => import('@/layout/user/index.vue'),
    children:[
      {
        path: 'home',
        name: 'home',
        component: () => import('@/views/user/Home/index.vue'),
        meta: { title: '首页' }
      },

      {
        path: 'article/:id',
        name: 'article',
        component: () => import('@/views/user/article/index.vue')

      },
      {
        path: ':type/:id',
        name: 'categoryTag',
        component: () => import('@/views/user/categoryTag/index.vue'),
        meta: { title: '分类与标签' }
      },
      {
        path: 'archive/article',
        name: 'ararticle',
        component: () => import('@/views/user/archive/index.vue'),
        meta: { title: '文章归档' }

      },
      {
        path: 'link',
        name: 'linkU',
        component: () => import('@/views/user/link/index.vue'),
        meta: { title: '友链' }
      },
      {
        path: 'message',
        name: 'message',
        component: () => import('@/views/user/message/index.vue'),
        meta: { title: '留言' }
      },
    ]
  },
  {
    path: '/welcome',
    name: 'welcome',
    component: () => import('@/views/user/welcome/index.vue'),
    redirect: '/welcome/login',
    children:[
      {
        path: 'login',
        name: 'login',
        component: () => import('@/views/user/welcome/login.vue'),
        meta: { title: '登录' }

      },
      {
        path: 'register',
        name: 'register',
        component: () => import('@/views/user/welcome/register.vue'),
        meta: { title: '注册' }
      },
      {
        path: 'reset',
        name: 'reset',
        component: () => import('@/views/user/welcome/reset.vue'),
        meta: { title: '重置密码' }
      },
      {
        path: 'token',
        name: 'token',
        component: { template: '<div></div>' },
        beforeEnter: (to,_from,next) => {
          const token = to.query.token as string;
          if (token) {
            localStorage.setItem('token', token);
            const userS = useUser();
           userS.info()
          }
          next('/');
        }
      }
    ]
  }
]

 export default routes
