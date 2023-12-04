<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'NoAuth',
  access: ACCESS_ENUM.NOT_LOGIN,
})
</script>

<template>
  <div>
    NoAuth
  </div>
</template>
