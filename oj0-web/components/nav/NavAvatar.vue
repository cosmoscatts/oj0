<script setup lang="ts">
const { size = 50 } = defineProps<{
  size?: number
}>()

const authStore = useAuthStore()
const currentUser = computed(() => authStore.user)

const textAvatar = computed(() => {
  const userName = currentUser.value?.userName ?? ''
  if (userName === '')
    return 'X'

  return userName.substring(0, 1)
})
</script>

<template>
  <div>
    <a-avatar :size="size">
      <img
        v-if="currentUser?.userAvatar"
        alt="头像"
        :src="currentUser.userAvatar"
      >
      <span v-else>
        {{ textAvatar }}
      </span>
    </a-avatar>
  </div>
</template>
