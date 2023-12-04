<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Editor } from '@bytemd/vue-next'

const { value = '' } = defineProps<{
  value?: string
}>()

const emit = defineEmits<{
  (event: 'change', value: string): void
}>()

/**
 * 定义编辑器需要用到的插件
 */
const plugins = [
  gfm(),
  highlight(),
]

function handleChange(value: string) {
  emit('change', value)
}
</script>

<template>
  <Editor :value="value" :plugins="plugins" @change="handleChange" />
</template>
