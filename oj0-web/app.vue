<script setup lang="ts">
const authStore = useAuthStore()

function createArcoTheme() {
  if (isDark.value)
    document.body.setAttribute('arco-theme', 'dark')
  else document.body.removeAttribute('arco-theme')
}

watch(isDark, () => {
  createArcoTheme()
  setThemeColor()
})

const route = useRoute()
watch(() => route.path, () => {
  const title = (route.meta.title ?? 'OJ0') as string
  useHead({ title })
}, { immediate: true })

async function checkLogin() {
  const result = await AuthApi.getLoginUser()
  authStore.updateUser(result.data)
}

onMounted(() => {
  createArcoTheme()
  setThemeColor()
  checkLogin()
})
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
