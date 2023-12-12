<script setup lang="ts">
const { judgeCase = [] } = defineProps<{
  judgeCase?: JudgeCase[]
}>()

const selectedCase = ref(0)
const caseItem = computed(() => {
  if (!judgeCase.length)
    return undefined
  return judgeCase[selectedCase.value]
})
</script>

<template>
  <div h-full w-full>
    <template v-if="!judgeCase.length">
      <div h-full w-full flex-center>
        <a-empty description="暂时还没有测试用例" />
      </div>
    </template>
    <template v-else>
      <div h-full w-full flex="~ col">
        <div min-h-50px flex="y-center wrap" px-3>
          <div v-for="_, idx in judgeCase" :key="idx" rounded-10 filter-saturate-0 btn-text :class="selectedCase === idx ? 'bg-code' : ''" @click="selectedCase = idx">
            Case {{ idx + 1 }}
          </div>
        </div>

        <div flex="1 col" p-3 text-base>
          <CommonTransition name="layout">
            <div :key="`case-${selectedCase}`" flex="~ col gap-3">
              <div>输入：</div>
              <p min-h-40px cursor-text rounded-lg bg-code px-3 py-10px>
                {{ caseItem?.input }}
              </p>
              <div>输出：</div>
              <p min-h-40px cursor-text rounded-lg bg-code px-3 py-10px>
                {{ caseItem?.output }}
              </p>
            </div>
          </CommonTransition>
        </div>
      </div>
    </template>
  </div>
</template>
