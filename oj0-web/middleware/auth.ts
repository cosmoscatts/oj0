/**
 * 路由权限控制
 */
export default defineNuxtRouteMiddleware((to) => {
  const authStore = useAuthStore()
  const user = authStore.user
  const hasLogin = authStore.checkLogin()

  if (checkAccess(user, to.meta?.access))
    return navigateTo(to.fullPath)

  if (!hasLogin)
    return navigateTo('/login')

  return navigateTo('/error')
})
