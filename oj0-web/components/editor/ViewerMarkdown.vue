<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import gemoji from '@bytemd/plugin-gemoji'
import math from '@bytemd/plugin-math'
import mermaid from '@bytemd/plugin-mermaid'
import { Viewer } from '@bytemd/vue-next'
import highlightjs from 'highlight.js'

const { value = '' } = defineProps<{
  value?: string
}>()

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

const themeClass = computed(() => isDark.value ? 'hljs-atom-one-dark' : 'hljs-atom-one-light')
</script>

<template>
  <ClientOnly>
    <div w-full class="markdown-body" :class="themeClass">
      <Viewer
        :value="value"
        :plugins="plugins"
      />
    </div>
  </ClientOnly>
</template>
