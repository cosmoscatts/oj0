<script setup lang="ts">
defineProps<{
  title?: string
}>()
defineEmits<{
  (event: 'add'): void
}>()
</script>

<template>
  <div w-full>
    <div h-50px flex-y-center justify-between>
      <slot name="title">
        <div text-lg>
          {{ title }}
        </div>
      </slot>

      <slot name="extra">
        <div btn-solid @click="$emit('add')">
          新增
        </div>
      </slot>
    </div>

    <div mt-6 w-full>
      <slot />
    </div>
  </div>
</template>
