<script setup lang="ts">
const { judgeConfig = {} } = defineProps<{
  judgeConfig?: JudgeConfig
}>()

const timeLimit = computed(() => judgeConfig?.timeLimit ? `${judgeConfig.timeLimit}ms` : 'NA')
const memoryLimit = computed(() => judgeConfig?.memoryLimit ? `${judgeConfig.memoryLimit}KB` : 'NA')
const stackLimit = computed(() => judgeConfig?.stackLimit ? `${judgeConfig.stackLimit}KB` : 'NA')
</script>

<template>
  <div flex="~ col" h-full min-h-250px w-full p-3 text-base>
    <div flex="~ col gap-2">
      <div>时间限制：</div>
      <p min-h-40px flex-y-center cursor-text rounded-lg bg-code px-3 py-10px>
        <span i-ri-time-line mr-2 />
        {{ timeLimit }}
      </p>
      <div>内存限制：</div>
      <p min-h-40px flex-y-center cursor-text rounded-lg bg-code px-3 py-10px>
        <span i-ri-cpu-line mr-2 />
        {{ memoryLimit }}
      </p>
      <div>堆栈限制：</div>
      <p min-h-40px flex-y-center cursor-text rounded-lg bg-code px-3 py-10px>
        <span i-ri-code-box-line mr-2 />
        {{ stackLimit }}
      </p>
    </div>
  </div>
</template>
