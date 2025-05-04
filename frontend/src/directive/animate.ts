
export default {
  mounted(el: HTMLElement) {
    const options = {
      root: null,
      rootMargin: '0px',
      threshold: 0.3
    };
    el.style.transition = 'transform 0.7s ease-out,opacity 0.7s ease-out,background 0.4 ease-out';
    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
      if (entry.isIntersecting) {
        el.style.transform='scale(1)'
        el.style.opacity = '1';
        } else {
           el.style.transform='scale(0)'
           el.style.opacity = '0';
        }
      });
    }, options);
    observer.observe(el);
    (el as any)._observer = observer;
  },
   beforeUnmount(el: HTMLElement) {
    (el as any)._observer.unobserve(el);
  }
};



