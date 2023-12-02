/**
 * 路由权限控制
 */
export default defineNuxtRouteMiddleware((to, from) => {
  return navigateTo('/login')
})
