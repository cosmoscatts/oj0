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
</script>

<template>
  <div mb-5 me-8 h-14 w-full flex-y-center justify-between border-b border-base px-30px>
    <div flex-center gap-1>
      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
      <a-divider direction="vertical" />
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
      <button flex-center btn-solid @click="doSubmit">
        <div i-ri-upload-cloud-fill mr-2 />
        提交
      </button>
    </div>

    <div flex-y-center justify-end gap-3>
      <DarkToggle />
      <NavAvatar :key="getRandomStr(10)" :size="36" />
    </div>
  </div>
</template>
