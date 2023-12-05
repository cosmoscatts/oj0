import { ACCESS_ENUM } from '~/constants'

export const useAuthStore = defineStore('authStore', () => {
  const user = ref<Nullable<User>>(null)

  function updateUser(loginUser: User) {
    user.value = loginUser
  }

  function getHasLogin() {
    return computed(() => {
      const userRole = user.value?.userRole ?? ACCESS_ENUM.NOT_LOGIN
      return userRole !== ACCESS_ENUM.NOT_LOGIN
    })
  }

  return {
    user,
    updateUser,
    getHasLogin,
  }
})
