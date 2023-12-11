<script setup lang="ts">
const selectedTab = ref(0) // 0 - 测试用例；1 - 测试结果
</script>

<template>
  <div h-full w-full of-hidden flex="~ col" border="1 base">
    <div flex-y-center gap-3 border-b border-base px-3 py-2>
      <button flex="~ gap-1 center" border="~ base rounded" :class="selectedTab === 0 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="selectedTab = 0">
        <span i-ri-checkbox-line /> 测试用例
      </button>
      <button flex="~ gap-1 center" border="~ base rounded" :class="selectedTab === 1 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="selectedTab = 1">
        <span i-ri-terminal-line /> 运行结果
      </button>
    </div>

    <div flex="~ 1 col">
      1
    </div>
  </div>
</template>
