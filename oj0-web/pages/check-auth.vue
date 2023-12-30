<script setup lang="ts">
import { useToast } from 'vue-toastification'
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'CheckAuth',
  title: 'OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const toast = useToast()

const params = useUrlSearchParams('history')
onMounted(() => {
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
</script>

<template>
  <div>11</div>
</template>
