<script setup lang="ts">
const { answer = '' } = defineProps<{
  answer?: string
}>()

const hasAnswer = computed(() => answer !== '')
</script>

<template>
  <div h-full of-y-auto px-3 py-10px>
    <ViewerMarkdown v-if="hasAnswer" :value="answer" />
    <div v-else h-full flex="center col">
      <div flex-center text-8rem op-10>
        <div i-ri-forbid-line />
      </div>
      <div mt-4 text-center text-xl>
        Sorry, 题解还未发布
      </div>
    </div>
  </div>
</template>
