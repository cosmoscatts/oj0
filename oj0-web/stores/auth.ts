export const useAuthStore = defineStore('authStore', () => {
  const user = ref<Nullable<User>>(null)

  function updateUser(loginUser: User) {
    user.value = loginUser
  }

  function checkLogin() {
    return !!user.value && !!user.value.userRole
  }

  return {
    user,
    updateUser,
    checkLogin,
  }
})
