import request from '@/api/request'

export const categoryList = () =>{
  return request.get('/category/list')
}
export const categoryArticleCount = () =>{
  return request.get('/category/count')
}
