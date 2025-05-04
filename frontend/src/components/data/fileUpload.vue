<script setup lang="ts">
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/index.css";
const props = defineProps<{
  title: string;
  image?: boolean;
}>();
const emit = defineEmits(["confirm"]);

const reader = new FileReader();
const dialogVisible = ref(false);
const isShow = ref(true);
const file = ref<globalThis.File>();
const inputClick = ref<HTMLInputElement>();
const inputPaste = ref<HTMLInputElement>();
const image = ref<string>();
const imageType = ["image/jpeg", "image/webp", "image/png", "image/gif"];
let allowe = [
  ...imageType,
  "video/mp4",
  "audio/mpeg",
  "audio/ogg",
  "audio/aac",
  "audio/vnd.dlna.adts",
];

reader.onload = (e) => {
  image.value = e.target!.result as string;
};

const drop = (e: DragEvent) => {
  e.preventDefault();
  fileHandle(e.dataTransfer?.files[0]!);
};
const paste = (e: ClipboardEvent) => {
  const items = e.clipboardData!.items;
  for (let i = 0; i < items.length; i++) {
    const item = items[i];
    if (item.kind == "file") {
      const file = item.getAsFile();
      fileHandle(file!);
      break;
    }
  }
};
const fileHandle = (f: globalThis.File) => {
  console.log(f);
  if (props.image) {
    allowe = imageType;
  }
  if (allowe.includes(f.type)) {
    file.value = f;
    if (imageType.includes(f.type)) {
      reader.readAsDataURL(file.value);
    }
    isShow.value = false;
  } else {
    ElMessage.error("格式错误");
  }
};
const confirm = async () => {
  dialogVisible.value = false;
  if (file.value != undefined) {
    emit("confirm", file.value);
  }
};
watch(dialogVisible, async (newValue) => {
  if (newValue) {
    await nextTick();
    inputPaste.value?.focus();
  }
});
</script>
<template>
  <el
    button
    @click="
      isShow = true;
      dialogVisible = true;
    "
    >{{ title }}</el
  >
  <el-dialog
    v-model="dialogVisible"
    :title="title"
    width="600"
    :close-on-click-modal="false"
  >
    <div class="contain">
      <div
        v-show="isShow"
        @click="inputClick?.click()"
        @drop="drop"
        @dragenter.prevent
        @dragover.prevent
        class="upload"
      >
        支持点击，拖拽，粘贴上传
      </div>
      <input
        ref="inputPaste"
        @paste="paste"
        @blur="inputPaste?.focus()"
        style="opacity: 0; position: absolute"
      />
      <input
        type="file"
        @change="(e:any)=>{
          fileHandle(e.target?.files[0]);
        }"
        ref="inputClick"
        style="display: none"
        accept="*"
      />
      <div class="upload" v-show="!isShow">
        <img
          v-show="image"
          :src="image"
          alt="预览图片"
          style="
            width: 100%;
            height: 100%;
            object-position: center;
            object-fit: cover;
          "
        />
        <div v-show="!image">视频/音频已接收</div>
      </div>
    </div>
    <template #footer>
      <div style="display: flex">
        <el
          button
          @click="dialogVisible = false"
          style="margin-left: auto; padding: 0 10px"
          >取消</el
        >
        <el button @click="confirm()"> 确定</el>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
.contain {
  width: 400px;
  height: 250px;
  margin: 0 auto;
  border: 2px dashed var(--color-text);
  @extend center;
  color: #ccc;
  background: var(--color-background);
  padding: 0;
  box-sizing: border-box;
  transition: all 0.5s;

  .upload {
    @extend center;
    height: 100%;
    width: 100%;
    overflow: hidden;
    background: var(--color-card);
    -webkit-user-select: none; /* For Safari 3+ and Chrome */
    -moz-user-select: none; /* For Firefox */
    -ms-user-select: none; /* For Internet Explorer/Edge */
    user-select: none;
    &:hover {
      filter: brightness(90%);
    }
  }
}
</style>
