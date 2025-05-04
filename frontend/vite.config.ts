import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import path from 'path'
import {resolve} from 'path'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver(),
      ],
      imports: ["vue", "vue-router", "@vueuse/core"],
      dirs: ["./src/utils","./src/stores","./src/service","./src/api/modules"],
      dts: 'src/types/lib/auto-imports.d.ts',
    }),
    Components({
      dts: 'src/types/lib/components.d.ts',
      resolvers: [ElementPlusResolver()],
    }),
    createSvgIconsPlugin({
      iconDirs: [path.resolve(process.cwd(), 'src/assets/icons')],
      symbolId: 'icon-[dir]-[name]',
      customDomId: '__svg__icons__dom__',
    }),
  ],
  /* 屏蔽没用的sass警告 */
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@use "@/styles/common.scss" as *;',
        silenceDeprecations: ['legacy-js-api'],
        javascriptEnabled: true
      }
    }
  },
  resolve: {
    alias: [
      {
      find: '@',
      replacement: resolve(__dirname,'./src')
      }
  ]
  }
})
