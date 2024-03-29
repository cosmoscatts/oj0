<script setup lang="ts">
interface Params extends Partial<User & { checkPassword?: string }> {}

const { data = {} } = defineProps<{
  data?: Params
}>()
const emit = defineEmits<{
  (event: 'save', data: Params): void
}>()
const visible = defineModel<boolean>()

const isAdd = computed(() => !data?.id)
const title = computed(() => ['添加用户', '编辑用户'][Number(!isAdd.value)])

const refForm = ref()
function getBaseForm() {
  return {
    id: undefined,
    userAccount: '', // 用户账号
    userName: '', // 用户昵称
    unionId: '', // 开放平台id
    mpOpenId: '', // 公众号openId
    userAvatar: '', // 用户头像
    userProfile: '', // 用户简介
    userRole: '', // 用户角色 notLogin/user/admin
    userPassword: '', // 用户密码
    checkPassword: '', // 确认密码
  } as Params
}
const {
  form,
  assign,
  ok,
  reset,
} = useTableForm<Params>({
  getBaseForm,
  refForm,
})
watch(visible, (val) => {
  if (val) {
    reset()
    assign([getBaseForm(), data][Number(!isAdd.value)])
  }
})
function handleOk() {
  ok(() => {
    emit('save', clone(form))
  })
}

const previewAvatarVisible = ref(false)

function validatePasswordSame(value: string, callback: (error?: string) => void) {
  if (value !== form.userPassword)
    callback('两次输入的密码不一致')
}
</script>

<template>
  <CommonFormDrawer v-model="visible" :width="600" :title="title" @ok="handleOk" @reset="reset(data)">
    <a-form ref="refForm" :model="form" auto-label-width size="large" mt-4>
      <a-form-item field="userAvatar" label="用户头像">
        <div w-full flex-center>
          <CommonAvatarUpload v-model:visible="previewAvatarVisible" v-model:avatar="form.userAvatar" />
        </div>
      </a-form-item>
      <a-form-item
        field="userAccount" label="用户账号" :rules="[
          { required: true, message: '用户账号是必须的' },
          { minLength: 5, message: '用户账号长度必须大于4' },
        ]"
      >
        <a-input v-if="isAdd" v-model="form.userAccount" allow-clear />
        <a-input v-else v-model="form.userAccount" allow-clear disabled />
      </a-form-item>
      <a-form-item field="userName" label="用户昵称">
        <a-input v-model="form.userName" allow-clear />
      </a-form-item>
      <a-form-item
        v-if="isAdd" field="userPassword" label="用户密码" :rules="[
          { required: true, message: '用户密码是必须的' },
          { minLength: 6, message: '用户密码长度必须大于8' },
        ]"
      >
        <a-input-password v-model="form.userPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        v-if="isAdd" field="checkPassword" label="确认密码" :rules="[
          { required: true, message: '确认密码是必须的' },
          { validator: validatePasswordSame },
        ]" validate-trigger="input"
      >
        <a-input-password v-model="form.checkPassword" allow-clear>
          <template #prefix>
            <IconLock />
          </template>
        </a-input-password>
      </a-form-item>
      <a-form-item
        field="userRole" label="用户角色" :rules="[
          { required: true, message: '用户角色是必须的' },
        ]"
      >
        <a-select v-model="form.userRole" :options="userRoleOptions" allow-clear />
      </a-form-item>
      <a-form-item
        field="userProfile" label="用户简介" :rules="[
          { maxLength: 20, message: '用户简介超出最大长度限制' },
        ]"
      >
        <a-textarea v-model="form.userProfile" :max-length="{ length: 20, errorOnly: true }" allow-clear show-word-limit />
      </a-form-item>
    </a-form>
  </CommonFormDrawer>
</template>
