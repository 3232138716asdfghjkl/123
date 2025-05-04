

export default {
    mounted(el: HTMLElement, binding: any) {
        const options = {
            root: null,
            rootMargin:  '0px',
            threshold: 0.1,
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    // 元素进入视口，执行加载操作
                    binding.value.callback();
                    // 停止观察，避免重复触发
                    observer.unobserve(el);
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

