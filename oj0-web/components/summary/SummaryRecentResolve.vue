<script setup lang="ts">
const { userId } = defineProps<{
  userId?: string | number
}>()

const acceptedData = ref<QuestionSubmit[]>([])

async function fetchAcceptedData() {
  if (!userId)
    return
  const { data: { records } } = await QuestionSubmitApi.list({
    userId,
    status: 2,
    sortField: 'createTime',
    sortOrder: 'descend',
  })
  acceptedData.value = records?.filter(i => i.judgeInfo && i.judgeInfo?.message === 'Accepted').slice(0, 10) || []
}
fetchAcceptedData()
</script>

<template>
  <div min-h-200px min-w-500px border border-base rounded p-4>
    <div text-lg font-bold>
      最近通过
    </div>
    <div v-if="acceptedData.length" flex="~ col" mt-4 min-h-200px gap-3>
      <div v-for="item, idx in acceptedData" :key="item.id" flex-y-center justify-between rounded-10px :class="(idx & 1) === 0 ? 'bg-code' : ''" px-6 py-4>
        <div>{{ item.questionId }}</div>
        <div>{{ item.createTime }}</div>
      </div>
    </div>
    <div v-else h-150px flex-center>
      您最近没有提交记录
    </div>
  </div>
</template>
