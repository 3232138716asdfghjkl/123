import { defineStore } from 'pinia'
import type user from '@/types/modules/user'


export const useUser =  defineStore('user', ()=>{
   const item = reactive<user>({
    username: '',
    password: '',
    avatar:'',
    email: '',
    repassword:'',
    code:''
   })



   const save = (token:string)=>{
    if(token != 'error'){
      localStorage.setItem("token", token);
      info()
      return true;
    }
    return false;
   }
   const login =async ()=>{
    return save(await userLogin(item))
   }
   const register = async ()=>{
    return save(await userRegister(item));
   }

   const info = async ()=>{
    if( localStorage.getItem("token")){
      Object.assign(item, await userInfo());
    }
   }
  const avatar =async (file:File)=>{
    const formData = new FormData();
    formData.append("file",file);
    item.avatar = await userAvatar(formData,item.email)
  }
  const clear = ()=>{
    localStorage.removeItem("token");
    item.id = undefined;
    item.username = '';
    item.password = '';
    item.avatar = '';
    item.email = '';
    item.code = '';
    item.repassword = '';
   };

  return{
    item,login,userEmail,register,avatar,info,clear,userReset
  }
})


