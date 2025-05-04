<script setup lang="ts"></script>
<template>
  <header>
    <el-menu border:true mode="horizontal" :ellipsis="false" :router="true">
      <el-sub-menu index="">
        <template #title>文章</template>
        <el-menu-item index="/admin/list">总览</el-menu-item>
        <el-menu-item index="/admin/add">新增</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/admin/link">友联</el-menu-item>
      <el-menu-item index="/admin/cover">背景图</el-menu-item>
      <el-menu-item
        ><a href="http://120.27.202.144:8001/doc.html#/home" target="_blank"
          >接口文档</a
        ></el-menu-item
      >
    </el-menu>
  </header>
</template>

<style lang="scss" scoped>
header {
  width: 100vw;
  box-sizing: border-box;
  z-index: 999;
  top: 0;
  background-color: var(--color-like);
  display: flex;
  align-items: center;
  height: 45px;
  .el-menu {
    display: flex;
    justify-content: flex-end;
    background-color: var(--color-like);
    font-weight: bold;
    height: 45px;
  }
}
</style>
