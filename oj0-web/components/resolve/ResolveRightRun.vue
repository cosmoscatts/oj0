<script setup lang="ts">
const selectedTab = ref(0) // 0 - 测试用例；1 - 测试结果

function changeSelectedTab(tab: number) {
  selectedTab.value = tab
}

defineExpose({ changeSelectedTab })
</script>

<template>
  <div h-full w-full of-hidden flex="~ col" border="1 base" rounded-br-8px>
    <div flex-y-center gap-3 border-b border-base px-3 py-2>
      <button flex="~ gap-1 center" border="~ base rounded" :class="selectedTab === 0 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="selectedTab = 0">
        <span i-ri-checkbox-line /> 题目限制
      </button>
      <button flex="~ gap-1 center" border="~ base rounded" :class="selectedTab === 1 ? '' : 'filter-saturate-0'" ws-nowrap bg-base px2 py1 btn-text @click="selectedTab = 1">
        <span i-ri-terminal-line /> 运行结果
      </button>
    </div>

    <div flex="~ 1 col" of-x-hidden of-y-auto>
      <CommonTransition name="layout">
        <ResolveRightRunConfig v-if="selectedTab === 0" :="$attrs" />
        <ResolveRightRunResult v-else :="$attrs" />
      </CommonTransition>
    </div>
  </div>
</template>
