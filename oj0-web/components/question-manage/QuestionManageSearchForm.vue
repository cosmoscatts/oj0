<script setup lang="ts">
const emit = defineEmits<{
  (event: 'search'): void
}>()

const { form, reset, getSearchParams } = useTableSearch<Partial<Question>>(() => ({
  title: '',
  content: '',
  tags: [],
}))

function search() {
  emit('search')
}

defineExpose({ getSearchParams })
</script>

<template>
  <CommonTableSearchForm :="{ form }" @search="search" @reset="reset">
    <a-col :span="8">
      <a-form-item field="title" label="题目标题">
        <a-input v-model="form.title" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="content" label="题目内容">
        <a-input v-model="form.content" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="difficulty" label="题目难度">
        <a-select v-model="form.difficulty" :options="questionDifficultyOptions" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="16">
      <a-form-item field="tags" label="题目标签" :label-col-props="{ span: 3 }" :wrapper-col-props="{ span: 21 }">
        <a-select v-model="form.tags" :options="questionTagGroupOptions" multiple allow-clear ml--2px :scrollbar="false" :max-tag-count="8" />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
