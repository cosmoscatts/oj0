import type { PaginationProps } from '@arco-design/web-vue'

const basePagination: Pagination = {
  current: 1,
  pageSize: 20,
  total: 0,
}

export function useTablePagination(fetch = () => {}, opts = basePagination) {
  const pagination = reactive({ ...opts })
  const formatRowIndex = (idx: number) => {
    const { current, pageSize } = pagination
    return (current - 1) * pageSize + idx + 1
  }
  const paginationProps = computed<PaginationProps | boolean>(() => {
    return {
      ...pagination,
      showTotal: true,
      showJumper: true,
      showPageSize: true,
      pageSizeOptions: [10, 20, 50],
    }
  })
  const onPageChange = (current: number) => {
    pagination.current = current
    fetch()
  }
  const onPageSizeChange = (pageSize: number) => {
    pagination.pageSize = pageSize
    fetch()
  }
  const setPaginationTotal = (total = 0) => {
    pagination.total = total
  }
  return {
    pagination,
    paginationProps,
    formatRowIndex,
    onPageChange,
    onPageSizeChange,
    setPaginationTotal,
  }
}
