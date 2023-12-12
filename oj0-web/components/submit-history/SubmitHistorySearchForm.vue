<script setup lang="ts">
const emit = defineEmits<{
  (event: 'search'): void
}>()

const { form, reset, getSearchParams } = useTableSearch<Partial<User>>(() => ({
  title: '',
  language: '',
  state: undefined,
}))

function search() {
  emit('search')
}

defineExpose({ getSearchParams })
</script>

<template>
  <CommonTableSearchForm :="{ form }" @search="search" @reset="reset">
    <a-col :span="8">
      <a-form-item field="title" label="题目">
        <a-input v-model="form.title" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="language" label="编程语言">
        <a-select v-model="form.language" :options="questionResolveLanguageOptions" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="state" label="判题结果">
        <a-select v-model="form.state" :options="questionSubmitStateOptions" allow-clear />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
