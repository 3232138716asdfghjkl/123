<script setup lang="ts">
const tagS = useTag();
const categoryListS = useCategoryList();
const articleListS = reactive(new ArticleList());

const articleS = reactive(new Article());
const dialogVisible = ref(false);

onMounted(() => {
  articleListS.state = 3;
  articleListS.init();
});

const articleDel = async () => {
  await articleS.del();
  dialogVisible.value = false;
  articleListS.init();
};
</script>

<template>
  <div>
    <el-table :data="articleListS.list" stripe>
      <el-table-column prop="id" label="id" />
      <el-table-column prop="title" label="标题" />
      <el-table-column label="封面">
        <template #default="scope">
          <img :src="scope.row.image" style="width: 60%" />
        </template>
      </el-table-column>
      <el-table-column label="分类">
        <template #default="scope">
          <p>{{ categoryListS.map.get(scope.row.categoryId) }}</p>
        </template>
      </el-table-column>
      <el-table-column label="标签">
        <template #default="scope">
          <div style="display: flex; gap: 5px">
            <el tag v-for="item in scope.row.tags">
              {{ tagS.map.get(item) }}
            </el>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template #default="scope">
          <p>{{ scope.row.state == 0 ? "草稿" : "发布" }}</p>
        </template>
      </el-table-column>
      <el-table-column prop="visitCount" label="浏览量" />
      <el-table-column prop="top" label="是否置顶">
        <template #default="scope">
          <p>{{ scope.row.top == 0 ? "否" : "是" }}</p>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column prop="updateTime" label="修改时间" />
      <el-table-column label="操作">
        <template #default="scope">
          <router-link :to="`/admin/add?id=${scope.row.id}`">
            <el-button size="small" type="primary"> 修改 </el-button>
          </router-link>
          <el-button
            size="small"
            type="danger"
            @click="
              dialogVisible = true;
              articleS.item.id = scope.row.id;
              articleS.item.image = scope.row.image;
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
        <div style="width: 100%; display: flex; flex-direction: row-reverse">
          <el button @click="dialogVisible = false">取消</el>
          <el button @click="articleDel()"> 确认 </el>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped></style>
