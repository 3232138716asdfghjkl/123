import type file from '@/types/modules/file'

export class FIle{
  list:file[] = [];

  filename = '';

  file:globalThis.File|undefined = undefined;
  async init(articleId:number){
    this.list = await fileList(articleId);

  }
  async upload(file?: globalThis.File,articleId?:number){
    if(file!=undefined){
      this.file = file;
    }
    const formData = new FormData();
    formData.append("file", this.file! );
    this.file = undefined;
    this.filename = await fileUpload(formData, articleId);
  }
   del (filename?:string){
    if(filename!=undefined){
      this.filename = filename;
    }
    fileDel(this.filename);
  }

 }
