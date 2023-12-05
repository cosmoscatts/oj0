<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'QuestionsManage',
  access: ACCESS_ENUM.ADMIN, // 需要管理员权限
  // middleware: 'auth',
})
</script>

<template>
  <div h-300px w-full flex-center bg-red text-30px font-bold>
    题目管理
  </div>
</template>
