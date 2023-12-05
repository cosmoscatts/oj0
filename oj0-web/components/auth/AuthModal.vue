<script setup lang="ts">
const visible = defineModel()
</script>

<template>
  <a-modal v-model:visible="visible" :footer="false" :closable="false">
    <div h-500px>
      Auth
    </div>
  </a-modal>
</template>
