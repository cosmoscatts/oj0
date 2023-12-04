<script setup lang="ts">
const route = useRoute()

const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')

const defaultSelectedKeys = ref<string[]>([route.path])
</script>

<template>
  <div h-full w-full>
    <a-menu mode="horizontal" :default-selected-keys="defaultSelectedKeys" :theme="themeMode" :style="{ backgroundColor: 'transparent' }">
      <a-menu-item v-for="menu in menus" :key="menu.path">
        <RouterLink :to="menu.path">
          {{ menu.name }}
        </RouterLink>
      </a-menu-item>
    </a-menu>
  </div>
</template>

<style scoped>
:deep(.arco-menu-item) {
  background-color: transparent;
}
</style>
