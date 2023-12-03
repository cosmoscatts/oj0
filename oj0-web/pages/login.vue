<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  layout: 'none',
  name: 'Login',
  access: ACCESS_ENUM.NOT_LOGIN,
})

useHead('')
</script>

<template>
  <div h-screen w-screen flex-center text-10rem>
    Login
  </div>
</template>
