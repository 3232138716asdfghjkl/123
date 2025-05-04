import request from '@/api/request'
import article from '@/types/modules/article';
const prefix = '/article/'
export const articlePage = (current : number, size : number,state:number) =>{
return request.get(prefix+'page',{params:{
  current,size,state
}})
}

export const articleList = (state ?: number,top ?:number) =>{
  return request.get(prefix+'list',{params:{
    state,
    top
  }});
}
export const articleAdd = (data:article) =>{
  return request.post(prefix+'add',data)
}
export const articleUpdate = (data:article) =>{
  return request.put(prefix+'update',data)
}
export const articleDel= (id:number) =>{
  return request.delete(prefix+'del',{
    params:{
      id
    }
  })
}
export const articleGet = (id:number) =>{
  return request.get(prefix+'get',{params:{
      id
    }
  })
}


export const articleAround = (id:number)=>{
  return request.get(prefix+'around',{params:{
    id
  }
})
}

export const articleListByIds = (ids:number[])=>{
  return request.post(prefix+'list/byIds',ids)
}
export const articleListByCateId= (id:number)=>{
  return request.get(prefix+'list/byCateId',{params:{
    id
  }
})
}
export const articleListByTagId = (id:number) =>{
  return request.get(prefix+'list/byTagId',{
    params:{
      id
    }
  })
}

export const articleVisit = (id:number) =>{
  return request.post(prefix+`visit/${id}`)
}
