<script setup lang="ts">
const refForm = ref()
const form = reactive({
  id: undefined,
  userOldPassword: '', // 旧密码
  userNewPassword: '', // 新密码
  checkNewPassword: '', // 确认新密码
})
</script>

<template>
  <div max-w-600px lt-md:w-full>
    <a-form ref="refForm" :model="form" auto-label-width size="large" mt-4>
      <a-form-item
        field="userOldPassword" label="原密码" :rules="[
          { required: true, message: '原密码是必须的' },
          { minLength: 6, message: '用户原密码长度必须大于6' },
        ]"
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
          { minLength: 6, message: '新密码长度必须大于6' },
        ]"
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
          { minLength: 6, message: '确认密码长度必须大于6' },
        ]"
      >
        <a-input-password v-model="form.checkNewPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item>
        <button w-full btn-solid>
          提交
        </button>
      </a-form-item>

      <a-divider />

      <a-form-item mt-4>
        <template #label>
          <div flex-center gap-2>
            <div i-ri-smartphone-line />
            手机号
          </div>
        </template>
        <div w-full flex-y-center justify-between>
          <div flex="1 center">
            未绑定
          </div>
          <button btn-text>
            绑定
          </button>
        </div>
      </a-form-item>

      <a-form-item>
        <template #label>
          <div flex-center gap-2>
            <div i-ri-mail-line />
            邮箱
          </div>
        </template>
        <div w-full flex-y-center justify-between>
          <div flex="1 center">
            未绑定
          </div>
          <button btn-text>
            绑定
          </button>
        </div>
      </a-form-item>

      <a-form-item>
        <template #label>
          <div flex-center gap-2>
            <div i-ri-github-line />
            GitHub
          </div>
        </template>
        <div w-full flex-y-center justify-between>
          <div flex="1 center">
            未绑定
          </div>
          <button btn-text>
            绑定
          </button>
        </div>
      </a-form-item>

      <a-form-item>
        <template #label>
          <div flex-center gap-2>
            <div i-ri-qq-line />
            QQ
          </div>
        </template>
        <div w-full flex-y-center justify-between>
          <div flex="1 center">
            未绑定
          </div>
          <button btn-text>
            绑定
          </button>
        </div>
      </a-form-item>

      <a-form-item>
        <template #label>
          <div flex-center gap-2>
            <div i-ri-wechat-line />
            微信
          </div>
        </template>
        <div w-full flex-y-center justify-between>
          <div flex="1 center">
            未绑定
          </div>
          <button btn-text>
            绑定
          </button>
        </div>
      </a-form-item>
    </a-form>
  </div>
</template>
