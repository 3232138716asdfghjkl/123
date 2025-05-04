<script setup lang="ts">
const linkListS = reactive(new LinkList());
const linkS = new Link();
const dialogVisible = ref(false);
onMounted(() => {
  linkListS.init();
});
</script>
<template>
  <el-table :data="linkListS.list" stripe>
    <el-table-column prop="id" label="id" />
    <el-table-column prop="nickname" label="昵称" />
    <el-table-column label="头像">
      <template #default="scope">
        <img :src="scope.row.avatar" style="width: 60%" />
      </template>
    </el-table-column>
    <el-table-column prop="description" label="简介" />
    <el-table-column prop="email" label="邮箱" />
    <el-table-column prop="address" label="网址" />
    <el-table-column prop="state" label="状态">
      <template #default="scope">
        {{ scope.row.state == 0 ? "待通过" : "通过" }}
      </template>
    </el-table-column>
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button
          size="small"
          type="primary"
          @click="linkS.update(scope.row.id, scope.row.email)"
          >通过
        </el-button>
        <el-button
          size="small"
          type="danger"
          @click="
            dialogVisible = true;
            linkS.item.id = scope.row.id;
          "
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible" title="提示" width="500">
    <span>确认删除</span>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="linkS.del()"> 确认 </el-button>
    </template>
  </el-dialog>
</template>

<style scoped></style>
