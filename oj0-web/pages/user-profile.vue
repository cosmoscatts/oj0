<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'UserProfile',
  access: ACCESS_ENUM.USER,
  // middleware: 'auth',
})
</script>

<template>
  <div>
    个人设置
  </div>
</template>
