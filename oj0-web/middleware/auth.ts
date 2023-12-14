import type { RouteLocationNormalized } from 'vue-router'

/**
 * 路由权限控制
 */
export default defineNuxtRouteMiddleware((to) => {
  if (isHydrated.value)
    return handleAuth(to)

  onHydrated(() => handleAuth(to))
})

async function handleAuth(to: RouteLocationNormalized) {
  const authStore = useAuthStore()
  const hasLogin = authStore.getHasLogin()
  if (!hasLogin.value)
    await authStore.autoLogin()

  if (checkAccess(authStore.user, to.meta?.access))
    return

  return navigateTo('/no-auth')
}
