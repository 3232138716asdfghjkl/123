 export default interface article{
  id?: number
  title: string
  image: string
  categoryId?: number
  tags?:number[]
  content: string
  state: number
  top: number | boolean
  visitCount:number
  createTime?:string
  updateTime?:string
}
