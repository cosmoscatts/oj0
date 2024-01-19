<script setup lang="ts">
const route = useRoute()

const visible = ref(false)
const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')

const defaultSelectedKeys = ref<string[]>([route.path])

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

const hasLogin = authStore.getHasLogin()
const userName = computed(() => hasLogin.value ? authStore.user?.userName || '默认昵称' : '请先登录')
const userProfile = computed(() => hasLogin.value ? authStore.user?.userProfile || '还未添加任何简介～' : '登录后即可查看~')
</script>

<template>
  <a-dropdown v-model:popup-visible="visible" trigger="click" :popup-max-height="false">
    <div v-if="!visible" i-ri-menu-line text-xl filter-saturate-0 btn-text />
    <div v-else i-ri-close-line text-xl filter-saturate-0 btn-text />
    <template #content>
      <div max-h-500px w-280px of-x-hidden of-y-auto p-4>
        <div h-50px flex-y-center shrink-0 gap-3>
          <NavAvatar :key="getRandomStr(10)" cursor-pointer @click="checkProfile" />

          <div pl-3>
            <div text-xl font-bold>
              {{ userName }}
            </div>
            <div mt-1 text-primary>
              {{ userProfile }}
            </div>
          </div>
        </div>

        <div mt-5 flex shrink-0 justify-between overflow-x-auto pb-3 md:grid md:grid-cols-3 md:gap-3 space-x-2 md:px-0 md:pb-0 md:space-x-0>
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

        <div w-full>
          <a-menu :theme="themeMode" :default-selected-keys="defaultSelectedKeys" :style="{ backgroundColor: 'transparent' }">
            <NuxtLink v-for="menu in menus" :key="menu.id" :to="menu.path">
              <a-menu-item :key="menu.path">
                <div flex-y-center gap-2>
                  <component :is="menu.icon" v-if="menu.icon" />
                  {{ menu.name }}
                </div>
              </a-menu-item>
            </NuxtLink>
          </a-menu>
        </div>

        <a-divider v-if="hasLogin" />

        <div v-if="hasLogin">
          <a-doption @click="checkSettings">
            <div flex-y-center gap-4>
              <div i-ri-settings-3-line />
              设置中心
            </div>
          </a-doption>
          <a-doption @click="checkProfile">
            <div flex-y-center gap-4>
              <div i-ri-profile-line />
              个人主页
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

<style scoped>
:deep(.arco-menu-item) {
  background-color: transparent;
}
</style>
