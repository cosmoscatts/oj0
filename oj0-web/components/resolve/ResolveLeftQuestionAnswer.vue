<script setup lang="ts">
const { answer = '' } = defineProps<{
  answer?: string
}>()
</script>

<template>
  <div of-y-auto px-3 py-10px>
    <ViewerMarkdown :value="answer" />
  </div>
</template>
