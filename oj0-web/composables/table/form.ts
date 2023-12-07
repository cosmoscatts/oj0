import type { UnwrapNestedRefs, UnwrapRef } from 'vue'

export function useTableForm<T = object, K = Partial<T>>({
  getBaseForm,
  refForm,
}: {
  getBaseForm: () => K
  refForm: Ref<any>
}) {
  const baseForm = ref<K>(getBaseForm())
  const form = toReactive(baseForm) as UnwrapNestedRefs<T>

  const assign = (data: K) => {
    baseForm.value = data as UnwrapRef<K>
  }

  const ok = (fn?: () => void) => {
    refForm.value.validate((errors: any) => {
      if (errors)
        return
      fn?.()
    })
  }

  const reset = (data?: K) => {
    baseForm.value = data && Object.keys(data).length
      ? data as UnwrapRef<K>
      : getBaseForm() as UnwrapRef<K>
    refForm.value?.clearValidate()
  }

  return {
    form,
    assign,
    ok,
    reset,
  }
}
