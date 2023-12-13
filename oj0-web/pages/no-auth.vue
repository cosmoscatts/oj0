<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'NoAuth',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const authStore = useAuthStore()
const hasLogin = authStore.getHasLogin()
</script>

<template>
  <div :style="{ height: 'calc(100vh - 150px)' }" min-h-300px min-w-200px w-full flex-center text-base>
    <div v-if="!hasLogin" flex="~ col gap-2" items-center text-center>
      <div i-ri-compasses-line text-5xl /><span text-xl>请先登录OJ0</span>
    </div>
    <div v-else flex="~ col gap-2" items-center text-center>
      <div i-ri-forbid-line text-5xl /><span text-xl>您没有权限访问</span>
    </div>
  </div>
</template>
