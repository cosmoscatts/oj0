import { ACCESS_ENUM } from '~/constants'

/**
 * 判断用户是否具有某个权限
 *
 * @return boolean
 */
export function checkAccess(user: Nullable<User>, needAccess = ACCESS_ENUM.NOT_LOGIN) {
  const userAccess = user?.userRole ?? ACCESS_ENUM.NOT_LOGIN

  if (needAccess === ACCESS_ENUM.NOT_LOGIN) // 不需要权限的页面
    return true

  if (needAccess === ACCESS_ENUM.USER) { // 需要用户权限的页面
    if (userAccess === ACCESS_ENUM.NOT_LOGIN)
      return false
  }

  if (needAccess === ACCESS_ENUM.ADMIN) { // 需要管理员权限的页面
    if (userAccess !== ACCESS_ENUM.ADMIN)
      return false
  }

  return true
}
