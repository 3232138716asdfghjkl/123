<script setup lang="ts">
const themeS = useTheme();
defineProps<{
  tag?: boolean;
  button?: boolean;
  link?: boolean;
  small?: boolean;
  Style?: Record<string, string | number>;
}>();
</script>
<template>
  <div>
    <el-tag :type="themeS.isdark ? 'info' : 'primary'" v-if="tag">
      <slot></slot
    ></el-tag>

    <el-button
      :color="themeS.isdark ? '#2f2f2f' : '#3b82f6'"
      :size="small ? 'default' : 'large'"
      :style="Style"
      v-if="button"
    >
      <slot></slot
    ></el-button>
    <el-link style="font-size: 15px" :underline="false" v-if="link">
      <slot></slot
    ></el-link>
  </div>
</template>

<style scoped lang="scss">
.el-tag {
  transition: $transition;
}
</style>
