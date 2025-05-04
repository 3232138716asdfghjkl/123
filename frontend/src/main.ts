
import App from "./App.vue";
import router from "./router";
import { createPinia } from 'pinia'
import "@/styles/reset.scss";
import "@/styles/theme.scss";
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'virtual:svg-icons-register'
import animate from "./directive/animate";
import lazy from "@/directive/lazy"


const pinia = createPinia()
createApp(App).use(router).use(pinia)
.directive('animate', animate)
.directive('lazy', lazy)
.mount("#app");
