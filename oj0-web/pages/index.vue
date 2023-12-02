<script setup lang="ts">
definePageMeta({
  layout: 'default',
  // middleware: 'auth',
})

const currentIdx = ref(1)
const data = Array.from({ length: 4 }, (_, idx) => {
  return `用户-${idx}`
})
const name = computed(() => {
  return data[currentIdx.value % 4]
})
</script>

<template>
  <div flex-center w-full h-full font-bold>
    <div flex justify-between px-30px w-800px>
      <div text-4rem>
        Hi@{{ name }} <span text-primary-active>1111</span>
      </div>

      <div>
        <a-button type="primary" @click="currentIdx++">
          Change
        </a-button>
        <a-button type="primary" status="success" @click="currentIdx++">
          Change
        </a-button>
        <a-button type="primary" status="warning" @click="currentIdx++">
          Change
        </a-button>
        <a-button type="primary" status="danger" @click="currentIdx++">
          Change
        </a-button>
      </div>
    </div>
  </div>
</template>
