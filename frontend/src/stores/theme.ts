import { defineStore } from 'pinia'

export const useTheme =  defineStore('theme', ()=>{
 const isdark = ref(localStorage.getItem('isdark') == 'true' ?true:false);

 const toggle = ()=>{
  isdark.value = !isdark.value;
  localStorage.setItem('isdark', `${isdark.value}`)
  document.documentElement.classList.toggle("dark", isdark.value);
 }

  return{
    isdark,toggle
  }
})

