import type { UnwrapRef } from 'vue'

/**
 * 通用表格查询参数方法
 */
export function useTableSearch<T = any>(getBaseForm: () => T, resetForm = getBaseForm) {
  const baseForm = ref<T>(getBaseForm())
  const form = toReactive<T>(baseForm)
  return {
    form,
    reset() {
      baseForm.value = resetForm() as UnwrapRef<T>
    },
    getSearchParams() {
      return clone(form)
    },
  }
}
