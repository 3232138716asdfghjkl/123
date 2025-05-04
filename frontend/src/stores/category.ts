import { defineStore } from 'pinia'
import type category from '@/types/modules/category'
import {categoryList,categoryArticleCount} from "@/api/modules/category"
import nameMap from "@/utils/nameMap"
export const useCategoryList =  defineStore('categoryList', ()=>{
  const list =reactive<category[] >([])
  const map = new nameMap();

  const init = async () =>{
    const res = await categoryList();
    list.push(... res)
    map.init(res)
  }
   const count = ()=>{
      return categoryArticleCount();
    }

  return{
    list,
    init,
    map,
    count
  }
})


