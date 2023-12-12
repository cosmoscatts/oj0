<script setup lang="ts">
const authStore = useAuthStore()

onMounted(async () => {
  const result = await AuthApi.getLoginUser()
  authStore.updateUser(result.data)
})

function createArcoTheme() {
  if (isDark.value)
    document.body.setAttribute('arco-theme', 'dark')
  else document.body.removeAttribute('arco-theme')
}

watch(isDark, () => {
  createArcoTheme()
  setThemeColor()
}, { immediate: true })

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
