import type { AxiosResponse } from 'axios'
import type { UnwrapRef } from 'vue'

export function useRequest<T>(
  api: () => Promise<AxiosResponse<Result<T>>>,
  defaultValue = undefined as unknown as T,
  isLoading = true,
) {
  const { loading, endLoading } = useLoading(isLoading)
  const response = ref<T>(defaultValue)
  api()
    .then((res) => {
      response.value = res.data as unknown as UnwrapRef<T>
    })
    .finally(endLoading)
  return { loading, response }
}
