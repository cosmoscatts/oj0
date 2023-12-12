<script setup lang="ts">
const { size = 50 } = defineProps<{
  size?: number
}>()

const authStore = useAuthStore()
const currentUser = computed(() => authStore.user)

const textAvatar = computed(() => {
  let str = currentUser.value?.userName ?? ''
  if (str === '')
    str = currentUser.value?.userAccount ?? ''
  if (str === '')
    return 'X'
  return str.substring(0, 1)
})
</script>

<template>
  <div select-none>
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
