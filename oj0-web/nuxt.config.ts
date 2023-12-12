export default defineNuxtConfig({
  app: {
    pageTransition: {
      name: 'page',
      mode: 'out-in',
    },
    layoutTransition: {
      name: 'layout',
      mode: 'out-in',
    },
  },
  devtools: { enabled: true },
  vue: {
    defineModel: true,
    propsDestructure: true,
  },
  modules: [
    '@vueuse/nuxt',
    '@unocss/nuxt',
    '@pinia/nuxt',
    ['unplugin-icons/nuxt', {
      autoInstall: true,
      scale: 1,
      defaultClass: 'inline-block',
    }],
  ],
  css: [
    'bytemd/dist/index.css',
    'github-markdown-css',
    'highlight.js/styles/vs.css',
    '@arco-design/web-vue/dist/arco.css',
    '@arco-design/web-vue/es/message/style/css.js',
    '@arco-design/web-vue/es/modal/style/css.js',
    '@arco-design/web-vue/es/notification/style/css.js',
    '@unocss/reset/tailwind.css',
    'floating-vue/dist/style.css',
    '~/styles/default-theme.css',
    '~/styles/vars.css',
    '~/styles/bytemd.css',
    '~/styles/codemirror.css',
    '~/styles/arco-design.css',
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
      './composables/question-manage',
      './composables/questions',
      './composables/table',
      './composables/user-manage',
    ],
    injectAtEnd: true,
  },
  components: [
    {
      path: '~/components',
      pathPrefix: false,
    },
  ],
})
