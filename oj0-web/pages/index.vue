<script setup lang="ts">
const currentIdx = ref(1)
const data = Array.from({ length: 4 }, (_, idx) => {
  return `用户-${idx}`
})
const name = computed(() => {
  return data[currentIdx.value % 4]
})
</script>

<template>
  <div flex="~ center" h-screen w-screen font-bold>
    <div flex justify-between px-30px w-800px>
      <div text-red text-4rem>
        Hi@{{ name }}
      </div>

      <button btn-base @click="currentIdx++">
        Change
      </button>

      <a-button type="primary">
        哈哈哈
      </a-button>
    </div>
  </div>
</template>
