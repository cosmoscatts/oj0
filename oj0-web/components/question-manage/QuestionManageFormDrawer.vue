<script setup lang="ts">
import { QUESTION_DIFFICULTY_ENUM } from '~/constants'

interface Params extends Partial<Question> {}

const { data = {} } = defineProps<{
  data?: Params
}>()
const emit = defineEmits<{
  (event: 'save', data: Params): void
}>()
const visible = defineModel<boolean>()

const isAdd = computed(() => !data?.id)
const title = computed(() => ['添加题目', '编辑题目'][Number(!isAdd.value)])

const refForm = ref()

function getBaseJudgeConfig() {
  return {
    timeLimit: undefined,
    memoryLimit: undefined,
    stackLimit: undefined,
  } as JudgeConfig
}

function getBaseJudgeCase() {
  return {
    input: '',
    ouput: '',
  } as JudgeCase
}

function getBaseForm() {
  return {
    id: undefined,
    title: '',
    content: '',
    difficulty: '',
    tags: [], // 标签
    answer: '',
    judgeCase: [getBaseJudgeCase()], // 判题用例
    judgeConfig: getBaseJudgeConfig(), // 判题配置
  } as Params
}

const {
  form,
  assign,
  ok,
  reset,
} = useTableForm<Params>({
  getBaseForm,
  refForm,
})

watch(visible, (val) => {
  if (val) {
    reset()
    if (isAdd.value) {
      assign(getBaseForm())
    }
    else {
      const res = clone(data)
      if (!res.judgeConfig || !Object.keys(res.judgeConfig).length)
        res.judgeConfig = getBaseJudgeConfig()
      if (!res.judgeCase?.length)
        res.judgeCase = [getBaseJudgeCase()]

      assign(res)
    }
  }
})

function handleOk() {
  ok(() => {
    emit('save', clone(form))
  })
}

const questionDifficultyOptions = Array.from({ length: 3 }, (_, idx) => {
  return {
    value: [QUESTION_DIFFICULTY_ENUM.EASY, QUESTION_DIFFICULTY_ENUM.MEDIUM, QUESTION_DIFFICULTY_ENUM.HARD][idx],
    label: ['简单', '中等', '困难'][idx],
  }
})

function addJudgeCase() {
  if (!form.judgeCase?.length) {
    form.judgeCase = [getBaseJudgeCase()]
    return
  }
  form.judgeCase.push(getBaseJudgeCase())
}

function removeJudgeCase(index: number) {
  if (!form.judgeCase?.length)
    return
  form.judgeCase?.splice(index, 1)
}
</script>

<template>
  <CommonFormDrawer v-model="visible" :width="900" :title="title" @ok="handleOk" @reset="reset(data)">
    <a-form ref="refForm" :model="form" auto-label-width size="large" mt-4>
      <a-form-item
        field="title" label="题目标题" :rules="[
          { required: true, message: '题目标题是必须的' },
        ]"
      >
        <a-input v-model="form.title" allow-clear />
      </a-form-item>
      <a-form-item
        field="difficulty" label="题目难度" :rules="[
          { required: true, message: '题目难度是必须的' },
        ]"
      >
        <a-select v-model="form.difficulty" allow-clear :options="questionDifficultyOptions" />
      </a-form-item>
      <a-form-item field="tags" label="题目标签">
        <a-select v-model="form.tags" multiple allow-create allow-search allow-clear :options="[]" />
      </a-form-item>
      <a-form-item field="content" label="题目内容">
        <EditorMarkdown v-model="form.content" w-full />
      </a-form-item>
      <a-form-item field="content" label="题目答案">
        <EditorMarkdown v-model="form.answer" w-full />
      </a-form-item>
      <a-form-item field="judgeCase" label="判题用例" :content-flex="false">
        <template v-for="(item, idx) in form.judgeCase" :key="idx">
          <CommonTransition>
            <div grid="~ cols-13 gap-2">
              <a-form-item field="judgeConfig.timeLimit" label="输入用例" col-span-6>
                <a-input v-model="item.input" allow-clear />
              </a-form-item>
              <a-form-item field="judgeConfig.timeLimit" label="输出用例" col-span-6>
                <a-input v-model="item.output" allow-clear />
              </a-form-item>
              <div col-span-1 mb-20px w-full flex-y-center justify-end>
                <CommonTooltip content="删除该组用例">
                  <div btn-action-icon @click="removeJudgeCase(idx)">
                    <div i-ri-delete-bin-6-line />
                  </div>
                </CommonTooltip>
              </div>
            </div>
          </CommonTransition>
        </template>
        <div btn-text @click="addJudgeCase">
          新增
        </div>
      </a-form-item>
      <a-form-item field="judgeConfig" label="判题配置">
        <a-form-item field="judgeConfig.timeLimit" label="时间限制">
          <a-input-number v-model="form.judgeConfig!.timeLimit" allow-clear hide-button :min="0">
            <template #suffix>
              ms
            </template>
          </a-input-number>
        </a-form-item>
        <a-form-item field="judgeConfig.timeLimit" label="内存限制">
          <a-input-number v-model="form.judgeConfig!.memoryLimit" allow-clear hide-button :min="0">
            <template #suffix>
              KB
            </template>
          </a-input-number>
        </a-form-item>
        <a-form-item field="judgeConfig.timeLimit" label="堆栈限制">
          <a-input-number v-model="form.judgeConfig!.stackLimit" allow-clear hide-button :min="0">
            <template #suffix>
              KB
            </template>
          </a-input-number>
        </a-form-item>
      </a-form-item>
    </a-form>
  </CommonFormDrawer>
</template>
