<script setup lang="ts">
import type { SelectOptionData } from '@arco-design/web-vue'
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'SubmitHistory',
  title: '历史提交 - OJ0',
  access: ACCESS_ENUM.USER, // 需要用户权限
  middleware: 'auth',
})

const authStore = useAuthStore()
const refSearchForm = ref()
const columns = getSubmitHistoryTableColumns()
const tableData = ref<QuestionSubmit[]>([])
const paginator = useTablePagination(search, { current: 1, pageSize: 50 })
const isOnlyMy = ref(false) // 仅查看自己的提交
const { loading, startLoading, endLoading } = useLoading()

/**
 * 获取查询参数
 */
function getSearchParams() {
  const { current, pageSize } = paginator.pagination
  const searchParams = {
    current,
    pageSize,
    ...refSearchForm.value?.getSearchParams(),
    sortField: 'createTime',
    sortOrder: 'descend',
  }
  if (isOnlyMy.value === true)
    searchParams.userId = authStore.user?.id

  return searchParams
}

async function search(update = false) {
  if (!update)
    startLoading()
  const { data: { records, total } } = await QuestionSubmitApi.list(getSearchParams())
  tableData.value = records || []
  paginator.setPaginationTotal(Number(total || 0))
  if (!update)
    useTimeoutFn(endLoading, 500)
}
onMounted(search)

watch(isOnlyMy, () => {
  paginator.pagination.current = 1
  search()
})

const userMap = ref<Record<number, User>>({})
async function fetchUserMap() {
  const { data: { records } } = await UserApi.listVo()
  userMap.value = records?.reduce((prev, cur) => {
    prev[cur.id] = {
      id: cur.id,
      userName: cur.userName?.trim(),
      userAvatar: cur.userAvatar,
    }
    return prev
  }, {} as Record<number, User>)
}
fetchUserMap()

function getUser(record: QuestionSubmit) {
  if (!record.userId)
    return undefined
  return userMap.value[record.userId]
}

const questionOptions = ref<SelectOptionData[]>([])
async function fetchQuestionOptions() {
  const { data: { records } } = await QuestionApi.listVo({ pageSize: -1 })
  questionOptions.value = records?.map((i) => {
    return {
      value: i.id,
      label: i.title,
    }
  }) || []
}
fetchQuestionOptions()

const router = useRouter()
function checkoutQuestion(record: QuestionSubmit) {
  if (checkMySubmitRecord(record)) { // 自己提交的题目，可以查看代码
    router.push(`/resolve/${record.questionId}/${record.id}`)
  }
  else {
    router.push(`/resolve/${record.questionId}`)
  }
}

function checkoutUserProfile(record: QuestionSubmit) {
  if (!record.userId)
    return
  router.push(`/profile/${record.userId}`)
}

onMounted(() => {
  useIntervalFn(() => search(true), 5000)
})
</script>

<template>
  <CommonTableWrapper>
    <template #title>
      <div w-full flex-y-center justify-between>
        <div>历史提交记录</div>
        <div>
          <a-checkbox v-model="isOnlyMy">
            仅自己
          </a-checkbox>
        </div>
      </div>
    </template>

    <template #extra>
      <div />
    </template>
    <SubmitHistorySearchForm ref="refSearchForm" @search="search" />

    <a-divider />

    <a-table
      row-key="id"
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
      :pagination="paginator.paginationProps.value"
      @page-change="paginator.onPageChange"
      @page-size-change="paginator.onPageSizeChange"
    >
      <template #id="{ rowIndex }">
        {{ paginator.formatRowIndex(rowIndex) }}
      </template>
      <template #questionId="{ record }">
        <CommonTooltip :content="`${record.questionId}.${getOptionsLabel(questionOptions, record.questionId)}`">
          <div hover="underline underline-offset-2 text-primary" cursor-pointer @click="checkoutQuestion(record)">
            {{ getOptionsLabel(questionOptions, record.questionId) }}
          </div>
        </CommonTooltip>
      </template>
      <template #language="{ record }">
        <span select-none rounded-lg bg-dm px-3 py-1>
          {{ getOptionsLabel(questionResolveLanguageOptions, record.language) }}
        </span>
      </template>
      <template #status="{ record }">
        <div flex-center>
          <div v-if="record.status === 0" flex-center gap-2>
            <div i-svg-spinners-bars-rotate-fade />
            等待中
          </div>
          <div v-if="record.status === 1" flex-center gap-2 text-orange>
            <div i-svg-spinners-bars-rotate-fade />
            判题中
          </div>
          <div v-else-if="record.status === 2">
            <span v-if="checkQuestionAccepted(record)" text-green>
              通过
            </span>
            <span v-else text-red>
              {{ getOptionsLabel(questionSubmitJudgeOptions, record.judgeInfo?.message) || '解答错误' }}
            </span>
          </div>
          <div v-else-if="record.status === 3" text-red>
            判题失败
          </div>
          <div v-else />
        </div>
      </template>
      <template #useTime="{ record }">
        <div flex-center gap-1>
          <div i-ri-time-line />
          <template v-if="checkQuestionAccepted(record) && record.judgeInfo?.time">
            <div mt-0.5>
              {{ record.judgeInfo.time }} ms
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
      <template #useMemory="{ record }">
        <div flex-center gap-1>
          <div i-ri-cpu-line />
          <template v-if="checkQuestionAccepted(record) && record.judgeInfo?.memory">
            <div mt-0.5>
              {{ record.judgeInfo.memory }} KB
            </div>
          </template>
          <template v-else>
            <div mt-0.5>
              NA
            </div>
          </template>
        </div>
      </template>
      <template #userId="{ record }">
        <CommonTooltip content="点击查看用户主页">
          <div flex-center cursor-pointer gap-2 @click="checkoutUserProfile(record)">
            <div flex-center select-none>
              <a-avatar :size="16">
                <img
                  v-if="getUser(record)?.userAvatar"
                  alt="头像"
                  :src="getUser(record)!.userAvatar"
                >
                <span v-else>
                  {{ getTextAvatar(getUser(record)) }}
                </span>
              </a-avatar>
            </div>
            <span flex-y-center>{{ getUser(record)?.userName || '匿名用户' }}</span>
          </div>
        </CommonTooltip>
      </template>
      <template #createTime="{ record }">
        <div>
          {{ formatDate(record.createTime, 'YYYY-MM-DD HH:mm:ss') }}
        </div>
      </template>
    </a-table>
  </CommonTableWrapper>
</template>
