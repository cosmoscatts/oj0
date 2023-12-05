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
    '@arco-design/web-vue/dist/arco.css',
    '@unocss/reset/tailwind.css',
    '~/styles/default-theme.css',
    '~/styles/vars.css',
    '~/styles/arco-design-overrite.css',
    '~/styles/transition.css',
    '~/styles/scrollbars.css',
    '~/styles/main.css',
  ],
  router: {
    options: {
      hashMode: true,
      scrollBehaviorType: 'smooth',
    },
  },
  imports: {
    dirs: ['~/composables/**'],
  },
  components: [
    {
      path: '~/components',
      pathPrefix: false,
    },
  ],
})
