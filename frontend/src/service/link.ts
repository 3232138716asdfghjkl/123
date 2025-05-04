import type link from '@/types/modules/link'
export class LinkList{
  list:link[]  = [];
  async init(){
    this.list = await linkList()

  }
}
export class Link{
  item = reactive<link>({
  nickname:'',
  address:'',
  state:0,
  avatar:'',
  description:'',
  email:''
  })
  add(){
    return linkAdd(this.item);
  }
  update(id:number,email:string){
  linkUpdate(id,email);
  }
  del(){
    linkDel(this.item.id!)
  }
}
