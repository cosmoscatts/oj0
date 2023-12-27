<script setup lang="ts">
</script>

<template>
  <h1 class="prose-h1" flex-y-center cursor-pointer gap-2>
    <a class="router-link-active router-link-exact-active" href="#/update-log#v016"><ContentSlot :use="$slots.default" unwrap="p" /></a>
    <div bg="green/20" select-none rounded-full px-2.5 py-1 text-5 border="1 success">
      Latest
    </div>
  </h1>
</template>
