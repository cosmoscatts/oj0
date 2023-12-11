<script setup lang="ts">
const selectedLanguage = ref('java')

const code = ref('hhhhh')
</script>

<template>
  <div h-full w-full of-hidden flex="~ col" border="1 base">
    <div flex-y-center justify-between gap-3 border-b border-base px-3 py-2>
      <button flex="~ gap-1 center" ws-nowrap>
        <span i-carbon-code text-green /> 代码
      </button>

      <div flex-y-center justify-end gap-3>
        <a-select v-model="selectedLanguage" :options="questionResolveLanguageOptions" size="mini" allow-clear w-100px rounded />

        <CommonTooltip content="还原到默认">
          <button i-carbon-reset mt-1 font-bold filter-saturate-0 />
        </CommonTooltip>
      </div>
    </div>

    <div flex="~ 1 col" p-3>
      <EditorCode v-model="code" :language="selectedLanguage" />
    </div>
  </div>
</template>
