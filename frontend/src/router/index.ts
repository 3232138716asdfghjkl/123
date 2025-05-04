import {createRouter , createWebHistory} from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import user from "@/router/user"
import admin from "@/router/admin"
import {useLoad} from "@/stores/load"
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/index.css";


const routes : Array<RouteRecordRaw> = [
  {path:'/:pathMatch(.*)*',
    redirect: '/user/home'},
 ...user,
 ...admin
]

const router = createRouter({
  history:createWebHistory(import.meta.env.BASE_URL),
  routes
})



const show = () =>{
  const loadS = useLoad()
  loadS.show();
}
const isAdmin=async ()=>{
  const userS = useUser();
  await userS.info()
  return userS.item.username=='筑乐'
}
router.beforeEach(async (to, _from, next) => {
  show()
  window.document.title = to.meta.title as string
  if (to.path.startsWith('/admin') ) {
    if(await isAdmin()){
      next();
    }else{
      ElMessage.error("只有管理员才能进入")
      next('/');
    }
  }else{
    next();
  }
});




export default router
