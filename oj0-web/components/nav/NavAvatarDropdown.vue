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
function checkSettings() {
  router.push('/settings')
}
function checkProfile() {
  router.push('/profile')
  visible.value = false
}
function checkInterface() {
  router.push('/settings/interface')
}
function checkFavorites() {
  router.push('/settings/favorites')
  visible.value = false
}
function checkSolutions() {
  router.push('/settings/solutions')
  visible.value = false
}
function checkResolveAnalysis() {
  router.push('/settings/resolve-analysis')
  visible.value = false
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
      <div w-280px p-4>
        <div h-50px flex-y-center shrink-0 gap-3>
          <NavAvatar :key="getRandomStr(10)" cursor-pointer @click="checkProfile" />

          <div pl-3>
            <div text-xl font-bold>
              {{ userName }}
            </div>
            <div mt-1 text-sm text-primary>
              {{ userProfile }}
            </div>
          </div>
        </div>

        <div mt-4 flex shrink-0 justify-between overflow-x-auto px-4 pb-3 md:grid md:grid-cols-3 md:gap-3 space-x-2 md:px-0 md:pb-0 md:space-x-0>
          <div h-20 w-20 flex shrink-0 flex-col cursor-pointer items-center justify-center rounded-13px bg-active hover:bg-border @click="checkFavorites">
            <div relative mb-1 h-9 w-10>
              <img src="https://static.leetcode.cn/cn-mono-assets/production/assets/starred.2e4c5ddc.png" w-full object-cover>
            </div>
            <div mb-2 text-xs text-secondary>
              收藏夹
            </div>
          </div>
          <div h-20 w-20 flex shrink-0 flex-col cursor-pointer items-center justify-center rounded-13px bg-active hover:bg-border @click="checkSolutions">
            <div relative mb-1 h-9 w-10>
              <img src="https://static.leetcode.cn/cn-mono-assets/production/assets/answer.9dab99b2.png" w-full object-cover>
            </div>
            <div mb-2 text-xs text-secondary>
              我的题解
            </div>
          </div>
          <div h-20 w-20 flex shrink-0 flex-col cursor-pointer items-center justify-center rounded-13px bg-active hover:bg-border @click="checkResolveAnalysis">
            <div relative mb-1 h-9 w-10>
              <img src="https://static.leetcode.cn/cn-mono-assets/production/assets/progress.106c8d89.png" w-full object-cover>
            </div>
            <div mb-2 text-xs text-secondary>
              做题分析
            </div>
          </div>
        </div>

        <a-divider />

        <div>
          <a-doption @click="checkSettings">
            <div flex-y-center gap-4>
              <div i-ri-settings-3-line />
              设置中心
            </div>
          </a-doption>
          <a-doption @click="checkInterface">
            <div flex-y-center gap-4>
              <div i-ri-compasses-2-line />
              外观
            </div>
          </a-doption>
          <a-doption @click="logout">
            <div flex-y-center gap-4>
              <div i-ri-logout-box-r-line />
              退出登录
            </div>
          </a-doption>
        </div>
      </div>
    </template>
  </a-dropdown>
</template>
