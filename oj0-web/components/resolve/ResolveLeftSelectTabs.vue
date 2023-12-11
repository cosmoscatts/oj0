<script setup lang="ts">
const modelValue = defineModel<number>()
</script>

<template>
  <div flex-y-center gap-3 border-b border-base px-3 py-2>
    <button flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 0 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="modelValue = 0">
      <span i-carbon-document /> 题目描述
    </button>
    <button flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 1 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="modelValue = 1">
      <span i-carbon-chemistry /> 题解
    </button>
    <button flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 2 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="modelValue = 2">
      <span i-carbon-view-next /> 提交记录
    </button>
  </div>
</template>
