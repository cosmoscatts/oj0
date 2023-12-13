/**
 * 如果更新的是当前登录用户，需要更新登录信息
 */
export async function checkAfterUpdateUser(data: Partial<User>) {
  if (!data.id)
    return
  const authStore = useAuthStore()

  if (authStore.user?.id !== data.id)
    return

  const loginUser = authStore.user

  const source = {
    userAvatar: loginUser.userAvatar,
    userAccount: loginUser.userAccount,
    userName: loginUser.userName,
    userRole: loginUser.userRole,
    userProfile: loginUser.userProfile,
  }

  const target = {
    userAvatar: data.userAvatar,
    userAccount: data.userAccount,
    userName: data.userName,
    userRole: data.userRole,
    userProfile: data.userProfile,
  }

  if (JSON.stringify(source) === JSON.stringify(target))
    return

  await authStore.autoLogin()

  if (source.userRole !== target.userRole) {
    const route = useRoute()
    const router = useRouter()
    // 如果退出登录后当前页面没有权限，则跳转至首页
    if (!checkAccess(authStore.user, route.meta.access))
      router.push('/')
  }
}

/**
 * 不能删除当前登录用户
 */
export function checkBeforeDeleteUser(id?: number) {
  const authStore = useAuthStore()
  const loginUser = authStore.user
  return !id || id !== loginUser?.id
}
