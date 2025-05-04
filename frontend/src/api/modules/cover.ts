import request from '@/api/request'
import cover from "@/types/modules/cover"
const prefix = '/cover/'
export const coverList = (state?:number) =>{
return request.get(prefix+'list',{params:{
  state
}})
}

export const coverAdd = (file:FormData) =>{
  return request.post(prefix+'add',file,{
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
export const coverDel = (id:number) =>{
  return request.delete(prefix+'del',{params:{
    id
  }})
}
export const coverUpdate = (data:cover) =>{
  return request.put(prefix+'update',data)
}
