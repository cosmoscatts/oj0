<script setup lang="ts">
const emit = defineEmits<{
  (event: 'search'): void
}>()

const { form, reset, getSearchParams } = useTableSearch<Partial<User>>(() => ({
  userName: '',
  userRole: '',
}))

function search() {
  emit('search')
}

defineExpose({ getSearchParams })
</script>

<template>
  <CommonTableSearchForm :="{ form }" @search="search" @reset="reset">
    <a-col :span="8">
      <a-form-item field="userName" label="用户名称">
        <a-input v-model="form.userName" allow-clear />
      </a-form-item>
    </a-col>
    <a-col :span="8">
      <a-form-item field="userRole" label="用户角色">
        <a-select v-model="form.userRole" :options="userRoleOptions" allow-clear />
      </a-form-item>
    </a-col>
  </CommonTableSearchForm>
</template>
