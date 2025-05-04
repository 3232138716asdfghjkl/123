<script setup lang="ts"></script>
<template>
  <card style="height: 300px; position: relative">
    <div
      style="height: 210px; width: 100%; display: flex; justify-content: center"
    >
      <div class="card__img">
        <img src="../../assets/image/你的名字.jpg" />
      </div>
      <div class="card__avatar">
        <img src="../../assets/image/xiubi.jpg" />
      </div>
    </div>
    <div class="center column">
      <div class="card__title">筑乐</div>
      <div class="card__subtitle">正在路上</div>
    </div>
  </card>
</template>

<style scoped lang="scss">
.card__img {
  position: absolute;
  top: 0;
  height: 50%;
  width: 100%;
  img {
    border-radius: $border-radius $border-radius 0 0;
    width: 100%;
    height: 100%;
  }
}

.card__avatar {
  position: absolute;
  width: 114px;
  height: 114px;
  background: var(--color-card);
  border-radius: 100%;
  transition: $transition;
  @extend center;
  top: calc(50% - 57px);

  img {
    width: 90%;
    height: 90%;
    border-radius: 50%;
    transition: transform 0.5s;
    &:hover {
      transform: rotate(360deg);
    }
  }
}

.card__title {
  font-weight: 500;
  font-size: 25px;
}

.card__subtitle {
  margin-top: 10px;
  font-weight: 400;
  font-size: 15px;
}
</style>
