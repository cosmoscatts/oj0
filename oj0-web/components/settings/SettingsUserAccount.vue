<script setup lang="ts">
import { useToast } from 'vue-toastification'

const toast = useToast()

const refForm = ref()
const form = reactive({
  id: undefined,
  userOldPassword: '', // 旧密码
  userNewPassword: '', // 新密码
  checkNewPassword: '', // 确认新密码
})

function validatePasswordSame(value: string, callback: (error?: string) => void) {
  if (value !== form.userNewPassword)
    callback('两次输入的密码不一致')
}

const authStore = useAuthStore()

async function submit() {
  const { code, message } = await AuthApi.updateMyPassword(form)
  if (code !== 0) {
    Message.error(message || '提交失败')
    return
  }
  toast.warning('提交成功，请重新登录！')
  refForm.value?.resetFields?.()
  useTimeoutFn(() => authStore.logout(false), 500)
}
</script>

<template>
  <div max-w-650px pl3 lt-md:w-full>
    <a-form ref="refForm" :model="form" auto-label-width size="large" mt-4>
      <a-form-item
        field="userOldPassword" label="原密码" :rules="[
          { required: true, message: '原密码是必须的' },
          { minLength: 8, message: '用户原密码长度必须大于8' },
        ]" hide-asterisk
      >
        <a-input-password v-model="form.userOldPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="userNewPassword" label="新密码" :rules="[
          { required: true, message: '新密码是必须的' },
          { minLength: 8, message: '新密码长度必须大于8' },
        ]" hide-asterisk
      >
        <a-input-password v-model="form.userNewPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="checkNewPassword" label="确认密码" :rules="[
          { required: true, message: '确认密码是必须的' },
          { validator: validatePasswordSame },
        ]" validate-trigger="input" hide-asterisk
      >
        <a-input-password v-model="form.checkNewPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item>
        <button w-full btn-solid @click="submit">
          提交
        </button>
      </a-form-item>

      <a-divider />

      <SettingsUserAccountListExtraAuth />
    </a-form>
  </div>
</template>
