<script setup lang="ts">
const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()
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
