<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  title: '登录',
  layout: 'none',
  name: 'Login',
  access: ACCESS_ENUM.NOT_LOGIN,
})
</script>

<template>
  <div>Login</div>
</template>
