<script setup lang="ts">
import { META } from '~/constants'
</script>

<template>
  <footer h-full w-full flex-center>
    <span mx-1>构建于 2 周前 </span>·<span mx-1>v0.1.1</span>·<a :href="META.github" mx-1 hover:underline>{{ META.author }}</a>·<a :href="META.repo" mx-1 hover:underline>GitHub</a>
  </footer>
</template>
