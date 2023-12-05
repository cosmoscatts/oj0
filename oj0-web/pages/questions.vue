<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  access: ACCESS_ENUM.USER, // 需要用户权限
  // middleware: 'auth',
})
</script>

<template>
  <div h-300px w-full flex-center bg-red text-30px font-bold>
    题库
  </div>
</template>
