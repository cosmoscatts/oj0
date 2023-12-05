<script setup lang="ts">
const { name = 'page' } = defineProps<{ name?: string }>()
</script>

<template>
  <Transition :name="name" mode="out-in" appear>
    <slot />
  </Transition>
</template>
