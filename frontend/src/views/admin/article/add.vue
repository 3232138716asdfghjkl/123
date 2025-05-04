<script setup lang="ts">
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";

import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/index.css";
const { copy } = useClipboard();
const fileS = reactive(new FIle());
const tagS = useTag();
const categoryListS = useCategoryList();
const articleS = reactive(new Article());
const route = useRoute();
const router = useRouter();
const themeS = useTheme();
onMounted(async () => {
  const articleId = Number(route.query.id);
  if (!isNaN(articleId)) {
    await articleS.init(articleId);
    fileS.init(articleId);
  }
});

const fileUpload = async (f: globalThis.File) => {
  if (f == undefined) {
    ElMessage.error("没有选择文件");
    return;
  }
  await fileS.upload(f, articleS.item.id);
  if (["image/jpeg", "image/png", "image/gif", "image/webp"].includes(f.type)) {
    console.log("识别");
    copy("<img src='" + fileS.filename + "' style='height:200px' >");
  } else if (f.type == "video/mp4") {
    copy(
      "<video width='100%' height='300px' controls><source src='" +
        fileS.filename +
        "'  type='video/mp4'></video>"
    );
  } else {
    copy(
      "<audio controls><source src='" +
        fileS.filename +
        "' type='audio/aac'></audio>"
    );
  }
};

const tablesubmit = async () => {
  articleS.item.top = articleS.item.top == true ? 1 : 0;
  if (articleS.item.id == undefined) {
    if (fileS.file) {
      await fileS.upload();
      articleS.item.image = fileS.filename;
    }
    if (!articleS.item.image.trim() || !articleS.item.title.trim()) {
      ElMessage.error("封面/标题待完善");
      return;
    }
    await articleS.add();
    router.back();
  } else {
    if (fileS.file && articleS.item.image) {
      fileS.del(articleS.item.image);
      await fileS.upload();
      articleS.item.image = fileS.filename;
    }
    articleS.update();
  }
  ElMessage.success("保存成功");
};
const tagClose = (tagId: number) => {
  articleS.item.tags = articleS.item.tags!.filter(
    (obj: number) => obj !== tagId
  );
};
</script>
<template>
  <div class="column">
    <div style="display: flex; height: 40px; width: 100%">
      <el-input
        v-model="articleS.item.title"
        placeholder="标题"
        clearable
        style="flex-grow: 1"
      />
      <FileUpload
        title="上传封面"
        image
        style="flex-grow: 1"
        @confirm="
          (f) => {
            fileS.file = f;
          }
        "
      ></FileUpload>
      <el-select
        v-if="articleS.item.id != undefined"
        v-model="fileS.filename"
        placeholder="删除文件"
        size="large"
      >
        <el-option
          v-for="item in fileS.list"
          :label="item.filename"
          :value="item.filename"
        />
      </el-select>
      <el
        button
        style="height: 100%; flex-grow: 1"
        v-if="articleS.item.id != undefined"
        @click="fileS.del(), fileS.init(articleS.item.id!)"
        >删除</el
      >
      <el-select
        v-model="articleS.item.categoryId"
        placeholder="选择分类"
        size="large"
      >
        <el-option
          v-for="item in categoryListS.list"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      <el-select
        v-if="articleS.item.id"
        v-model="articleS.item.tags"
        placeholder="选择标签"
        multiple
        size="large"
      >
        <el-option
          v-for="item in tagS.list"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
        <template #tag>
          <el-tag
            v-for="item in articleS.item.tags"
            :key="item"
            closable
            @close="tagClose(item)"
          >
            {{ tagS.map.get(item) }}
          </el-tag>
        </template>
      </el-select>
      <el-select
        v-model="articleS.item.state"
        placeholder="选择状态"
        size="large"
      >
        <el-option
          v-for="item in 2"
          :label="item - 1 == 0 ? '草稿' : '发布'"
          :value="item - 1"
        />
      </el-select>
      <div style="width: 100px; flex-shrink: 0" class="center">
        <div style="text-align: center">置顶:</div>
        <el-switch v-model="articleS.item.top" />
      </div>
      <FileUpload
        title="上传文件"
        @confirm="fileUpload"
        style="flex-grow: 1"
        v-if="articleS.item.id"
      >
      </FileUpload>
      <el button @click="tablesubmit()" style="height: 100%; flex-grow: 1"
        >保存</el
      >
    </div>

    <MdEditor
      v-model="articleS.item.content"
      style="flex-grow: 1"
      :theme="themeS.isdark ? 'dark' : 'light'"
    />
  </div>
</template>

<style scoped lang="scss">
.el-select {
  flex-grow: 1;
}
</style>
