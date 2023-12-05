/**
 * 路由权限控制
 */
export default defineNuxtRouteMiddleware((to) => {
  const authStore = useAuthStore()
  const user = authStore.user
  const hasLogin = authStore.getHasLogin()

  if (checkAccess(user, to.meta?.access))
    return navigateTo(to)

  if (!hasLogin.value)
    return navigateTo('/login')

  return navigateTo('/no-auth')
})
