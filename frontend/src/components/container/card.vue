<script setup lang="ts"></script>
<template>
  <div class="card">
    <slot></slot>
  </div>
</template>

<style scoped lang="scss">
.card {
  background: var(--color-card);
  border-radius: $border-radius;
  width: 100%;
  margin: $margin 0;
  box-shadow: var(--box-shadow);
  transition: $transition;
}
</style>
