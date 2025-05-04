<script setup lang="ts">
const tagS = useTag();
const categoryListS = useCategoryList();
const userS = useUser();
const themeS = useTheme();
const loadS = useLoad();
onMounted(() => {
  tagS.init();
  categoryListS.init();
  userS.info();
  document.documentElement.classList.toggle("dark", themeS.isdark);
});
</script>
<template>
  <RouterView v-show="!loadS.isLoading" />
  <load v-show="loadS.isLoading" />
</template>

<style scoped></style>
