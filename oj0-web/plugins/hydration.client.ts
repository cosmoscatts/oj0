export default defineNuxtPlugin((nuxtApp) => {
  nuxtApp.hooks.hookOnce('app:suspense:resolve', () => { // 在 app created 之前执行
    isHydrated.value = true
  })
})
