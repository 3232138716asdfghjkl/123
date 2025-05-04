import type comment from '@/types/modules/comment'


export class COmment {
  item:comment={
    content:'',
    browser:'',
    location:'',
    createTime:''
  }

  async add(){
    await commentAdd(this.item)
  }

  clear(){
    this.item={
      content:'',
      browser:'',
      location:'',
      createTime:''
    }
  }
}
