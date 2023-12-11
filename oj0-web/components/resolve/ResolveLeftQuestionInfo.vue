<script setup lang="ts">
import { QUESTION_DIFFICULTY_ENUM } from '~/constants'

const { title = '', content = '', tags = [], hasSubmited = false, hasResolved = false } = defineProps<{
  title?: string
  content?: string
  tags?: string[]
  difficulty?: string
  hasSubmited?: boolean // 是否有提交记录
  hasResolved?: boolean // 是否解答
}>()
</script>

<template>
  <div flex="~ col">
    <div h-50px flex-y-center justify-between px-3 py-2>
      <div text-2xl font-bold text-base>
        {{ title }}
      </div>
      <div flex-y-center justify-end>
        <template v-if="hasSubmited">
          <div v-if="hasResolved" flex-center gap-2>
            <div>已解答</div>
            <div i-carbon-checkmark-outline text-green />
          </div>
          <div v-else flex-center gap-2>
            <div i-carbon-recording text-primary />
            <div>尝试过</div>
          </div>
        </template>
      </div>
    </div>

    <div flex-y-center flex-wrap gap-2 px-2>
      <template v-if="difficulty">
        <div v-if="difficulty === QUESTION_DIFFICULTY_ENUM.EASY" border-1 border-base rounded px-1.5 text-green>
          简单
        </div>
        <div v-if="difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM" border-1 border-base rounded px-1.5 text-orange>
          中等
        </div>
        <div v-if="difficulty === QUESTION_DIFFICULTY_ENUM.HARD" border-1 border-base rounded px-1.5 text-red>
          困难
        </div>
      </template>
      <template v-if="tags.length">
        <div v-for="tag in tags" :key="tag" border-1 border-base rounded px-1.5>
          {{ tag }}
        </div>
      </template>
    </div>

    <div flex="1 center" min-500px mt-5 of-y-auto px-3>
      <ViewerMarkdown :value="content" />
    </div>
  </div>
</template>