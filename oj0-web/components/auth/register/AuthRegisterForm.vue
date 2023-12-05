<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const form = reactive({
  userAccount: '',
  password: '',
  checkPass: '',
})

const changeActionType = inject<(type: string) => void>('changeActionType')
function gotoLogin() {
  changeActionType?.(AUTH_ACTION_ENUM.LOGIN)
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
      <a-form :model="form" layout="vertical" size="small">
        <a-form-item field="userAccount" label="用户账号">
          <a-input v-model="form.userAccount" />
        </a-form-item>
        <a-form-item field="password" label="用户密码">
          <a-input v-model="form.password" />
        </a-form-item>
        <a-form-item field="checkPass" label="确认密码">
          <a-input v-model="form.checkPass" />
        </a-form-item>
        <a-form-item mt-2>
          <a-button long type="primary">
            提交
          </a-button>
        </a-form-item>
      </a-form>
    </div>

    <p mt-2 text-sm text-secondary>
      已经注册？<a cursor-pointer text-base underline @click="gotoLogin">登录</a>
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

:deep(.arco-input-wrapper) {
  background-color: transparent;
  border-color: var(--c-border);
}

:deep(.arco-input-wrapper):focus-within {
  background-color: var(--c-bg-active);
  border-color: var(--c-primary-light);
}
</style>
