<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Viewer } from '@bytemd/vue-next'

const { value = '' } = defineProps<{
  value?: string
}>()

/**
 * 定义编辑器需要用到的插件
 */
const plugins = [
  gfm(),
  highlight(),
]
</script>

<template>
  <ClientOnly>
    <div w-full>
      <Viewer
        :value="value"
        :plugins="plugins"
      />
    </div>
  </ClientOnly>
</template>
