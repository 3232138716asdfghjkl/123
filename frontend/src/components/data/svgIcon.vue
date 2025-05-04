<script setup lang="ts">
defineProps({
  // xlink:href 属性值的前缀
  prefix: {
    type: String,
    default: "#icon-",
  },
  // 提供使用的图标的名字
  name: String,
  color: {
    type: String,
    default: "",
  },
  size: {
    type: String,
    default: "16px",
  },
  hover: {
    type: Boolean,
    default: false,
  },
});

const themeS = useTheme();
</script>
<template>
  <!-- svg：图标外层容器节点，内部需要与 use 标签结合使用 -->
  <svg :style="{ width: size, height: size }" class="svg">
    <!-- xlink:href执行用哪一个图标，属性值务必#icon-图标名字 -->
    <!-- use 标签 fill 属性可以设置图标的颜色 -->
    <use
      :xlink:href="prefix + name"
      :fill="color == '' ? (themeS.isdark ? '#808080' : '') : color"
      :class="hover ? '' : 'icon'"
    ></use>
  </svg>
</template>

<style scoped lang="scss">
.svg {
  transform: translateY(10%);
  &:hover {
    .icon {
      fill: var(--color-like);
    }
  }
}
</style>
