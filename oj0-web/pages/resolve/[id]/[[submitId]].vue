<script setup lang="ts">
import { ACCESS_ENUM, RESOLVE_LANGUAGE_ENUM } from '~/constants'

definePageMeta({
  name: 'ResolveQuestion',
  access: ACCESS_ENUM.USER, // 需要用户权限
  layout: 'none',
  middleware: 'auth',
  pageTransition: false,
})

const route = useRoute()
const id = computed(() => route.params.id as string) // 题目 id
const submitId = computed(() => route.params.submitId as string) // 题目提交记录 id
const selectedLeftTab = ref(submitId.value ? 2 : 0) // 0 - 题目描述；1 - 题解；2 - 提交记录
const currentQuestion = ref<Question>()

async function fetchCurrentQuestion() {
  if (!id.value) {
    Message.error('题目 id 异常')
    return
  }
  const { data } = await QuestionApi.getVo({ id: id.value })
  currentQuestion.value = data
}
fetchCurrentQuestion()

const currentSubmitId = ref<Nullable<string | number>>()
const refResolveQuestionSubmitList = ref()
const refResolveRightRun = ref()
function submitQuestionCallback(id?: Nullable<string | number>) {
  currentSubmitId.value = id
  if (id)
    refResolveRightRun.value?.changeSelectedTab?.(1)
  if (selectedLeftTab.value === 2)
    refResolveQuestionSubmitList.value?.update()
}

const refResolveRightCodeEditor = ref()
function getSubmitData() {
  const code = refResolveRightCodeEditor.value?.getCode() || ''
  const language = refResolveRightCodeEditor.value?.getLanguage() || RESOLVE_LANGUAGE_ENUM.JAVA
  return {
    code,
    language,
    questionId: id.value,
  }
}

const clickSubmitCnt = ref(0)
const doSubmit = useThrottleFn(async () => {
  try {
    const { code, data, message } = await QuestionSubmitApi.submit(getSubmitData())
    if (code !== 0) {
      Message.error(message || '提交失败')
      clickSubmitCnt.value = 0
      return
    }
    Message.success('提交成功')
    submitQuestionCallback(data)
    clickSubmitCnt.value = 0
  }
  catch {
    Message.error('提交失败')
    clickSubmitCnt.value = 0
  }
}, 1000)

function onClickSubmit() {
  clickSubmitCnt.value += 1

  if (clickSubmitCnt.value > 1)
    Message.warning('短时间内不要点击太多次哦～')

  doSubmit()
}

/**
 * 判断是否从提交历史页面来的，submitId 存在就说明是自己提交的，可以查看代码
 */
function checkFormSubmitHistory() {
  submitQuestionCallback(submitId.value)
}
onMounted(checkFormSubmitHistory)
</script>

<template>
  <div h-screen w-screen bg-base>
    <ResolveActionBar @submit="onClickSubmit" />

    <div :style="{ height: `calc(100vh - 4.3rem)` }" of-hidden px-10px>
      <a-split :style="{ height: '100%', width: '100%', minWidth: '500px' }" rounded-8px bg-darker>
        <template #first>
          <div h-full w-full of-hidden flex="~ col" border="1 base" rounded-l-8px>
            <ResolveLeftSelectTabs v-model="selectedLeftTab" />

            <div flex="~ 1 col" of-auto>
              <CommonTransition name="layout">
                <ResolveLeftQuestionInfo v-if="selectedLeftTab === 0" :="currentQuestion" h-full />
                <ResolveLeftQuestionAnswer v-if="selectedLeftTab === 1" :="currentQuestion" />
                <ResolveLeftQuestionSubmitList v-if="selectedLeftTab === 2" :id="id" ref="refResolveQuestionSubmitList" />
              </CommonTransition>
            </div>
          </div>
        </template>
        <template #second>
          <div lt-md:w-full>
            <a-split direction="vertical" :style="{ height: 'calc(100vh - 4.3rem)', overflow: 'hidden' }" :default-size="0.92">
              <template #first>
                <div h-full w-full of-hidden border="1 base" rounded-tr-8px>
                  <ResolveRightCodeEditor ref="refResolveRightCodeEditor" :submit-id="submitId" />
                </div>
              </template>
              <template #second>
                <div h-full w-full of-hidden>
                  <ResolveRightRun ref="refResolveRightRun" :judge-config="currentQuestion?.judgeConfig" :submit-id="currentSubmitId" />
                </div>
              </template>
            </a-split>
          </div>
        </template>
      </a-split>
    </div>
  </div>
</template>
