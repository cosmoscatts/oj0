<script setup lang="ts">

</script>

<template>
  <a-dropdown trigger="click">
    <NavAvatar cursor-pointer :size="28" />
    <template #content>
      <a-doption>个人中心</a-doption>
      <a-doption>退出登陆</a-doption>
    </template>
  </a-dropdown>
</template>
