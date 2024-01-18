<script setup lang="ts">
const route = useRoute()
watch(() => route.path, () => {
  const title = (route.meta.title ?? 'OJ0') as string
  useHead({ title })
  scrollTop('wrapper')
}, { immediate: true })

onMounted(() => {
  scrollTop('wrapper')
})
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
