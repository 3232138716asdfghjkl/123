import { defineStore } from 'pinia'
import type comment from '@/types/modules/comment';
export const useCommentList =  defineStore('commentList', ()=>{
  const parentList = reactive<comment[]>([]);
  const childList = reactive(new Map<number,comment[]>());
  const current = ref(1);
  const articleId = ref(0)
  const isMore = ref(true);
  const count = ref(0)

  const init =  async ()=>{
      const res:comment[] = await commentPage(articleId.value,current.value,10);
      parentList.push(...res);
      if(res.length<10){
        isMore.value = false;
      }
      childList.clear();
      childGet(res);
}

const replyUpdate = async ()=>{
  const res:comment[] = await commentPage(articleId.value,1,current.value * 10);
  parentList.length = 0;
  parentList.push(...res);
  childList.clear();
  childGet(res);
}
const countGet = async ()=>{
  count.value  = await commentCount(articleId.value)
}

const childGet = async (parents:comment[])=>{
  const ids = parents.map((c)=>{
    return c.id!;
  })
  const ress:comment[] = await commentList(ids);
  if(ress){
    const nameMap = new Map<number, string>();
    ress.forEach((c) => {
      nameMap.set(c.id!, c.username!);
    });
    ress.forEach((c) => {
      if (c.replyId) {
        c.content = `<font color='#4A90E2'>@${nameMap.get(c.replyId)}</font>: ${
          c.content
        }`;
      }
        if(childList.has(c.parentId!)){
          childList.get(c.parentId!)!.push(c);
        }else{
          childList.set(c.parentId!,[c])
        }
    })
  }
}
  const del = async (id:number)=>{
    await commentdel(id)
    countGet()
    replyUpdate()
  }
return{
  parentList,
  childList,
  current,
  isMore,
  init,
  replyUpdate
  ,countGet,
  count,articleId,del
}
})
