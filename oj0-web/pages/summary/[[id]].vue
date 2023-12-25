<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Summary',
  title: '做题统计 - OJ0',
  access: ACCESS_ENUM.USER,
  middleware: 'auth',
})

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const id = computed(() => route.params?.id ? route.params.id : undefined)
const userId = computed(() => id.value ? id.value : authStore.user?.id)

const userInfo = ref<User>()

async function getUserInfo() {
  if (!id.value) {
    const hasLogin = authStore.getHasLogin()
    await until(hasLogin).toBe(true, { timeout: 3000 })
  }
  if (!userId.value)
    return
  const { data } = await UserApi.getVo(String(userId.value))
  userInfo.value = data
}
getUserInfo()

function back() {
  router.go(-1)
}
</script>

<template>
  <div mxa max-w-70vw>
    <div mb-2 me-8 flex-y-center>
      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
    </div>

    <div mt-4 w-full flex="~ col" gap-3>
      <div my-20px h-50px flex-y-center gap-5>
        <CommonAvatar :key="getRandomStr(10)" :user="userInfo" :size="64" shape="square" />
        <div flex="~ col" shrink-0 gap-2>
          <div text-2xl font-bold>
            {{ userInfo?.userName || '匿名用户' }}
          </div>
          <div mt-1 text-sm text-primary>
            {{ userInfo?.userProfile || '还未添加任何简介～' }}
          </div>
        </div>
      </div>
      <div grid="~ cols-2" gap-3>
        <SummaryUserInfo col-span-1 lt-sm:col-span-2 />
        <SummaryResolveQuestionInfo col-span-1 lt-sm:col-span-2 />
      </div>
      <SummaryYearChart />
      <SummaryRecentResolve />
    </div>
  </div>
</template>
