<script setup lang="ts">
const route = useRoute()
const router = useRouter()

const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')
function onMenuClick(path = '/') {
  router.push(path)
}

const selectedKeys = computed(() => {
  return [route.path]
})
</script>

<template>
  <div h-full w-full>
    <a-menu :selected-keys="selectedKeys" mode="horizontal" :theme="themeMode" :style="{ backgroundColor: 'transparent' }">
      <a-menu-item v-for="menu in menus" :key="menu.path ?? String(menu.id)" @click="onMenuClick(menu.path)">
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
