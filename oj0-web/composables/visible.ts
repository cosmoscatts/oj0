export function useVisible<T = any>(initValue = false) {
  const visible = ref(initValue)
  const data = ref<T>()
  return {
    visible,
    data,
    show(_data?: T) {
      data.value = _data
      visible.value = true
    },
    close() {
      data.value = undefined
      visible.value = false
    },
  }
}
