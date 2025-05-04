<script setup lang="ts">
import Header from "@/layout/admin/Header.vue";
</script>
<template>
  <div style="height: 100vh; display: flex; flex-direction: column">
    <Header></Header>
    <RouterView style="flex-grow: 1" />
  </div>
</template>

<style lang="scss" scoped>
.mmain {
  width: 80%;
  @media screen and (max-width: 1000px) {
    width: 100%;
  }
}
.sidebar {
  width: 20%;
  @media screen and (max-width: 1000px) {
    display: none;
  }
}
</style>
