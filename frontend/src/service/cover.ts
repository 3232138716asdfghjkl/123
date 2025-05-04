import type cover from '@/types/modules/cover'
export class Cover{
  list:cover[] = [];
  async init (state?:number){
    this.list = await coverList(state);
    this.shuffleArray(this.list);
  }
   shuffleArray(array:any[]) {
    for (let i = array.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1)); // 生成一个随机索引
      [array[i], array[j]] = [array[j], array[i]]; // 交换元素
    }
    return array;
  }
  del (id:number){
    coverDel(id);
  }
   add(file: globalThis.File){
    const formData = new FormData();
    formData.append("file",file );
    coverAdd(formData);
  }
  update(item:cover){
   if(item.state == 0){
    item.state = 1;
   }else{
    item.state = 0;
   }
   coverUpdate(item);
  }
}
