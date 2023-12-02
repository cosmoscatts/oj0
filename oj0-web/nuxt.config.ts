
export default defineNuxtConfig({
  devtools: { enabled: true },
  vue: {
    defineModel: true,
    propsDestructure: true
  },
  modules: [
    '@vueuse/nuxt',
    '@unocss/nuxt',
    '@pinia/nuxt',
  ],
  css: [
    '@unocss/reset/tailwind.css',
    '~/styles/main.css',
  ],
})
