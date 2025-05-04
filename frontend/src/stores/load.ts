

import { defineStore } from 'pinia'

export const useLoad =  defineStore('load', ()=>{
  const isLoading = ref(false)

    const hide = ()=>{
      document.body.style.overflow = '';
      isLoading.value = false
  }
  const show = ()=>{
      document.body.style.overflow = 'hidden';
      isLoading.value = true
  }
  return{
    isLoading,hide,show
  }
})

