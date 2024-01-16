<script setup lang="ts">
const visible = ref(false)

/**
 * 当屏幕宽度发生变化时，应关闭 dropdown
 */
const { width } = useWindowSize()
watch(width, () => {
  if (visible.value)
    visible.value = false
})

const router = useRouter()
function checkProfile() {
  router.push('/user-profile')
}
function checkSummary() {
  router.push('/summary')
}

const authStore = useAuthStore()
function logout() {
  authStore.logout()
}

const userName = computed(() => authStore.user?.userName || '默认昵称')
const userProfile = computed(() => authStore.user?.userProfile || '还未添加任何简介～')
</script>

<template>
  <a-dropdown v-model:popup-visible="visible" trigger="click" :popup-max-height="false">
    <NavAvatar :key="getRandomStr(10)" cursor-pointer :size="28" />
    <template #content>
      <div w-250px p-4>
        <div h-50px flex-y-center shrink-0 gap-3>
          <NavAvatar :key="getRandomStr(10)" />

          <div pl-3>
            <div text-xl font-bold>
              {{ userName }}
            </div>
            <div mt-1 text-sm text-primary>
              {{ userProfile }}
            </div>
          </div>
        </div>

        <a-divider />

        <div>
          <a-doption @click="checkProfile">
            <div flex-y-center gap-4>
              <div i-ri-profile-line />
              账号设置
            </div>
          </a-doption>
          <a-doption @click="checkSummary">
            <div flex-y-center gap-4>
              <div i-ri-bar-chart-2-line />
              个人统计
            </div>
          </a-doption>
          <a-doption @click="logout">
            <div flex-y-center gap-4>
              <div i-ri-logout-box-r-line />
              退出登陆
            </div>
          </a-doption>
        </div>
      </div>
    </template>
  </a-dropdown>
</template>
