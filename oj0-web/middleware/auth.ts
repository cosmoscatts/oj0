import type { RouteLocationNormalized } from 'vue-router'

/**
 * 路由权限控制
 */
export default defineNuxtRouteMiddleware((to) => {
  if (isHydrated.value)
    return handleAuth(to)

  onHydrated(() => handleAuth(to))
})

function handleAuth(to: RouteLocationNormalized) {
  const authStore = useAuthStore()
  const user = authStore.user

  if (checkAccess(user, to.meta?.access))
    return

  return navigateTo('/no-auth')
}
