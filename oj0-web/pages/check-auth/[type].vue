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
const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const type = computed(() => route.params.type as string) // 第三方类型
const hasLogin = authStore.getHasLogin()
const action = computed(() => hasLogin.value ? 1 : 0) // 0 - 注册登录；1 - 绑定；

async function checkAuth() {
  const params = useUrlSearchParams('history')
  const code = params?.code as string

  if (!code || !type.value || !['github', 'gitee', 'qq', 'wechat'].includes(type.value)) {
    toast.error('参数错误，请重试！')
    router.push('/')
    return
  }

  toast.success('授权成功，正在校验···')

  if (!hasLogin.value)
    await authStore.autoLogin()

  if (type.value === 'github') {
    if (action.value === 0)
      handleGithubLogin(code)
    else
      handleGithubBound(code)
  }
  else if (type.value === 'gitee') {
    if (action.value === 0)
      handleGiteeLogin(code)
    else
      handleGiteeBound(code)
  }
  // else if (type.value === 'qq') {

  // }
  // else if (type.value === 'wechat') {

  // }
}

onMounted(checkAuth)

onMounted(() => useLottie({
  containerId: '#lottie',
  path: 'https://lottie.host/87e660f6-9b48-43fe-878e-daa18eab5ebb/7uuyUSkDbI.json',
}))
</script>

<template>
  <div h-screen w-screen flex="center col">
    <div id="lottie" mt--100px h-300px w-400px />
    <div mt-30px flex-center gap-2 text-3xl font-bold>
      <div i-svg-spinners-bars-rotate-fade text-3xl />
      正在验证中
      <div i-svg-spinners-3-dots-scale-middle text-3xl />
    </div>
  </div>
</template>
