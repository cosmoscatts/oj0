<script setup lang="ts">
import { ACCESS_ENUM, QUESTION_DIFFICULTY_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  title: '题库 - OJ0',
  access: ACCESS_ENUM.USER, // 需要用户权限
  middleware: 'auth',
})

const router = useRouter()
const refSearchForm = ref()
const columns = getQuestionsTableColumns()
const selectedRadioValue = ref<string>()
const paginator = useTablePagination(search)

const tableData = ref<Question[]>()
const acceptedQuestionIds = ref<number[]>([]) // 已经通过的题号
const { loading, startLoading, endLoading } = useLoading()

/**
 * 获取查询参数
 */
function getSerchParams() {
  const { current, pageSize } = paginator.pagination
  const searchParams = {
    current,
    pageSize,
    ...refSearchForm.value?.getSearchParams(),
  }
  if (selectedRadioValue.value) {
    const tags = searchParams.tags || []
    const mergeTags = [...new Set([...tags, selectedRadioValue.value])]
    searchParams.tags = mergeTags
  }
  return searchParams
}

async function search() {
  startLoading()
  const { data: { records, total } } = await QuestionApi.listVo(getSerchParams())
  tableData.value = records || []
  paginator.pagination.total = Number(total || 0)
  useTimeoutFn(endLoading, 500)
}
onMounted(search)

watch(selectedRadioValue, () => {
  paginator.pagination.current = 1
  refSearchForm.value?.reset?.()
  search()
})

async function fetchAcceptedQuestionIds() {
  const { data } = await QuestionSubmitApi.listMyAcceptedQuestionIds()
  acceptedQuestionIds.value = data || []
}
fetchAcceptedQuestionIds()

function checkoutQuestion(record: Question) {
  router.push(`/resolve/${record.id}`)
}

async function checkoutRandomQuestion() {
  const { data, message } = await QuestionApi.getRandomQuestionId()
  if (!data) {
    Message.error(message || '获取随机题目发生异常')
    return
  }
  router.push(`/resolve/${data}`)
}

function calculateAcceptPercent(record: Question) {
  const { submitNum = 0, acceptedNum = 0 } = record
  if (submitNum === 0)
    return ''
  if (acceptedNum === 0)
    return '0 %'
  return `${(acceptedNum / submitNum * 100).toFixed(2)} %`
}

/**
 * 判断题目是否已经通过
 */
function checkHasAccepted(record: Question) {
  if (!record.id)
    return false
  return acceptedQuestionIds.value.includes(record.id)
}
</script>

<template>
  <div min-h-500px w-full>
    <QuestionsFilterRadioGroup v-model="selectedRadioValue" />

    <div grid="~ cols-10" mt-5 gap-4 lt-lg:hidden>
      <QuestionsSearchForm ref="refSearchForm" col-span-9 @search="search" />

      <div col-span-1 flex justify-end gap-3>
        <div mt-2px h-32px flex-center>
          <div flex-center gap-2 btn-solid @click="checkoutRandomQuestion">
            <button i-ri-shuffle-line />
            <span lt-xl:hidden>随机一题</span>
          </div>
        </div>
      </div>
    </div>

    <a-table
      mt-4
      row-key="id"
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
      :pagination="paginator.paginationProps.value"
      @page-change="paginator.onPageChange"
      @page-size-change="paginator.onPageSizeChange"
    >
      <template #state="{ record }">
        <div v-if="checkHasAccepted(record)" w-full flex-center text-lg>
          <div i-ri-check-line text-green />
        </div>
      </template>
      <template #id="{ record }">
        <a v-if="record.id" cursor-pointer hover="underline underline-offset-2 text-primary" @click="checkoutQuestion(record)">
          {{ record.id }}
        </a>
      </template>
      <template #title="{ record }">
        <a v-if="record.title" cursor-pointer hover="underline underline-offset-2 text-primary" @click="checkoutQuestion(record)">
          {{ record.title }}
        </a>
      </template>
      <template #answer="{ record }">
        <span select-none rounded-lg bg-dm px-3 py-1>
          {{ record.answer ? '已有题解' : '暂未发布' }}
        </span>
      </template>
      <template #acceptPercent="{ record }">
        {{ calculateAcceptPercent(record) }}
      </template>
      <template #tags="{ record }">
        <div v-if="record.tags.length" w-250px>
          <CommonTooltip :content="record.tags.reduce((prev: string, cur: string) => `${prev}, ${cur}`, '').slice(1)">
            <a-overflow-list>
              <a-tag v-for="tag in record.tags" :key="tag" bordered color="transparent" :style="{ color: 'var(--c-text-base)' }">
                {{ tag }}
              </a-tag>
            </a-overflow-list>
          </CommonTooltip>
        </div>
      </template>
      <template #difficulty="{ record }">
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.EASY" text-green>简单</span>
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.MEDIUM" text-orange>中等</span>
        <span v-if="record.difficulty === QUESTION_DIFFICULTY_ENUM.HARD" text-red>困难</span>
      </template>
      <template #action="{ record }">
        <CommonTooltip content="做题">
          <div w-full flex-center text-lg btn-text @click="checkoutQuestion(record)">
            <div i-ri-arrow-right-up-line />
          </div>
        </CommonTooltip>
      </template>
    </a-table>
  </div>
</template>
