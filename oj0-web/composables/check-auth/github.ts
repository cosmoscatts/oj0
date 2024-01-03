import { useToast } from 'vue-toastification'

export async function handleGithubLogin(code: string) {
  const toast = useToast()
  const router = useRouter()
  const authStore = useAuthStore()

  try {
    const result = await AuthApi.loginByGithub({ code })
    if (result.code !== 0) {
      Message.error(result.message ?? '登录失败')
      router.push('/')
      return
    }
    authStore.updateUser(result.data)
    const userName = result.data.userName ?? ''
    const content = userName === ''
      ? '欢迎回来！'
      : `${userName}, 欢迎回来！`
    toast.success(`登录成功, ${content}`)
    router.push('/')
    // 新题目提醒
    useTimeoutFn(() => doNewQuestionNotification(result.data.id), 5 * 1000)
  }
  catch {
    toast.error('登录失败')
    router.push('/')
  }
}

export async function handleGithubBound(code: string) {
  const toast = useToast()
  const router = useRouter()
  try {
    const result = await AuthApi.bindExtraAuth('github', { code })
    if (result.code !== 0) {
      Message.error(result.message ?? '绑定失败')
      router.push('/user-profile/account')
      return
    }
    toast.success('绑定成功')
    router.push('/user-profile/account')
  }
  catch {
    toast.error('绑定失败')
    router.push('/user-profile/account')
  }
}
