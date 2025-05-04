<script setup lang="ts">
defineProps<{
  two?: boolean;
  title?: string;
}>();
const themeS = useTheme();
const bg = computed(() => (themeS.isdark ? "dark" : "light"));
const current = ref(0);
const style = { height: "40px", width: "40px" };
const top = () => {
  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
};
const botton = () => {
  const total = document.documentElement.scrollHeight - window.innerHeight;
  window.scrollTo({
    top: total,
    behavior: "smooth",
  });
};

const scrollPercentage = computed(() => {
  const total = document.documentElement.scrollHeight - window.innerHeight;
  return Math.round((current.value / total) * 100) || 0;
});

const throttledFn = useThrottleFn(() => {
  current.value = window.scrollY;
}, 50);
onMounted(() => {
  window.addEventListener("scroll", throttledFn);
});
onBeforeUnmount(() => {
  window.removeEventListener("scroll", throttledFn);
});
</script>

<template>
  <div style="width: 100%">
    <div class="layout" :class="bg">
      <div style="width: 77%; display: flex" v-if="two">
        <div class="main">
          <slot name="main"></slot>
        </div>
        <div class="sidebar">
          <slot name="sidebar"></slot>
        </div>
      </div>
      <div style="width: 70%" v-else>
        <div class="banner">
          <h2 class="title">
            {{ title }}
          </h2>
        </div>
        <card>
          <slot></slot>
        </card>
      </div>
    </div>
    <div style="position: fixed; right: 30px; bottom: 60px">
      <el button small @click="top()" :Style="style"><svgIcon name="上" /></el>
      <el button small style="margin: 10px 0" :Style="style"
        >{{ scrollPercentage }}
      </el>
      <el button small @click="botton()" :Style="style"
        ><svgIcon name="下"
      /></el>
    </div>
  </div>
</template>

<style scoped lang="scss">
.dark {
  background-image: url("../../assets/image/莫娜.jpg");
}
.light {
  background-image: url("../../assets/image/蓝天.jpg");
}
.layout {
  display: flex;
  justify-content: center;
  padding-bottom: 20px;
  background-attachment: fixed;
  background-size: cover;
  background-position: center;
  width: 100%;
  transition: $transition;
  .main {
    width: 75%;
    /*  @media screen and (max-width: 1000px) {
    width: 100%;
  } */
  }
  .sidebar {
    width: 23%;
    margin-left: auto;
    /*  @media screen and (max-width: 1000px) {
    display: none;
  } */
  }
  .banner {
    @extend center;
    height: 50vh;
    width: 100%;
    .title {
      color: white;
      font-size: 4.6rem;
      font-weight: normal;
      text-shadow: 0 1px 0 hsl(174, 5%, 80%), 0 2px 0 hsl(174, 5%, 75%),
        0 3px 0 hsl(174, 5%, 70%), 0 4px 0 hsl(174, 5%, 66%),
        0 5px 0 hsl(174, 5%, 64%), 0 6px 0 hsl(174, 5%, 62%),
        0 7px 0 hsl(174, 5%, 61%), 0 8px 0 hsl(174, 5%, 60%),
        0 0 5px rgba(0, 0, 0, 0.05), 0 1px 3px rgba(0, 0, 0, 0.2),
        0 3px 5px rgba(0, 0, 0, 0.2), 0 5px 10px rgba(0, 0, 0, 0.2),
        0 10px 10px rgba(0, 0, 0, 0.2), 0 20px 20px rgba(0, 0, 0, 0.3);
    }
  }
}
</style>
