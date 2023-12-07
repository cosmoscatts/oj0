<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Editor } from '@bytemd/vue-next'
import zhHans from '~/config/zh_Hans.json'

const { mode = 'split' } = defineProps<{
  mode?: string
}>()

const value = defineModel<string>()

/**
 * 定义编辑器需要用到的插件
 */
const plugins = [
  gfm(),
  highlight(),
]

function handleChange(newValue: string) {
  value.value = newValue
}
</script>

<template>
  <ClientOnly>
    <div w-full>
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
