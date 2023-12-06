<script setup lang="ts">
const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()

function search() {

}
</script>

<template>
  <CommonTableWrapper title="用户管理">
    <UserManageSearchForm @search="search" />

    <a-divider />

    <a-table
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
    />
  </CommonTableWrapper>
</template>
