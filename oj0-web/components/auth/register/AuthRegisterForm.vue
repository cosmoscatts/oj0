<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const form = reactive({
  userAccount: '',
  userPassword: '',
  checkPassword: '',
})

const changeActionType = inject<(type: string) => void>('changeActionType')
function gotoLogin() {
  changeActionType?.(AUTH_ACTION_ENUM.LOGIN)
}

function validatePasswordSame(value: string, callback: (error?: string) => void) {
  if (value !== form.userPassword)
    callback('两次输入的密码不一致')
}

const refForm = ref()
async function submit() {
  const errors = await refForm.value.validate()
  if (errors)
    return

  const result = await AuthApi.register(form)
  if (result.code !== 0) {
    Message.error(result.message ?? '注册失败')
    return
  }
  ANotification.success('注册成功，快去登录吧！')
  gotoLogin()
}
</script>

<template>
  <div flex="~ col" w-full px-2 pt-2>
    <div>
      <div text-xl font-bold>
        注册
      </div>
      <div mt-2 text-sm text-secondary>
        欢迎来到 OJ0！
      </div>
    </div>

    <AuthExtraLinks mt-3 />

    <a-divider />

    <div>
      <a-form ref="refForm" :model="form" layout="vertical" size="small">
        <a-form-item
          field="userAccount" label="用户账号" hide-asterisk :rules="[
            { required: true, message: '用户账号是必须的' },
            { minLength: 4, message: '用户账号不能少于4个字符' },
          ]"
        >
          <a-input v-model="form.userAccount" allow-clear />
        </a-form-item>
        <a-form-item
          field="userPassword" label="用户密码" hide-asterisk :rules="[
            { required: true, message: '用户密码是必须的' },
            { minLength: 8, message: '用户密码不能少于8位' },
          ]"
        >
          <a-input-password v-model="form.userPassword" allow-clear />
        </a-form-item>
        <a-form-item
          field="checkPassword" hide-asterisk label="确认密码" :rules="[
            { required: true, message: '确认密码是必须的' },
            { validator: validatePasswordSame },
          ]" validate-trigger="input"
        >
          <a-input-password v-model="form.checkPassword" allow-clear />
        </a-form-item>
        <a-form-item mt-2>
          <button w-full btn-solid @click="submit">
            提交
          </button>
        </a-form-item>
      </a-form>
    </div>

    <p mt-2 text-sm text-secondary>
      已经注册？<a cursor-pointer text-base underline underline-offset-2 hover:text-primary @click="gotoLogin">登录</a>
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
