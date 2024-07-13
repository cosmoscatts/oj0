<script setup lang="ts">
import { LAST_UPDATED } from '~/constants'
</script>

<template>
  <footer h-full w-full flex-center text-secondary>
    <span mx-2>构建于 {{ LAST_UPDATED }}</span>·<span mx-2>v0.1.9</span>·<a href="https://beian.miit.gov.cn/" mx-2 hover:underline>苏ICP备2023051139号</a>
  </footer>
</template>
