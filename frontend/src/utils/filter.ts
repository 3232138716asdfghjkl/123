export const contentFilter = (text:string)=>{
  return text.replace(/<[^>]*>|#|>/g, "");
  }
  export const timeFilter = (time?:string)=>{
    return time?.substring(0, 10);
}
