<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Home',
  access: ACCESS_ENUM.NOT_LOGIN,
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
  <div h-full w-full font-bold>
    <div w-800px flex justify-between px-30px>
      <div text-4rem>
        Hi@{{ name }} <span text-primary-active>1111</span>
      </div>
    </div>
  </div>
</template>
