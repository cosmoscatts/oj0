<script setup lang="ts">
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
function getBaseForm() {
  return {
    id: undefined,
    title: '',
    content: '',
    difficulty: '',
    tags: [], // 标签
    answer: '',
    judgeCase: [], // 判题用例
    judgeConfig: [], // 判题配置
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
    assign([getBaseForm(), data][Number(!isAdd.value)])
  }
})
function handleOk() {
  ok(() => {
    emit('save', clone(form))
  })
}
</script>

<template>
  <CommonFormDrawer v-model="visible" :width="800" :title="title" @ok="handleOk" @reset="reset(data)">
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
        <a-input v-model="form.difficulty" allow-clear />
      </a-form-item>
      <!-- <a-form-item field="tags" label="题目标签">
        <a-input v-model="form.tags" allow-clear />
      </a-form-item> -->
      <a-form-item field="content" label="题目内容">
        <EditorMarkdown v-model="form.content" w-full />
      </a-form-item>
      <a-form-item field="content" label="题目答案">
        <EditorMarkdown v-model="form.answer" w-full />
      </a-form-item>
    </a-form>
  </CommonFormDrawer>
</template>
