import { defineStore } from 'pinia'
import type tag from '@/types/modules/tag'
import {tagList} from '@/api/modules/tag'
import nameMap from "@/utils/nameMap"

export const useTag =  defineStore('tag', ()=>{
  const list = reactive<tag[]>([])
 const map = new nameMap();

 const init = async () =>{
  const res = await tagList();
  list.push(... res)

  map.init(res)
  }

  return{
    list,
    init,
    map
  }
})



