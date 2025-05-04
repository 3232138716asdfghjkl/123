import request from '@/api/request'
import user from '@/types/modules/user';
const prefix = '/user/'

export const userLogin = (data:user) =>{
  return request.post(prefix+'login',data)
}

export const userRegister = (data:user) =>{
  return request.post(prefix+'register',data)
}

export const userInfo = () =>{
  return request.get(prefix+'userInfo')
}
export const userReset = (data:user) =>{
  return request.post(prefix+'reset',data)
}
export const userEmail = (email:string) =>{
  return request.get(prefix+'email',{
    params:{
      email
    }
  })
}
export const userAvatar = (file:FormData,email?:string) =>{
  return request.post(prefix+'avatar',file,{
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    params:{
      email
    }
  })
}


