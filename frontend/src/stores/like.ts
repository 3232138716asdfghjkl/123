import like from "@/types/modules/like"

import { defineStore } from 'pinia'

export const useLike =  defineStore('like', ()=>{
  const commentList = reactive<number[]>([]);

  const change= (userId:number,commentId:number)=>{
    likeChange({userId,commentId})
 }

 const user = async (id:number)=>{
   const res:like[] = await likeUser(id)
   commentList.push(...res.map((l)=>{
     return l.commentId
   }))

 }

  return{
    commentList,change,user
  }
})

