<script setup lang="ts">
import { FormInstance, FormItemRule } from "element-plus";
import { isEmail } from "validator";
import { required } from "@/utils/content";
const prop = defineProps<{
  form: FormInstance;
}>();
const userS = useUser();
const timer = ref<number>(Number(localStorage.getItem("timer")));
window.onbeforeunload = () => {
  localStorage.setItem("timer", timer.value.toString());
};
const { resume, pause } = useIntervalFn(() => {
  if (timer.value! > 0) {
    timer.value! -= 1;
  } else {
    pause();
  }
}, 1000);
const codeRule: FormItemRule[] = [
  required,
  {
    validator: (_rule, _value, callback) => {
      if (!isEmail(userS.item.email)) {
        callback(new Error("邮箱格式错误"));
      } else {
        callback();
      }
    },
    trigger: "blur",
  },
];
const submit = () => {
  prop.form.validateField("email", (valid) => {
    if (valid) {
      userS.userEmail(userS.item.email);
      timer.value = 60;
      resume();
    }
  });
};
</script>
<template>
  <div>
    <el-form-item prop="email" label="邮箱" size="large" :rules="codeRule">
      <el-input
        v-model="userS.item.email"
        placeholder="输入邮箱"
        show-word-limit
      ></el-input>
    </el-form-item>
    <div style="display: flex">
      <el-form-item prop="code" label="验证码" size="large" :rules="[required]">
        <el-input
          v-model="userS.item.code"
          placeholder="输入邮箱验证码"
        ></el-input>
      </el-form-item>
      <el
        button
        style="margin-left: 10px; flex-grow: 1"
        @click="submit()"
        v-show="!timer"
      >
        <div>发送验证码</div>
      </el>
      <el button large style="margin-left: 10px; flex-grow: 1" v-show="timer">
        <div>{{ timer }} 秒可发送</div></el
      >
    </div>
  </div>
</template>

<style scoped></style>
