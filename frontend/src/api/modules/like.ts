import request from '@/api/request'
import like from "@/types/modules/like"
const prefix = '/like/';

export const likeChange = (data:like) =>{
  return request.put(prefix + 'change',data)
}
export const likeUser = (id:number) =>{
  return request.get(prefix + 'user',{
    params:{
      id
    }
  })
}
