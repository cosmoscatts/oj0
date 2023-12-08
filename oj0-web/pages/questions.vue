<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  access: ACCESS_ENUM.USER, // 需要用户权限
  // middleware: 'auth',
})

const selectedRadioValue = ref<string>()
</script>

<template>
  <div min-h-500px w-full>
    <QuestionsFilterRadioGroup v-model="selectedRadioValue" />
  </div>
</template>
