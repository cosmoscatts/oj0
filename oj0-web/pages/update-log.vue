<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'UpdateLog',
  title: '更新日志 - OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})
</script>

<template>
  <div class="prose" mx-a h-full max-w-70rem min-h-600px lt-md:max-w-40rem>
    <ContentDoc path="/logs" />
  </div>
</template>
