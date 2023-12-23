<script setup lang="ts">
const emit = defineEmits<{
  (event: 'search'): void
}>()

const { form, reset, getSearchParams } = useTableSearch<Partial<{ questionId?: number, language?: string, status?: number }>>(() => ({
  questionId: undefined,
  language: '',
  status: undefined,
}))

function search() {
  emit('search')
}

defineExpose({ getSearchParams })
</script>

<template>
  <CommonTableSearchForm :="{ form }" @search="search" @reset="reset">
    <a-col :span="8">
      <a-form-item field="questionId" label="题目 id">
        <a-input-number v-model="form.questionId" allow-clear hide-button />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="language" label="编程语言">
        <a-select v-model="form.language" :options="questionResolveLanguageOptions" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="status" label="判题结果">
        <a-select v-model="form.status" :options="questionSubmitStatusOptions" allow-clear />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
