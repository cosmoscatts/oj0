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

const route = useRoute()
watch(() => route.path, () => {
  const title = (route.meta.title ?? 'OJ0') as string
  useHead({ title })
}, { immediate: true })
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
