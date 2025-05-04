interface css{
  [key:string]:any
}
export default class color{
  letters = '0123456789ABCDEF';

  random(){
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += this.letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  normal(key:string){
    const css:css = {}
    css[key] =  this.random();
    return css;
  }

  high(key:string){
    const css:css = {}
    const color1 = this.random();
    const color2 = this.random();
    const angle = Math.floor(Math.random() * 360);
    css[key] =  `linear-gradient(${angle}deg, ${color1}, ${color2})`
    return  css;
  }
}
