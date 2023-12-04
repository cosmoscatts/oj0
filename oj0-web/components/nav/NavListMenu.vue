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
    .map(i => String(i.id)).filter(i => i != null)
})
</script>

<template>
  <div h-full w-full>
    <a-menu mode="horizontal" :selected-keys="selectedKeys" :theme="themeMode" :style="{ backgroundColor: 'transparent' }">
      <a-menu-item v-for="menu in menus" :key="String(menu.id)" @click="onMenuClick(menu.path)">
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
