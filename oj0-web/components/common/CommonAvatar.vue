<script setup lang="ts">
const { size = 50, shape = 'circle', user } = defineProps<{
  user?: User
  size?: number
  shape?: 'circle' | 'square'
}>()

const textAvatar = computed(() => getTextAvatar(user))
</script>

<template>
  <div select-none>
    <a-avatar v-if="user?.userAvatar" :size="size" :shape="shape">
      <img alt="头像" :src="user.userAvatar">
    </a-avatar>
    <a-avatar v-else :size="size" :shape="shape">
      <span>
        {{ textAvatar }}
      </span>
    </a-avatar>
  </div>
</template>
