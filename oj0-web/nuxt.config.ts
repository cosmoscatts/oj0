export default defineNuxtConfig({
  extends: '@nuxt-themes/typography',
  ssr: false,
  app: {
    pageTransition: {
      name: 'page',
      mode: 'out-in',
    },
    layoutTransition: {
      name: 'layout',
      mode: 'out-in',
    },
    head: {
      viewport: 'width=device-width,initial-scale=1,viewport-fit=cover',
      link: [
        { rel: 'icon', href: '/favicon.png', sizes: 'any' },
        { rel: 'apple-touch-icon', href: '/apple-touch-icon.png' },
      ],
    },
  },
  devtools: { enabled: false },
  vue: {
    defineModel: true,
    propsDestructure: true,
  },
  modules: [
    '@vueuse/nuxt',
    '@unocss/nuxt',
    '@pinia/nuxt',
    '@nuxt/content',
    ['unplugin-icons/nuxt', {
      autoInstall: true,
      scale: 1,
      defaultClass: 'inline-block',
    }],
  ],
  css: [
    'bytemd/dist/index.css',
    'github-markdown-css',
    '@arco-design/web-vue/dist/arco.css',
    '@arco-design/web-vue/es/message/style/css.js',
    '@arco-design/web-vue/es/modal/style/css.js',
    '@arco-design/web-vue/es/notification/style/css.js',
    'vue-toastification/dist/index.css',
    '@unocss/reset/tailwind.css',
    'floating-vue/dist/style.css',
    '~/styles/default-theme.css',
    '~/styles/vars.css',
    '~/styles/hljs-atom-one-light.css',
    '~/styles/hljs-atom-one-dark.css',
    '~/styles/bytemd.css',
    '~/styles/codemirror.css',
    '~/styles/arco-design.css',
    '~/styles/prose.css',
    '~/styles/transition.css',
    '~/styles/scrollbars.css',
    '~/styles/dropdown.css',
    '~/styles/main.css',
  ],
  router: {
    options: {
      hashMode: true,
      scrollBehaviorType: 'smooth',
    },
  },
  imports: {
    dirs: [
      './composables/**',
      './api/**',
    ],
    injectAtEnd: true,
  },
  components: [
    {
      path: '~/components',
      pathPrefix: false,
    },
  ],
  content: {
    api: {
      baseURL: '/_content',
    },
    experimental: {
      clientDB: true,
    },
  },
})
