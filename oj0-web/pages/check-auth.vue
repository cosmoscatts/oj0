<script setup lang="ts">
import { useToast } from 'vue-toastification'
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  layout: 'none',
  name: 'CheckAuth',
  title: 'OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const toast = useToast()

const params = useUrlSearchParams('history')
onMounted(() => {
  console.log(params)
  if (params?.code)
    handleGithubLogin(params.code as string)
})

const router = useRouter()
const authStore = useAuthStore()
async function handleGithubLogin(code: string) {
  const result = await AuthApi.loginByGithub({ code })

  if (result.code !== 0) {
    Message.error(result.message ?? '登录失败')
    return
  }
  authStore.updateUser(result.data)
  const userName = result.data.userName ?? ''
  const content = userName === ''
    ? '欢迎回来！'
    : `${userName}, 欢迎回来！`
  toast.success(`登录成功, ${content}`)
  const path = '/'
  router.replace(path)
  // 新题目提醒
  useTimeoutFn(() => doNewQuestionNotification(result.data.id), 5 * 1000)
}

onMounted(() => useLottie({
  containerId: '#lottie',
  path: 'https://lottie.host/1039c80e-7aa0-40df-9155-5a552cfd6d11/0bh8uqYidp.json',
}))
</script>

<template>
  <div h-screen w-screen flex="center col">
    <div id="lottie" mt--100px h-300px w-400px />
    <div mt-30px flex-center gap-2 text-3xl font-bold>
      <div i-ri-loader-2-line animate-spin text-3xl />
      正在验证中···
    </div>
  </div>
</template>
