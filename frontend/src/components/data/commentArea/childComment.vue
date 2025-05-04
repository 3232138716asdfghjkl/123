<script setup lang="ts">
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/index.css";
import type comment from "@/types/modules/comment";
const likeS = useLike();
const userS = useUser();
const commentListS = useCommentList();
const isReply = reactive<boolean[]>([]);
const isLike = reactive<boolean[]>([]);
const dialogVisible = ref(false);
const delComId = ref(0);

const prop = defineProps<{
  parentId: number;
}>();

onMounted(() => {
  userLikeGet();
});

const like = async (coommentId: number, index: number, item: comment) => {
  if (userS.item.id) {
    likeS.change(userS.item.id, coommentId);
    if (isLike[index]) {
      item.like!--;
    } else {
      item.like!++;
    }
    isLike[index] = !isLike[index];
  } else {
    ElMessage.error("点赞请登录");
  }
};
const userLikeGet = () => {
  commentListS.childList.get(prop.parentId)?.forEach((element, index) => {
    if (likeS.commentList.includes(element.id!)) {
      isLike[index] = true;
    }
  });
};
</script>
<template>
  <card v-for="(item, index) in commentListS.childList.get(parentId)" v-animate>
    <div style="width: 100%; display: flex; align-items: center">
      <el-avatar :src="item.avatar" v-if="item.avatar" fit="cover" />
      <img
        src="../../../assets/image/默认头像.png"
        style="height: 40px"
        v-else
      />
      <div style="font-size: 20px; display: flex; margin-left: 5px">
        {{ item.username }}
        <el tag style="margin-right: 5px"> {{ item.location }}</el>
        <el tag>{{ item.browser }}</el>
      </div>
      <div style="font-size: 15px; margin-left: auto">
        {{ item.createTime }}
      </div>
    </div>
    <div style="width: 100%; display: flex; align-items: center">
      <MdPreview v-model="item.content" />
      <div
        style="margin-bottom: 1.3rem; margin-top: auto; display: flex"
        v-if="
          userS.item.username == '筑乐' || userS.item.username == item.username
        "
      >
        <div
          class="icon"
          @click="(dialogVisible = true), (delComId = item.id!)"
        >
          <svgIcon name="删除" size="20px" />
        </div>
        <div class="icon">
          <svgIcon
            name="点赞"
            size="20px"
            @click="like(item.id!, index, item)"
            v-show="!isLike[index]"
          />
          <svgIcon
            name="已点赞"
            size="20px"
            color="#3b82f6 "
            @click="like(item.id!, index, item)"
            v-show="isLike[index]"
          />
          <span class="number">{{ item.like == 0 ? "" : item.like }}</span>
        </div>
        <div class="icon">
          <svgIcon
            name="回复"
            size="20px"
            @click="isReply[index] = !isReply[index]"
          />
        </div>
      </div>
    </div>
    <reply-box
      :parentId="parentId"
      :replyId="item.id"
      :userName="item.username"
      :isShow="isReply[index]"
      @submit="isReply[index] = !isReply[index]"
      v-show="isReply[index]"
    ></reply-box>
  </card>
  <el-dialog v-model="dialogVisible" title="提示" width="500">
    <span>确认删除</span>
    <template #footer>
      <div
        style="
          width: 100%;
          display: flex;
          flex-direction: row-reverse;
          gap: 10px;
        "
      >
        <el button @click="dialogVisible = false">取消</el>
        <el
          button
          @click="
            commentListS.del(delComId);
            dialogVisible = false;
          "
        >
          确认
        </el>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped lang="scss">
@import "./common.scss";
:deep(#md-editor-v3) {
  background-color: var(--color-card);
}
</style>
