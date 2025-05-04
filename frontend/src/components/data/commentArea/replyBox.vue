<script setup lang="ts">
import axios from "axios";
import { emojis } from "./emojis";
import bowser from "bowser";
import { MdPreview } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
const prop = defineProps<{
  parentId?: number;
  replyId?: number;
  userName?: string;
  isShow?: boolean;
}>();
const emit = defineEmits(["submit"]);
const userAgent = window.navigator.userAgent;
const browser = bowser.getParser(userAgent);
const commentS = reactive(new COmment());
const commentListS = useCommentList();
const userS = useUser();
const popRef = ref();
const textRef = ref();
const router = useRouter();
const themeS = useTheme();
const isPreview = ref(false);
const textHeight = ref(50);
const likeS = useLike();

const input = (e: Event) => {
  const target = e.target as HTMLTextAreaElement;
  textHeight.value = target.scrollHeight;
};

const submit = async () => {
  commentS.item.browser = `${browser.getBrowserName()} ${browser.getBrowserVersion()}`;
  try {
    const res = await axios.get("https://ip.useragentinfo.com/json");
    commentS.item.location = res.data.province;
  } catch (error) {
    try {
      const res = await axios.get("https://myip.ipip.net/s");
      const ress = await axios.get(
        "https://api.vore.top/api/IPdata?ip=" + res.data
      );
      commentS.item.location = ress.data.ipdata.info1;
    } catch (error) {
      commentS.item.location = "未知";
    }
  }
  if (commentS.item.location != "未知") {
    commentS.item.location = commentS.item.location.slice(0, -1);
  }
  commentS.item.userId = userS.item.id;
  commentS.item.articleId = commentListS.articleId;
  if (prop.parentId) {
    commentS.item.parentId = prop.parentId;
  }
  if (prop.replyId) {
    commentS.item.replyId = prop.replyId;
  }
  await commentS.add();
  if (userS.item.id) {
    likeS.user(userS.item.id);
  }
  commentS.clear();
  commentListS.count++;
  commentListS.replyUpdate();
  emit("submit");
};
watch(prop, async () => {
  await nextTick();
  textRef.value?.focus();
});
</script>
<template>
  <div style="width: 100%">
    <div class="contain">
      <textarea
        :placeholder="
          userName ? `回复@${userName}:` : '期待您的留言(支持markdown语法)'
        "
        v-model="commentS.item.content"
        ref="textRef"
        :style="{ height: textHeight + 'px' }"
        @input="input"
      ></textarea>
      <div
        style="display: flex; width: 100%; align-items: center; margin-top: 5px"
      >
        <el-popover
          placement="bottom-end"
          :width="510"
          trigger="click"
          ref="popRef"
        >
          <template #reference>
            <SvgIcon name="笑" size="25px" style="cursor: pointer" />
          </template>
          <div>
            <el-scrollbar height="200px">
              <div style="display: flex; flex-wrap: wrap">
                <div
                  class="emoji"
                  v-for="(emoji, key) in emojis"
                  :key="key"
                  :title="key"
                  @click="(commentS.item.content += emoji), popRef.hide()"
                >
                  {{ emoji }}
                </div>
              </div>
            </el-scrollbar>
          </div>
        </el-popover>
        <el
          button
          small
          @click="isPreview = !isPreview"
          style="margin-left: auto; margin-right: 5px"
        >
          预览</el
        >
        <el button small @click="submit" v-if="userS.item.id"> 发布</el>
        <el button small @click="router.push('/welcome/login')" v-else>登陆</el>
      </div>
      <MdPreview
        v-show="isPreview"
        v-model="commentS.item.content"
        :theme="themeS.isdark ? 'dark' : 'light'"
        style="border-radius: 10px"
      />
    </div>
  </div>
</template>

<style scoped lang="scss">
@import "./common.scss";
.contain {
  width: 100%;
  @extend center;
  flex-direction: column;
}
textarea {
  transition: background-color 0.4s ease-out;
  overflow: hidden;
  font-family: cartoon;
  color: var(--text-color);
}

textarea {
  width: 100%;
  height: 100px;
  padding: 1rem;
  border-radius: $border-radius;
  box-sizing: border-box;
  resize: none;
}
.emoji {
  padding: 5px;
  font-size: 30px;
  &:hover {
    background: var(--color-card);
    filter: brightness(80%);
  }
}
</style>
