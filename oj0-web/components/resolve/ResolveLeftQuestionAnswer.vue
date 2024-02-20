<script setup lang="ts">
import empty from '~/public/empty.png'

const { answer = '' } = defineProps<{
  answer?: string
}>()

const hasAnswer = computed(() => answer !== '')
</script>

<template>
  <div h-full of-y-auto px-3 py-10px>
    <ViewerMarkdown v-if="hasAnswer" :value="answer" />
    <div v-else flex="center col" h-full min-h-420px min-w-300px py-50px>
      <div h-180px flex-center>
        <img :src="empty" w-200px select-none>
      </div>
      <div mt-5 text-center text-xl>
        Sorry, 题解还未发布
      </div>
    </div>
  </div>
</template>
