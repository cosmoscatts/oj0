<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import gemoji from '@bytemd/plugin-gemoji'
import math from '@bytemd/plugin-math'
import mermaid from '@bytemd/plugin-mermaid'
import { Editor } from '@bytemd/vue-next'
import zhHans from 'bytemd/locales/zh_Hans.json'
import highlightjs from 'highlight.js'

const { mode = 'split' } = defineProps<{
  mode?: string
}>()

const value = defineModel<string>()

onMounted(() => {
  useTimeoutFn(highlightjs.highlightAll, 200)
})
/**
 * 定义编辑器需要用到的插件
 */
const plugins = [
  gfm(),
  gemoji(),
  math(),
  mermaid(),
]

function handleChange(newValue: string) {
  value.value = newValue
}

const themeClass = computed(() => isDark.value ? 'hljs-atom-one-dark' : 'hljs-atom-one-light')
</script>

<template>
  <ClientOnly>
    <div w-full class="markdown-body" :class="themeClass">
      <Editor
        :value="value"
        :plugins="plugins"
        :mode="mode"
        :locale="zhHans"
        @change="handleChange"
      />
    </div>
  </ClientOnly>
</template>

<style scoped>
:deep(.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child) {
  display: none;
}
</style>
