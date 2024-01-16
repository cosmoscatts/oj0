import type { StoreDefinition } from 'pinia'
import { useToast } from 'vue-toastification'
import { ACCESS_ENUM } from '~/constants'

export const useAuthStore = defineStore('authStore', () => {
  const user = ref<Nullable<User>>(null)

  function updateUser(loginUser: Nullable<User>) {
    user.value = loginUser
    useTimeoutFn(checkUserInfoCompletely, 500)
  }

  async function checkUserInfoCompletely() {
    const route = useRoute()
    if (route.fullPath === '/user-profile')
      return
    const { data } = await AuthApi.checkAccount()
    if (data === false) {
      useConfirm({
        title: '完善账号信息',
        content: '您的账号信息还未完善哦～',
        ok: () => {
          const router = useRouter()
          router.push('/user-profile')
        },
        okText: '去完善',
        cancelText: '以后再说',
      })
    }
  }

  function getHasLogin() {
    return computed(() => {
      const userRole = user.value?.userRole ?? ACCESS_ENUM.NOT_LOGIN
      return userRole !== ACCESS_ENUM.NOT_LOGIN
    })
  }

  /**
   * 自动登录
   */
  async function autoLogin() {
    const result = await AuthApi.getLoginUser()
    updateUser(result.data)
  }

  /**
   * 退出登录
   */
  async function logout(message = true) {
    const toast = useToast()
    const result = await AuthApi.logout()
    if (result.data === true) {
      if (message)
        toast.error('退出成功')
      updateUser(null)
      const route = useRoute()
      const router = useRouter()
      // 如果退出登录后当前页面没有权限，则跳转至首页
      if (!checkAccess(null, route.meta.access))
        router.push('/')

      return
    }
    if (message)
      toast.error('退出失败，请重试！')
  }

  return {
    user,
    updateUser,
    getHasLogin,
    autoLogin,
    logout,
  }
})

if (import.meta.hot)
  import.meta.hot.accept(acceptHMRUpdate(useAuthStore as unknown as StoreDefinition, import.meta.hot))
