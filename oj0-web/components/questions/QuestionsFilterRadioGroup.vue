<script setup lang="ts">
import { popularQuestionTags as tags } from '~/config/question'

const value = defineModel<string>()

function isActive(tag: string) {
  return tag === value.value
}

const icons = [
  'i-ri-brackets-line',
  'i-ri-text',
  'i-ri-code-line',
  'i-ri-database-2-line',
  'i-ri-split-cells-horizontal',
  'i-ri-links-line',
  'i-ri-threads-line', // fallback 默认 icon
]

function getIcon(index: number) {
  if (index < 0 || index >= icons.length)
    return icons[icons.length]
  return icons[index]
}
</script>

<template>
  <div flex="~ gap4 wrap" gap-4>
    <button v-for="tag, idx in tags" :key="tag" flex="~ gap-1 center" border="~ base rounded" :class="isActive(tag) ? 'pointer-events-none' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="value = tag">
      <span :class="getIcon(idx)" /> {{ tag }}
    </button>
  </div>
</template>
