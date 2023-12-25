<script setup lang="ts">
const { size = 50, shape = 'circle' } = defineProps<{
  size?: number
  shape?: 'circle' | 'square'
}>()

const authStore = useAuthStore()
const currentUser = computed(() => authStore.user!)
</script>

<template>
  <common-avatar :size="size" :shape="shape" :user="currentUser" />
</template>
