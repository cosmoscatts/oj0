<script setup lang="ts">
import { useToast } from 'vue-toastification'
import { GITHUB_CLIENT_ID } from '~/config/github'

const toast = useToast()

function getDefaultBoundInfo() {
  return {
    canUnbound: false,
    github: false,
    qq: false,
    wechat: false,
  }
}
const boundInfo = ref<ExtraAuthBound>(getDefaultBoundInfo())
async function fetchExtraAuth() {
  const { data } = await AuthApi.getMyExtraAuthBound()
  boundInfo.value = data || getDefaultBoundInfo()
}
fetchExtraAuth()

async function unbind(type: string) {
  if (!boundInfo.value.canUnbound) {
    Message.error('请先设置初始账号')
    return
  }
  const { code, message } = await AuthApi.unbindExtraAuth({ type })
  if (code !== 0) {
    toast.error(message || '提交失败')
    return
  }
  toast.success('解绑成功')
  fetchExtraAuth()
}

function onExtraBtnClick(e: MouseEvent) {
  e.preventDefault()
  Message.warning('功能开发中，暂不支持此操作！')
}

function bind(type: string) {
  const urlMap = {
    github: `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}`,
    qq: '',
    wechart: '',
  } as Record<string, string>

  const url = urlMap[type]
  if (!url)
    return
  useOpenWindow(url, { target: '_self' })
}
</script>

<template>
  <a-form-item>
    <template #label>
      <div flex-center gap-2>
        <div i-ri-github-line />
        GitHub
      </div>
    </template>
    <div w-full flex-y-center justify-between>
      <div flex="1 center">
        {{ boundInfo.github ? '已绑定' : '未绑定' }}
      </div>
      <button v-if="boundInfo.github" btn-text @click="unbind('github')">
        解绑
      </button>
      <button v-else btn-text @click="bind('github')">
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
        {{ boundInfo.qq ? '已绑定' : '未绑定' }}
      </div>
      <button v-if="boundInfo.qq" btn-text @click="unbind('qq')">
        解绑
      </button>
      <button v-else btn-text @click="onExtraBtnClick">
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
        {{ boundInfo.wechat ? '已绑定' : '未绑定' }}
      </div>
      <button v-if="boundInfo.wechat" btn-text @click="unbind('wechat')">
        解绑
      </button>
      <button v-else btn-text @click="onExtraBtnClick">
        绑定
      </button>
    </div>
  </a-form-item>

  <!-- <a-form-item mt-4>
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
          <button btn-text @click="onExtraBtnClick">
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
          <button btn-text @click="onExtraBtnClick">
            绑定
          </button>
        </div>
      </a-form-item> -->
</template>
