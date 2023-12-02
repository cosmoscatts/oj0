<script setup lang="ts">
function createArcoTheme() {
  if (isDark.value)
    document.body.setAttribute('arco-theme', 'dark')
  else document.body.removeAttribute('arco-theme')
}

onMounted(() => {
  setThemeColor()
  createArcoTheme()
})

watch(isDark, () => {
  createArcoTheme()
  setThemeColor()
})
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
