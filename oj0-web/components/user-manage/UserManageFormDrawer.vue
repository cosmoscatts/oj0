<script setup lang="ts">
const visible = defineModel<boolean>()
</script>

<template>
  <CommonFormDrawer v-model="visible" title="添加用户">
    1
  </CommonFormDrawer>
</template>
