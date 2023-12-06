<script setup lang="ts">
const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()
</script>

<template>
  <CommonTableWrapper title="题目管理">
    <a-table
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
    />
  </CommonTableWrapper>
</template>
