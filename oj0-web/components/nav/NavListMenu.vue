<script setup lang="ts">
const route = useRoute()

const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')

const defaultSelectedKeys = ref<string[]>([route.path])
</script>

<template>
  <div h-full w-full>
    <a-menu mode="horizontal" :default-selected-keys="defaultSelectedKeys" :theme="themeMode" :style="{ backgroundColor: 'transparent' }">
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
</template>

<style scoped>
:deep(.arco-menu-item) {
  background-color: transparent;
}
</style>
