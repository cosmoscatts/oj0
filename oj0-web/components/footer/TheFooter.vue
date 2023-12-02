<script setup lang="ts">
import { META } from '~/constants';
</script>

<template>
  <footer h-full w-full flex-center>
    构建于 2 周前 · v0.1.1 · <a :href="META.github" hover:underline>{{ META.author }}</a> · <a :href="META.repo" hover:underline>GitHub</a>
  </footer>
</template>
