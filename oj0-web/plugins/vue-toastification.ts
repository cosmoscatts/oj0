import VueToast from 'vue-toastification'

export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.vueApp.use(VueToast, {
    timeout: 2000,
    position: 'top-right',
  })
})
