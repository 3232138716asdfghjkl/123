export default class nameMap{
  map = new Map();
  init(data:any){
    for (let i of data) {
      this.map.set(i.id,i.name);
    }

  }
  get(id:number){
    return this.map.get(id);
  }
}
