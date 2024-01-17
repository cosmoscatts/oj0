<script setup lang="ts">
const visible = defineModel<boolean>()

const router = useRouter()
const showTags = ref(false)
const questions = ref<Question[]>([])

const authStore = useAuthStore()
const acceptedQuestionIds = ref<number[]>([]) // 已经通过的题号
const submittedQuestionIds = ref<number[]>([]) // 所有提交过题号

/**
 * TODO: 查询热门的 50 条题目（目前只查询最新的 50 条）
 */
async function fetchPopularQuestions() {
  const { data: { records } } = await QuestionApi.listVo({
    current: 1,
    pageSize: 50,
    sortField: 'createTime',
    sortOrder: 'descend',
  })
  questions.value = records || []
}
fetchPopularQuestions()

async function fetchAcceptedQuestionIds() {
  const { data } = await QuestionSubmitApi.listMyAcceptedQuestionIds()
  acceptedQuestionIds.value = data || []
}
fetchAcceptedQuestionIds()

async function fetchSubmittedQuestionIds() {
  const { data: { records } } = await QuestionSubmitApi.list({
    userId: authStore.user?.id,
    pageSize: -1,
  })
  submittedQuestionIds.value = records?.reduce((prev, cur) => {
    if (!!cur.questionId && !prev.includes(cur.questionId))
      prev.push(cur.questionId)

    return prev
  }, [] as number[]) || []
}
fetchSubmittedQuestionIds()

/**
 * 判断题目是否已经通过
 */
function checkHasAccepted(record: Question) {
  if (!record.id)
    return false
  return acceptedQuestionIds.value.includes(record.id)
}

/**
 * 判断题目是否已经提交
 */
function checkHasSubmitted(record: Question) {
  if (!record.id)
    return false
  if (checkHasAccepted(record))
    return false
  return submittedQuestionIds.value.includes(record.id)
}

const route = useRoute()
const currentQuestionId = computed(() => route.params?.id as string)
</script>

<template>
  <a-drawer
    :width="600"
    :visible="visible"
    :footer="false"
    placement="left"
    unmount-on-close
    @cancel="visible = false"
  >
    <template #header>
      <div w-full py-5>
        <div w-full flex items-center justify-between>
          <div w-500px>
            <div flex-inline cursor-pointer items-center @click="router.push('/questions')">
              <div truncate text-xl font-bold>
                题库
              </div>
              <div i-ri-arrow-right-s-line text-xl filter-saturate-0 btn-text />
            </div>
          </div>

          <div i-ri-close-line text-xl filter-saturate-0 btn-text @click="visible = false" />
        </div>
      </div>
    </template>

    <div of-auto>
      <div flex="~ col" relative>
        <div flex items-end justify-between px-1>
          <div mb-18px flex items-center gap-3>
            <div flex flex-1 flex-col items-start gap-2 of-hidden>
              <div w-full flex items-center gap-1 text-lg font-medium text-base>
                为你推荐
              </div>
              <div text-xs leading-22px>
                为你精选近期热门题目
              </div>
            </div>
          </div>
          <div mb-4.5>
            <a-checkbox v-model="showTags" select-none>
              显示标签
            </a-checkbox>
          </div>
        </div>

        <div w-full flex="~ col" gap-4>
          <div w-full of-hidden border-1 border-base rounded-lg>
            <ResolveActionBarQuestionsPanelListItem
              v-for="item, idx in questions"
              :key="item.id"
              class="slide-enter" :style="{
                '--enter-stage': idx + 1,
                '--enter-step': '120ms',
              }"
              v-bind="{
                id: item.id,
                title: item.title,
                tags: item.tags,
                difficulty: item.difficulty,
                hasAccepted: checkHasAccepted(item),
                hasSubmitted: checkHasSubmitted(item),
                showTags,
                currentQuestionId,
              }"
              @click="router.push(`/resolve/${item.id}`)"
            />
          </div>
        </div>
      </div>
    </div>
  </a-drawer>
</template>
