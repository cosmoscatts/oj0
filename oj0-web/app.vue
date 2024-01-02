<script setup lang="ts">
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
  scrollTop('wrapper')
}, { immediate: true })

const router = useRouter()
function checkExtraLoginCallBackUrl() {
  const params = useUrlSearchParams('history')
  const code = params?.code
  if (code)
    router.push(`/check-auth/${code}`)
}

onMounted(() => {
  createArcoTheme()
  setThemeColor()
  checkExtraLoginCallBackUrl()
  scrollTop('wrapper')
})
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
