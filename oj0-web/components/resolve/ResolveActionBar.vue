<script setup lang="ts">
const emit = defineEmits<{
  (event: 'submit'): void
}>()

const route = useRoute()
const router = useRouter()

function back() {
  router.go(-1)
}

const id = computed(() => route.params?.id as string)

async function checkoutRandomQuestion() {
  const { data, message } = await QuestionApi.getRandomQuestionId()
  if (!data) {
    Message.error(message || '获取随机题目发生异常')
    return
  }
  if (String(data) === id.value) {
    Message.warning('\\(o_o)/ 真是不巧，又刷到这题了呢～')
    return
  }
  router.replace(`/resolve/${data}`)
}

async function checkoutPrevQuestion() {
  if (!id)
    return

  const { data, message } = await QuestionApi.getPrevQuestionId({ id: id.value })
  if (!data) {
    Message.error(message || '获取上一题发生异常')
    return
  }
  router.replace(`/resolve/${data}`)
}

async function checkoutNextQuestion() {
  if (!id)
    return

  const { data, message } = await QuestionApi.getNextQuestionId({ id: id.value })
  if (!data) {
    Message.error(message || '获取下一题发生异常')
    return
  }
  router.replace(`/resolve/${data}`)
}

function doSubmit() {
  emit('submit')
}

const questionsPanelVisible = ref(false)
</script>

<template>
  <div me-8 h-14 w-full flex-y-center justify-between px-15px py-10px pl-20px>
    <div flex-center gap-1>
      <NavLogo short mr-4 h-20px />
      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
      <a-divider direction="vertical" :size="2" />
      <CommonTooltip content="展开面板">
        <div flex items-center gap-2 rounded-2 hover:bg-active py-1 font-bold filter-saturate-0 btn-text @click="questionsPanelVisible = true">
          <div i-ri-play-list-2-fill mt-0.5 />
          题库
        </div>
      </CommonTooltip>
      <CommonTooltip content="上一题">
        <div i-ri-arrow-left-s-line text-xl filter-saturate-0 btn-text @click="checkoutPrevQuestion" />
      </CommonTooltip>
      <CommonTooltip content="下一题">
        <div i-ri-arrow-right-s-line text-xl filter-saturate-0 btn-text @click="checkoutNextQuestion" />
      </CommonTooltip>
      <CommonTooltip content="随机一题">
        <div i-ri-shuffle-line filter-saturate-0 btn-text @click="checkoutRandomQuestion" />
      </CommonTooltip>
    </div>

    <div flex-center>
      <button flex-center py-1 btn-outline @click="doSubmit">
        <div i-ri-upload-cloud-fill mr-2 />
        提交
      </button>
    </div>

    <div flex-y-center justify-end gap-3>
      <DarkToggle />
      <NavAvatarDropdown :avatar-size="24" />
    </div>

    <ResolveActionBarQuestionsPanel v-model="questionsPanelVisible" />
  </div>
</template>
