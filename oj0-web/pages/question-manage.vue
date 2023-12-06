<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'QuestionManage',
  access: ACCESS_ENUM.ADMIN, // 需要管理员权限
  // middleware: 'auth',
})

const columns = getQuestionManageTableColumns()

const tableData = ref<Question[]>([])

const { loading } = useLoading()

function fetchData() {

}
</script>

<template>
  <a-card title="题目管理" w-full>
    <a-table
      :columns="columns"
      :data="tableData"
      :loading="loading"
    />
  </a-card>
</template>
