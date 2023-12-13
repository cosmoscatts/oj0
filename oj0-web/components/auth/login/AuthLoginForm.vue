<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const authStore = useAuthStore()
const closeAuthModal = inject<() => void>('closeAuthModal')

const form = reactive({
  userAccount: '',
  userPassword: '',
})

const changeActionType = inject<(type: string) => void>('changeActionType')
function gotoRegister() {
  changeActionType?.(AUTH_ACTION_ENUM.REGISTER)
}

const refForm = ref()
function submit() {
  refForm.value.validate(async (errors: any) => {
    if (errors)
      return
    const result = await AuthApi.login(form)
    if (result.code !== 0) {
      Message.error(result.message ?? '登录失败')
      return
    }
    authStore.updateUser(result.data)
    closeAuthModal?.()
    const userName = result.data.userName ?? ''
    const content = userName === ''
      ? '欢迎回来！'
      : `${userName}, 欢迎回来！`
    ANotification.success({
      title: '登录成功',
      content,
      duration: 3000,
    })
  })
}
</script>

<template>
  <div flex="~ col" w-full px-2 pt-2>
    <div>
      <div text-xl font-bold>
        登录
      </div>
      <div mt-2 text-sm text-secondary>
        欢迎来到 OJ0！
      </div>
    </div>

    <AuthExtraLinks mt-3 />

    <a-divider />

    <div>
      <a-form ref="refForm" :model="form" layout="vertical" size="small">
        <a-form-item field="userAccount" label="用户账号" hide-asterisk :rules="[{ required: true, message: '用户账号是必须的' }]">
          <a-input v-model="form.userAccount" />
        </a-form-item>
        <a-form-item field="userPassword" label="用户密码" hide-asterisk :rules="[{ required: true, message: '用户密码是必须的' }]">
          <a-input-password v-model="form.userPassword" />
        </a-form-item>
        <a-form-item mt-2>
          <button w-full btn-solid @click="submit">
            提交
          </button>
        </a-form-item>
      </a-form>
    </div>

    <p mt-2 text-sm text-secondary>
      没有账号？<a cursor-pointer text-base underline underline-offset-2 hover:text-primary @click="gotoRegister">注册一个</a>
    </p>
  </div>
</template>

<style scoped>
:deep(.arco-form-item) {
  margin-bottom: 8px;
}

:deep(.arco-form-item-layout-vertical > .arco-form-item-label-col) {
  margin-bottom: 0;
}

:deep(.arco-form-item-label-col > .arco-form-item-label) {
  color: var(--c-text-secondary);
}
</style>
