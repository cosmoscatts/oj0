<script setup lang="ts">
const emit = defineEmits<{
  (event: 'search'): void
}>()

const { form, reset, getSearchParams } = useTableSearch(() => ({
  difficulty: '',
  tags: [],
  title: '', // 题目标题
}))

function search() {
  emit('search')
}

defineExpose({ getSearchParams, reset })
</script>

<template>
  <CommonTableSearchForm :="{ form }" @search="search" @reset="reset">
    <a-col :span="8">
      <a-form-item field="difficulty" label="难度" hide-label>
        <a-select v-model="form.difficulty" placeholder="难度" :options="questionDifficultyOptions" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="tags" label="标签" hide-label>
        <a-select v-model="form.tags" placeholder="标签" multiple allow-search allow-clear :max-tag-count="10" :options="questionTagGroupOptions" />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="title" label="题目" hide-label>
        <a-input v-model="form.title" placeholder="题目" allow-clear />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
