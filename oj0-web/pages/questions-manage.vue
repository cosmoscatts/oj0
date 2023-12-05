<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'QuestionsManage',
  access: ACCESS_ENUM.ADMIN, // 需要管理员权限
  // middleware: 'auth',
})
</script>

<template>
  <a-card title="题目管理" w-full>
    11
  </a-card>
</template>
