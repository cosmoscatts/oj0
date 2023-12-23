<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'ResolveQuestion',
  access: ACCESS_ENUM.USER, // 需要用户权限
  layout: 'none',
  middleware: 'auth',
})

const route = useRoute()
const id = computed(() => route.params.id as string) // 题目 id
const selectedLeftTab = ref(0) // 0 - 题目描述；1 - 题解；2 - 提交记录
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
</script>

<template>
  <div h-screen w-screen>
    <ResolveActionBar />

    <div :style="{ height: `calc(100vh - 6rem)` }" mb-2 of-hidden px-30px>
      <a-split :style="{ height: '100%', width: '100%', minWidth: '500px' }">
        <template #first>
          <div h-full w-full of-hidden flex="~ col" border="1 base">
            <ResolveLeftSelectTabs v-model="selectedLeftTab" />

            <div flex="~ 1 col" of-auto>
              <CommonTransition name="layout">
                <ResolveLeftQuestionInfo v-if="selectedLeftTab === 0" :="currentQuestion" />
                <ResolveLeftQuestionAnswer v-if="selectedLeftTab === 1" :="currentQuestion" />
                <ResolveLeftQuestionSubmitList v-if="selectedLeftTab === 2" :id="id" />
              </CommonTransition>
            </div>
          </div>
        </template>
        <template #second>
          <div lt-md:w-full>
            <a-split direction="vertical" :style="{ height: 'calc(100vh - 6rem)', overflow: 'hidden' }">
              <template #first>
                <div h-full w-full of-hidden border="1 base">
                  <ResolveRightCodeEditor />
                </div>
              </template>
              <template #second>
                <div h-full w-full of-hidden border="1 base">
                  <ResolveRightRun :judge-config="currentQuestion?.judgeConfig" />
                </div>
              </template>
            </a-split>
          </div>
        </template>
      </a-split>
    </div>
  </div>
</template>
