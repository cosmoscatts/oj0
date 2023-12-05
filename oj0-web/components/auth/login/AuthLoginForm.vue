<script setup lang="ts">
const form = reactive({
  userName: '',
  password: '',
})
</script>

<template>
  <div flex="~ col" w-full px-2 py-4>
    <div>
      <div text-xl font-bold>
        登录
      </div>
      <div mt-2>
        欢迎来到 OJ0！
      </div>
    </div>

    <AuthExtraLinks mt-4 />

    <a-divider />

    <div border="1 red">
      <a-form :model="form" layout="vertical" size="small">
        <a-form-item field="userName" label="用户账号">
          <a-input v-model="form.userName" />
        </a-form-item>
        <a-form-item field="password" label="用户密码">
          <a-input v-model="form.password" />
        </a-form-item>
        <a-form-item>
          <a-button block>
            登录
          </a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
