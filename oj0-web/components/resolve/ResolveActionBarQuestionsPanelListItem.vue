<script setup lang="ts">
import { QUESTION_DIFFICULTY_ENUM } from '~/constants'

const { id, tags = [], currentQuestionId } = defineProps<{
  showTags?: boolean
  id?: number
  title?: string
  difficulty?: string
  tags?: string[]
  hasAccepted?: boolean
  hasSubmitted?: boolean
  currentQuestionId?: string
}>()

const isCurrent = computed(() => {
  return id && String(id) === String(currentQuestionId)
})
</script>

<template>
  <div flex="~ col" cursor-pointer border-b-1.5px border-base hover:bg-active duration-300 last:border-b-0>
    <div h-52px w-full flex items-center px-4 space-x-3>
      <div flex items-center>
        <div v-if="hasAccepted" i-bi-check-circle cursor-pointer text-3 text-green />
        <div v-else-if="hasSubmitted" i-bi-dash-circle cursor-pointer text-3 text-orange />
        <div v-else i-ri-circle-line cursor-pointer text-3 text-secondary />
      </div>
      <div relative h-full w-full flex items-center>
        <div w-0 flex flex-1 items-center space-x-2>
          <div class="max-w-[75%] font-bold text-base">
            <CommonTooltip :content="`${id}.${title}`">
              <div truncate :class="isCurrent ? 'text-primary' : ''">
                {{ title }}
              </div>
            </CommonTooltip>
          </div>
        </div>
        <p v-if="difficulty === QUESTION_DIFFICULTY_ENUM.EASY" text-14px text-green>
          简单
        </p>
        <p v-if="difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM" text-14px text-orange>
          中等
        </p>
        <p v-if="difficulty === QUESTION_DIFFICULTY_ENUM.HARD" text-14px text-red>
          困难
        </p>
      </div>
    </div>
    <div v-if="showTags" mb-4 ml-10 mt--2 space-x-1>
      <span v-for="tag in tags.slice(0, 4)" :key="tag" max-w-full min-h-24px min-w-24px inline-flex items-center rounded-full bg-dm px-2 text-xs>
        {{ tag }}
      </span>
      <span v-if="tags.length > 4" max-w-full min-h-24px min-w-24px inline-flex items-center rounded-full bg-dm px-2 text-xs>
        +{{ tags.length - 4 }}
      </span>
    </div>
  </div>
</template>
