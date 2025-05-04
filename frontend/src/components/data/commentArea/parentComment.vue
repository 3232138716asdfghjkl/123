<script setup lang="ts">
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { ElMessage } from "element-plus";
import "element-plus/theme-chalk/index.css";
import type comment from "@/types/modules/comment";
const commentListS = useCommentList();
const likeS = useLike();
const userS = useUser();

const isLike = reactive<boolean[]>([]);
const isChild = reactive<boolean[]>([]);
const isReply = reactive<boolean[]>([]);
const dialogVisible = ref(false);

const delComId = ref(0);
onMounted(() => {
  userLikeGet();
});

watch(
  [commentListS.parentList, likeS.commentList],
  () => {
    userLikeGet();
  },
  { deep: false }
);

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
  isLike.length = 0;
  commentListS.parentList.forEach((element, index) => {
    if (likeS.commentList.includes(element.id!)) {
      isLike[index] = true;
    }
  });
};
</script>
<template>
  <card v-for="(item, index) in commentListS.parentList" v-animate>
    <div style="width: 100%; display: flex; align-items: center">
      <el-avatar :src="item.avatar" v-if="item.avatar" fit="cover" />
      <img
        src="../../../assets/image/默认头像.png"
        style="height: 40px"
        v-else
      />
      <div style="font-size: 20px; margin-left: 5px">
        {{ item.username }}
      </div>
      <div style="font-size: 15px; margin-left: auto">
        {{ item.createTime }}
      </div>
    </div>
    <MdPreview v-model="item.content" />
    <div style="display: flex">
      <el tag style="margin-right: 5px"> {{ item.location }}</el>
      <el tag>{{ item.browser }}</el>
      <div style="display: flex; margin-left: auto">
        <div
          class="icon"
          @click="(dialogVisible = true), (delComId = item.id!)"
          v-if="
            userS.item.username == '筑乐' ||
            userS.item.username == item.username
          "
        >
          <svgIcon name="删除" size="20px" />
        </div>
        <div
          class="icon"
          @click="isChild[index] = !isChild[index]"
          v-if=" commentListS.childList?.get(item.id!)"
        >
          <svgIcon name="查看回复" size="20px" />
          <span class="number">
            {{
              commentListS.childList?.get(item.id!)
                ? commentListS.childList?.get(item.id!)?.length
                : ""
            }}
          </span>
        </div>
        <div class="icon" @click="like(item.id!, index, item)">
          <svgIcon name="点赞" size="20px" v-show="!isLike[index]" />
          <svgIcon
            name="已点赞"
            size="20px"
            color="#3b82f6 "
            v-show="isLike[index]"
          />
          <span class="number">{{ item.like == 0 ? "" : item.like }}</span>
        </div>
        <div class="icon" @click="isReply[index] = !isReply[index]">
          <svgIcon name="回复" size="20px" />
        </div>
      </div>
    </div>
    <reply-box
      ref="replyRef"
      :parentId="item.id"
      :userName="item.username"
      :isShow="isReply[index]"
      @submit="(isReply[index] = !isReply[index]), (isChild[index] = true)"
      v-show="isReply[index]"
    ></reply-box>
    <ChildComment :parentId="item.id!" v-if="isChild[index]"></ChildComment>
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
