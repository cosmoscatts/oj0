<script setup lang="ts">
import { META } from '~/constants'
</script>

<template>
  <footer h-full w-full flex-center text-secondary>
    <span mx-2>构建于 2 周前 </span>·<span mx-2>v0.1.1</span>·<a :href="META.github" mx-2 hover:underline>{{ META.author }}</a>·<a :href="META.repo" mx-2 hover:underline>GitHub</a>·<a href="https://beian.miit.gov.cn/" mx-2 hover:underline>苏ICP备2023051139号</a>
  </footer>
</template>
