import type { EffectScope } from 'vue'

const STORAGE_KEY_SETTINGS = 'oj0-settings'

export function getDefaultUserSettings(): UserSettings {
  return {
    colorMode: 'system',
  }
}

export function useUserSettings() {
  const settingsStorage = useUserLocalStorage<UserSettings>(STORAGE_KEY_SETTINGS, () => getDefaultUserSettings())
  return settingsStorage
}

interface UseUserLocalStorageCache {
  scope: EffectScope
  value: Ref<Record<string, any>>
}

/**
 * Create reactive storage for the current user
 * @param key
 * @param initial
 */
export function useUserLocalStorage<T extends object>(key: string, initial: () => T): Ref<T> {
  // @ts-expect-error bind value to the function
  const map: Map<string, UseUserLocalStorageCache> = useUserLocalStorage._ = useUserLocalStorage._ || new Map()

  if (!map.has(key)) {
    const scope = effectScope(true)
    const value = scope.run(() => {
      const all = useLocalStorage<Record<string, T>>(key, {}, { deep: true })
      const authStore = useAuthStore()
      return computed(() => {
        const id = authStore.user?.id
          ? String(authStore.user?.id)
          : '[anonymous]'
        all.value[id] = Object.assign(initial(), all.value[id] || {})
        return all.value[id]
      })
    })
    map.set(key, { scope, value: value! })
  }

  return map.get(key)!.value as Ref<T>
}

/**
 * Clear all storages for the given id
 * @param id
 */
export function clearUserLocalStorage(id?: string | number) {
  if (!id) {
    const authStore = useAuthStore()
    id = authStore.user?.id
  }
  if (!id)
    return

  id = String(id)

  // @ts-expect-error bind value to the function
  const cacheMap = useUserLocalStorage._ as Map<string, UseUserLocalStorageCache> | undefined
  cacheMap?.forEach(({ value }) => {
    if (value.value[id!])
      delete value.value[id!]
  })
}
