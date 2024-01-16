<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Summary',
  title: '个人统计 - OJ0',
  access: ACCESS_ENUM.USER,
  middleware: 'auth',
})

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const id = computed(() => route.params?.id ? route.params.id : undefined)
const userId = computed(() => id.value
  ? id.value as string
  : authStore.user?.id
    ? String(authStore.user?.id)
    : undefined,
)

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

// 是否是本人
const isSelf = computed(() => userId.value === authStore.user?.id)
</script>

<template>
  <div mxa max-w-70rem>
    <div mb-2 me-8 flex-y-center>
      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
    </div>

    <div flex="~ col" mxa mt-4 max-w-50rem gap-3>
      <div my-20px h-50px flex justify-between>
        <div h-full flex-y-center gap-5>
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

        <div h-57px flex items-end text-right text-sm text-base>
          <span mb-2px>{{ `${isSelf ? '您' : 'TA'}在` }}</span>
          <span mx-1 mb-2px font-bold>{{ `${formatDateAgo(userInfo?.createTime)}` }}</span>
          <span mb-2px>加入了OJ0</span>
        </div>
      </div>

      <div grid="~ cols-2" gap-3>
        <SummaryResolveQuestionInfo col-span-1 lt-sm:col-span-2 :user-id="userId" />
        <SummaryUserInfo col-span-1 lt-sm:col-span-2 :user-id="userId" />
      </div>
      <SummaryYearChart :user-id="userId" />
      <SummaryRecentResolve :user-id="userId" />
    </div>
  </div>
</template>
