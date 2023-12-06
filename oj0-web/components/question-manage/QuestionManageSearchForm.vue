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

const options = Array.from({ length: 20 }, (_, idx) => {
  return {
    value: idx + 1,
    label: `标签${idx + 1}`,
  }
})
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
      <a-form-item field="tags" label="题目标签">
        <a-select v-model="form.tags" :options="options" multiple :allow-search="false" allow-clear :scrollbar="false" :max-tag-count="5" />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
