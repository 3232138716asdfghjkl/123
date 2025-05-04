import request from '@/api/request'
const prefix = '/file/'
export const fileList = (id:number) =>{
  return request.get('/file/list',{
    params:{
      id
    }
  })
}
export const fileUpload = (file:FormData,id?:number) =>{
  return request.post(prefix+'add',file,{
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    params:{
      id
    }
  })
}
export const fileDel = (filename:string) =>{
  return request.delete(prefix+'del',{
    params:{
     filename
    }
  })
}


