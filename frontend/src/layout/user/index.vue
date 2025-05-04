<script setup lang="ts">
/* import { loadOml2d } from "oh-my-live2d"; */
import Header from "../user/Header/Header.vue";
import Footer from "../user/Footer.vue";

/* loadOml2d({
  dockedPosition: "right",
  initialStatus: "sleep",
  menus: {
    disable: false,
    items: [],
  },
}); */
</script>
<template>
  <div class="body">
    <Header></Header>
    <RouterView />
    <Footer></Footer>
  </div>
</template>

<style scoped>
.body {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
