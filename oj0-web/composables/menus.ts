import { MENUS } from '~/constants'

/**
 * 获取用户菜单
 */
export function getAppMenus() {
  const authStore = useAuthStore()

  return computed(() => {
    const user = authStore.user

    if (!user)
      return []

    return MENUS.filter((menu) => {
      return checkAccess(user, menu.access)
    })
  })
}
