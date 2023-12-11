<script setup lang="ts">
const route = useRoute()

const menus = getAppMenus()
const themeMode = computed(() => isDark.value ? 'dark' : 'light')

const selectKeys = ref<string[]>([route.path])
watch(() => route.path, (n) => {
  selectKeys.value = [n]
})
</script>

<template>
  <div h-full w-full>
    <a-menu mode="horizontal" :selected-keys="selectKeys" :theme="themeMode" :style="{ backgroundColor: 'transparent' }">
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
