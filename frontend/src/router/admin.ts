import type { RouteRecordRaw } from 'vue-router'


const routes : Array<RouteRecordRaw> = [
  {
    path: '/admin',
    name: 'admin',
    component: () => import('@/layout/admin/index.vue'),
    redirect: '/admin/list',
    children:[
      {
        path: 'list',
        name: 'list',
        component: () => import('@/views/admin/article/list.vue'),
        meta: { title: '文章列表' }
      },
      {
        path: 'add',
        name: 'add',
        component: () => import('@/views/admin/article/add.vue'),
        meta: { title: '文章添加' }
      },
      {
        path: 'link',
        name: 'linkA',
        component: () => import('@/views/admin/link/index.vue'),
        meta: { title: '友链管理' }
      },
      {
        path: 'cover',
        name: 'cover',
        component: () => import('@/views/admin/cover/index.vue'),
        meta: { title: '背景图管理' }
      },
    ]
  }
]

 export default routes
