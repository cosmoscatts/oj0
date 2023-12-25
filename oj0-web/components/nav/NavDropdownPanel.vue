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

const hasLogin = authStore.getHasLogin()
const userName = computed(() => hasLogin.value ? authStore.user?.userName || '默认昵称' : '请先登录')
const userProfile = computed(() => hasLogin.value ? authStore.user?.userProfile || '还未添加任何简介～' : '登录后即可查看~')
</script>

<template>
  <a-dropdown v-model:popup-visible="visible" trigger="click" :popup-max-height="false">
    <div v-if="!visible" i-ri-menu-line text-xl filter-saturate-0 btn-text />
    <div v-else i-ri-close-line text-xl filter-saturate-0 btn-text />
    <template #content>
      <div w-250px p-4>
        <div h-50px flex-y-center shrink-0 gap-3>
          <NavAvatar :key="getRandomStr(10)" />

          <div pl-3>
            <div text-xl font-bold>
              {{ userName }}
            </div>
            <div mt-1 text-primary>
              {{ userProfile }}
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
          <a-doption @click="checkProfile">
            <div flex-y-center gap-4>
              <div i-ri-profile-line />
              个人中心
            </div>
          </a-doption>
          <a-doption @click="checkSummary">
            <div flex-y-center gap-4>
              <div i-ri-bar-chart-2-line />
              做题统计
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
