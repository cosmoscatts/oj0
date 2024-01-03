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

function checkAuth() {
  const params = useUrlSearchParams('history')
  const code = params?.code as string

  if (!code || !type.value || !['github', 'qq', 'wechat'].includes(type.value)) {
    toast.error('参数错误，请重试！')
    router.push('/')
    return
  }

  toast.success('授权成功，正在校验···')

  if (type.value === 'github') {
    if (action.value === 0)
      handleGithubLogin(code)
    else
      handleGithubBound(code)
  }
  // else if (type.value === 'qq') {

  // }
  // else if (type.value === 'wechat') {

  // }
}
checkAuth()

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
