<script setup lang="ts">
const refSearchForm = ref()

const columns = getUserManageTableColumns()

const tableData = ref<User[]>([])
const { loading } = useLoading()

function search() {

}

const drawerVisible = ref(false)

function add() {
  drawerVisible.value = true
}
</script>

<template>
  <CommonTableWrapper title="用户管理" @add="add">
    <UserManageSearchForm ref="refSearchForm" @search="search" />

    <a-divider />

    <a-table
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
    />

    <UserManageFormDrawer v-model="drawerVisible" />
  </CommonTableWrapper>
</template>
