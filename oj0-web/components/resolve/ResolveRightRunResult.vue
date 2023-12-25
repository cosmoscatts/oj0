<script setup lang="ts">
const { submitId } = defineProps<{
  submitId?: number | string | null
}>()

let timer: NodeJS.Timeout | null = null

const submitInfo = ref<QuestionSubmit>()

async function fetchSubmitInfo() {
  if (!submitId)
    return
  const { data } = await QuestionSubmitApi.getById({ id: submitId })
  if (!submitInfo.value || [2, 3].includes(data.status))
    submitInfo.value = data

  if (data && [0, 1].includes(data.status) && !timer) {
    timer = setInterval(fetchSubmitInfo, 3 * 1000)
  }
  else {
    if (timer && [2, 3].includes(data.status)) {
      clearInterval(timer)
      timer = null
    }
  }
}
fetchSubmitInfo()
watch(() => submitId, fetchSubmitInfo)

const authStore = useAuthStore()
const userName = computed(() => authStore.user?.userName || '匿名用户')

onBeforeUnmount(() => {
  if (timer) {
    clearInterval(timer)
    timer = null
  }
})
</script>

<template>
  <div flex="~ col" h-full w-full p-3 text-base>
    <div v-if="!submitInfo" h-full flex-center>
      请先提交题目代码
    </div>
    <template v-else>
      <div v-if="submitInfo.status === 0" h-full flex="~ col">
        <div flex-y-center gap-2 text-sm>
          <NavAvatar :key="getRandomStr(10)" :size="16" />
          <span font-bold>{{ userName }}</span>提交于 <span>{{ formatDate(submitInfo.createTime, 'YYYY-MM-DD HH:mm') }}</span>
        </div>
        <div flex="1 center" gap-2>
          <div i-ri-loader-2-line animate-spin />
          等待中
        </div>
      </div>
      <div v-else-if="submitInfo.status === 1" h-full flex="~ col">
        <div flex-y-center gap-2 text-sm>
          <NavAvatar :key="getRandomStr(10)" :size="16" />
          <span font-bold>{{ userName }}</span>提交于 <span>{{ formatDate(submitInfo.createTime, 'YYYY-MM-DD HH:mm') }}</span>
        </div>
        <div flex="1 center" gap-2 text-orange>
          <div i-ri-loader-2-line animate-spin />
          判题中
        </div>
      </div>
      <div v-else-if="submitInfo.status === 3" h-full flex="~ col" gap-3>
        <div flex-y-center gap-2 text-red>
          <div i-ri-error-warning-line />
          判题失败
        </div>
        <div flex-y-center gap-2 text-sm>
          <NavAvatar :key="getRandomStr(10)" :size="16" />
          <span font-bold>{{ userName }}</span>提交于 <span>{{ formatDate(submitInfo.createTime, 'YYYY-MM-DD HH:mm') }}</span>
        </div>
        <div w-full gap-2 rounded-10px bg-code p-3>
          {{ submitInfo.judgeInfo?.message || '系统错误' }}
        </div>
      </div>
      <div v-else flex="~ col" h-full min-h-250px>
        <div h-60px flex="~ col" gap-1>
          <div text-lg font-bold>
            <span v-if="checkQuestionAccepted(submitInfo)" text-green> 通过</span>
            <span v-else text-red>
              {{ getOptionsLabel(questionSubmitJudgeOptions, submitInfo.judgeInfo?.message) || '解答错误' }}
            </span>
          </div>
          <div flex-y-center gap-2 text-sm>
            <NavAvatar :key="getRandomStr(10)" :size="16" />
            <span font-bold>{{ userName }}</span>提交于 <span>{{ formatDate(submitInfo.createTime, 'YYYY-MM-DD HH:mm') }}</span>
          </div>
        </div>

        <div flex="~ 1 col" gap-3 border border-base rounded p-5>
          <div v-if="submitInfo.status === 3" rounded-10px bg-code p-3>
            {{ submitInfo.judgeInfo?.message || '解答错误' }}
          </div>

          <div flex="~ col" gap-2 rounded-10px bg-code p-3>
            <div flex-y-center gap-2>
              <div i-ri-time-line />执行用时
            </div>
            <div flex-y-center gap-2>
              <template v-if="submitInfo.judgeInfo?.time">
                <div text-4 font-bold>
                  {{ submitInfo.judgeInfo.time }}
                </div> ms
              </template>
              <div v-else text-4 font-bold>
                NA
              </div>
            </div>
          </div>

          <div flex="~ col" gap-2 rounded-10px bg-code p-3>
            <div flex-y-center gap-2>
              <div i-ri-cpu-line />消耗内存
            </div>
            <div flex-y-center gap-2>
              <template v-if="submitInfo.judgeInfo?.memory">
                <div text-4 font-bold>
                  {{ submitInfo.judgeInfo.memory }}
                </div> KB
              </template>
              <div v-else text-4 font-bold>
                NA
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>
