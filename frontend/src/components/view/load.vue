<script setup lang="ts"></script>

<template>
  <div class="contain">
    <div class="loader"></div>
  </div>
</template>
<style scoped lang="scss">
.contain {
  height: 100vh;
  width: 100vw;
  background: var(--color-background);
  @extend center;
  .loader {
    position: relative;
    width: 120px;
    height: 90px;
    margin: 0 auto;
    &:before {
      content: "";
      position: absolute;
      bottom: 30px;
      left: 50px;
      height: 30px;
      width: 30px;
      border-radius: 50%;
      background: #2a9d8f;
      animation: loading-bounce 0.5s ease-in-out infinite alternate;
    }
    &:after {
      content: "";
      position: absolute;
      right: 0;
      top: 0;
      height: 7px;
      width: 45px;
      border-radius: 4px;
      animation: loading-step 1s ease-in-out infinite;
    }

    @keyframes loading-bounce {
      0% {
        transform: scale(1, 0.7);
      }

      40% {
        transform: scale(0.8, 1.2);
      }

      60% {
        transform: scale(1, 1);
      }

      100% {
        bottom: 140px;
      }
    }

    @keyframes loading-step {
      0% {
        box-shadow: 0 10px 0 var(--color-like), 0 10px 0 var(--color-like),
          -35px 50px 0 var(--color-like), -70px 90px 0 var(--color-like);
      }

      100% {
        box-shadow: 0 10px 0 var(--color-like), -35px 50px 0 var(--color-like),
          -70px 90px 0 var(--color-like), -70px 90px 0 var(--color-like);
      }
    }
  }
}
</style>
