import request from '@/api/request'


export const tagList = () =>{
  return request.get('/tag/list')
}

