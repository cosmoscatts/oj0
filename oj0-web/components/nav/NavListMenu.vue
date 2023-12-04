<script setup lang="ts">
const route = useRoute()
const router = useRouter()

const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')
function onMenuClick(path = '/') {
  router.push(path)
}

const selectedKeys = computed(() => {
  return menus.value.filter(i => i.path === route.path)
    .map(i => i.path)
})
</script>

<template>
  <div h-full w-full>
    <a-menu mode="horizontal" :selected-keys="selectedKeys" :theme="themeMode" :style="{ backgroundColor: 'transparent' }" @menu-item-click="onMenuClick">
      <a-menu-item v-for="menu in menus" :key="menu.path">
        {{ menu.name }}
      </a-menu-item>
    </a-menu>
  </div>
</template>

<style scoped>
:deep(.arco-menu-item) {
  background-color: transparent;
}
</style>
