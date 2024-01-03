<script setup lang="ts">
const refForm = ref()
const form = reactive<Partial<User>>({
  id: undefined,
  userAccount: '', // 用户账号
  userName: '', // 用户昵称
  userAvatar: '', // 用户头像
  userProfile: '', // 用户简介
})

async function fetchMy() {
  const { data } = await AuthApi.getMy()
  form.id = data.id
  form.userAccount = data.userAccount
  form.userName = data.userName
  form.userAvatar = data.userAvatar
  form.userProfile = data.userProfile
}
fetchMy()

const previewAvatarVisible = ref(false)

const authStore = useAuthStore()
async function save() {
  const { code, message } = await AuthApi.updateMy(form)
  if (code !== 0) {
    Message.error(message || '提交失败')
    return
  }
  Message.success('提交成功')
  authStore.autoLogin()
}
</script>

<template>
  <div max-w-650px lt-md:w-full>
    <a-form ref="refForm" :model="form" auto-label-width size="large" mt-4>
      <a-form-item field="userAvatar" label="用户头像">
        <div w-full flex-center>
          <CommonAvatarUpload v-model:visible="previewAvatarVisible" v-model:avatar="form.userAvatar" />
        </div>
      </a-form-item>
      <a-form-item
        field="userAccount" label="用户账号" :rules="[
          { required: true, message: '用户账号是必须的' },
          { minLength: 5, message: '用户账号长度必须大于5' },
        ]" hide-asterisk
      >
        <a-input v-model="form.userAccount" allow-clear :disabled="!!form.userAccount" />
      </a-form-item>
      <a-form-item field="userName" label="用户昵称">
        <a-input v-model="form.userName" allow-clear />
      </a-form-item>
      <a-form-item
        field="userProfile" label="用户简介" :rules="[
          { maxLength: 20, message: '用户简介超出最大长度限制' },
        ]"
      >
        <a-textarea v-model="form.userProfile" :max-length="{ length: 20, errorOnly: true }" allow-clear show-word-limit />
      </a-form-item>
      <a-form-item>
        <button w-full btn-solid @click="save">
          提交
        </button>
      </a-form-item>
    </a-form>
  </div>
</template>
