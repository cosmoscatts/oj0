<script setup lang="ts">
import { useToast } from 'vue-toastification'
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Home',
  title: '首页 - OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const toast = useToast()

const emojiArray = [
  '\\(o_o)/',
  '(o^^)o',
  '(˚Δ˚)b',
  '(^-^*)',
  '(^_^)b',
  '(╯‵□′)╯',
  '(=\'X\'=)',
  '(>_<)',
  '\\(°ˊДˋ°)/',
  'ㄟ(▔▽▔)ㄏ',
]
function getEmoji(): string {
  return emojiArray[Math.floor(Math.random() * emojiArray.length)]
}

onMounted(() => useLottie({
  containerId: '#lottie',
  path: 'https://lottie.host/87e660f6-9b48-43fe-878e-daa18eab5ebb/7uuyUSkDbI.json',
}))

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
  <div flex="col center" h-full min-h-600px w-full>
    <div id="lottie" h-300px w-400px />
    <div mt-30px text-center font-bold>
      <h1 text="3xl" m="t-2 b-2">
        Hello, {{ getEmoji() }}.
      </h1>
      <p m="t-3 b-2" mx-a w-40vw text-2xl>
        纵有疾风起，人生不言弃！
      </p>
    </div>
  </div>
</template>
