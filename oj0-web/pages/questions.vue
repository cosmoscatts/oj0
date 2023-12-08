<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  access: ACCESS_ENUM.USER, // 需要用户权限
  // middleware: 'auth',
})

const columns = getQuestionsTableColumns()

const selectedRadioValue = ref<string>()

const { loading } = useLoading()

const paginator = useTablePagination(search)

const tableData = ref<Question[]>()
function search() {

}
</script>

<template>
  <div min-h-500px w-full>
    <QuestionsFilterRadioGroup v-model="selectedRadioValue" />

    <a-table
      mt-4
      row-key="id"
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
      :pagination="paginator.paginationProps.value"
      @page-change="paginator.onPageChange"
      @page-size-change="paginator.onPageSizeChange"
    >
      <template #tags="{ record }">
        <div v-if="record.tags.length" w-200px>
          <a-overflow-list>
            <a-tag v-for="tag in record.tags" :key="tag" bordered color="transparent" :style="{ color: 'var(--c-text-base)' }">
              {{ tag }}
            </a-tag>
          </a-overflow-list>
        </div>
      </template>
    </a-table>
  </div>
</template>
