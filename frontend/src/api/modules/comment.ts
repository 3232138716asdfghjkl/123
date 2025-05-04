import request from '@/api/request'
import type comment from "@/types/modules/comment"

const prefix = '/comment/'


export const commentPage = (id:number,current:number,size:number) =>{
  return request.get(prefix+ 'page',{
    params: {
      id,current,size
    }
  })
}
export const commentList = (ids:number[]) =>{
  return request.post(prefix+ 'list',ids)
}

export const commentCount = (id:number) =>{
  return request.get(prefix+ 'count',{
    params: {
      id
    }
  })
}

export const commentAdd = (data:comment) =>{
  return request.post(prefix+ 'add',data)
}
export const commentdel = (id:number) =>{
  return request.delete(prefix+ 'del',{
    params:{
      id
    }
  })
}
