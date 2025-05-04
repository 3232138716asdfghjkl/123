<script setup lang="ts">
const tagS = useTag();
const categoryListS = useCategoryList();
const colorI = new color();
interface Map {
  [key: string]: any;
}
const map = ref<Map>({});
const count = ref([]);
const router = useRouter();
onMounted(async () => {
  count.value = await categoryListS.count();
  count.value.forEach((item) => {
    const key = Object.keys(item)[0];
    map.value[key] = item[key];
  });
});
</script>
<template>
  <div style="position: sticky; top: 20px">
    <sideCard title="分类" icon="分类">
      <div class="category" :style="{ '--color': colorI.random() }">
        <div
          v-for="(item, index) in categoryListS.list"
          class="text"
          @click="router.push(`/user/category/${item.id}`)"
        >
          <span class="right">{{ item.name }}</span>
          <span class="left">{{ map[`${index + 1}`] }}</span>
        </div>
      </div>
    </sideCard>
    <sideCard title="标签" icon="标签">
      <div class="tag">
        <div
          v-for="item in tagS.list"
          @click="router.push(`/user/tag/${item.id}`)"
        >
          <div class="test" :style="{ '--color': colorI.random() }">
            {{ item.name }}
          </div>
        </div>
      </div>
    </sideCard>
  </div>
</template>

<style scoped lang="scss">
.category {
  @extend center;
  flex-direction: column;
  .text {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 3rem;
    width: 100%;
    font-size: 17px;
    border-radius: 10px;
    .right {
      transition: transform 0.3s ease;
    }
    .left {
      transition: transform 0.3s ease;
    }
    &:hover {
      background: var(--color);
      /* background: $sky-blue; */
    }
    &:hover .right {
      transform: translateX(10px);
    }
    &:hover .left {
      transform: translateX(-10px);
    }
  }
}
.tag {
  display: flex;
  flex-wrap: wrap;
  .test {
    @extend center;
    margin: 0.5rem;
    border-radius: $border-radius;
    height: 2rem;
    width: 3rem;

    &:hover {
      color: white;
      background: var(--color);
    }
  }
}
</style>
