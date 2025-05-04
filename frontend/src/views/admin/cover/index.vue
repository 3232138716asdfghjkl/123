<script setup lang="ts">
const coverS = reactive(new Cover());
onMounted(() => {
  coverS.init();
});
</script>

<template>
  <div class="file">
    <FileUpload
      title="上传文件"
      image
      @confirm="
        (file) => {
          coverS.add(file);
        }
      "
    ></FileUpload>
  </div>
  <div class="gallery">
    <card v-for="item in coverS.list" style="width: 360px; overflow: hidden">
      <img :src="item.url" class="image" />
      <div style="display: flex">
        <el button @click="coverS.update(item)" class="button">{{
          item.state == 1 ? "隐藏" : "展示"
        }}</el>
        <el button @click="coverS.del(item.id)" class="button">删除</el>
      </div>
    </card>
  </div>
</template>

<style lang="scss" scoped>
.file {
  @extend center;
  width: 100%;
  height: 100px;
}
.gallery {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  .image {
    width: 360px;
    height: 180px;
    object-fit: cover;
  }
  .button {
    margin: 0 auto;
  }
}
</style>
