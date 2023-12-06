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
        <div text-lg font-bold>
          {{ title }}
        </div>
      </slot>

      <slot name="extra">
        <a-button type="primary" @click="$emit('add')">
          <template #icon>
            <IconPlus />
          </template>
          新增
        </a-button>
      </slot>
    </div>

    <a-card mt-4 w-full>
      <slot />
    </a-card>
  </div>
</template>
