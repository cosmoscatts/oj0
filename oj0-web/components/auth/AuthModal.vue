<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const { type = AUTH_ACTION_ENUM.LOGIN } = defineProps<{
  type?: string
}>()
const visible = defineModel<boolean>()
</script>

<template>
  <a-modal v-model:visible="visible" :footer="false" :closable="false" width="25rem">
    <AuthLoginForm />
  </a-modal>
</template>
