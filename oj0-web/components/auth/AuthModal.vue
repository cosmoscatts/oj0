<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const { type = AUTH_ACTION_ENUM.LOGIN } = defineProps<{
  type?: string
}>()
const visible = defineModel<boolean>()

const isLoginAction = computed(() => type === AUTH_ACTION_ENUM.LOGIN)
</script>

<template>
  <a-modal v-model:visible="visible" :footer="false" :closable="false" width="25rem">
    <div reactive>
      <div absolute right-3 top-3 rounded btn-action-icon @click="visible = false">
        <div i-ri-close-fill />
      </div>

      <AuthLoginForm v-if="isLoginAction" />
      <AuthRegisterForm v-else />
    </div>
  </a-modal>
</template>
