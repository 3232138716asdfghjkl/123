<script setup lang="ts">
import { Files, ChatLineSquare, Link, House } from "@element-plus/icons-vue";
import toggle from "./toggle.vue";
import "animate.css";

const userS = useUser();
const isHeader = ref(true);
addEventListener("wheel", (event) => {
  if (event.deltaY > 0) {
    isHeader.value = false;
  } else if (event.deltaY < 0) {
    isHeader.value = true;
  }
});
</script>
<template>
  <transition
    enter-active-class="animate__animated animate__fadeInDown"
    leave-active-class="animate__animated animate__fadeOutUp"
  >
    <header v-show="isHeader">
      <router-link to="/" style="text-decoration: none"
        ><div class="name">筑乐</div>
      </router-link>

      <el-menu style="border: none" mode="horizontal" :ellipsis="false" router>
        <el-menu-item index="/user/home"
          ><el-icon><House /></el-icon>首页</el-menu-item
        >
        <el-menu-item index="/user/archive/article"
          ><el-icon><Files /></el-icon>归档</el-menu-item
        >

        <el-menu-item index="/user/link">
          <el-icon><Link /></el-icon>友链</el-menu-item
        >
        <el-menu-item index="/user/message">
          <el-icon><ChatLineSquare /></el-icon>留言</el-menu-item
        >
      </el-menu>
      <div
        style="
          height: 100%;
          margin-left: auto;
          margin-right: 1.5rem;
          display: flex;
          align-items: center;
        "
      >
        <toggle style="margin-right: 1rem"></toggle>

        <el-dropdown
          v-if="userS.item.id"
          :hide-on-click="false"
          trigger="click"
        >
          <el-avatar
            v-if="userS.item.avatar"
            fit="cover"
            :src="userS.item.avatar"
          />
          <img
            src="../../../assets/image/默认头像.png"
            style="height: 40px"
            v-else
          />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item
                ><fileUpload
                  title="修改头像"
                  @confirm="userS.avatar"
                ></fileUpload>
              </el-dropdown-item>
              <el-dropdown-item @click="userS.clear()"
                ><el button>退出登录</el>
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <router-link to="/welcome/login" v-else
          ><el-avatar>登录</el-avatar></router-link
        >
      </div>
    </header>
  </transition>
</template>

<style lang="scss" scoped>
header {
  display: flex;
  align-items: center;
  height: 45px;
  box-sizing: border-box;
  position: fixed;
  top: 0;
  z-index: 999;
  width: 100vw;
  padding: 0;
  .name {
    font-size: 30px;
    text-align: left;
    margin: 20px;
    font-weight: 500;
    transition: $transition;
    color: var(--text-color);
    &:hover {
      color: $sky-blue;
    }
  }
  .el-menu {
    display: flex;
    justify-content: flex-end;
    font-weight: bold;
    background-color: rgba(125, 142, 159, 0);
    height: 45px;
    /*  @media screen and (max-width: 910px) {
      display: none;
    } */
    .el-menu-item {
      right: 0;
      margin: 0;
      font-size: 15px;
    }
  }
}
</style>
