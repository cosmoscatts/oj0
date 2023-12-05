import { ACCESS_ENUM, MENUS } from '~/constants'

/**
 * 获取用户菜单
 *  - ADMIN 全部菜单
 *  - USER/NOT_LOGIN 用户角色菜单
 */
export function getAppMenus() {
  const authStore = useAuthStore()

  return computed(() => {
    const userRole = authStore.user?.userRole ?? ACCESS_ENUM.NOT_LOGIN

    if ([ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.USER].includes(userRole)) {
      return MENUS.filter((menu) => {
        return menu.access !== ACCESS_ENUM.ADMIN
      })
    }
    return MENUS
  })
}
