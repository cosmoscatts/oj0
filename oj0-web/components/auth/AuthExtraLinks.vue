<script setup lang="ts">
import { GITHUB_CLIENT_ID } from '~/config/github'

function onClick() {
  Message.warning('功能开发中，暂不支持此操作！')
}

function openGitHubAuth() {
  const url = `https://github.com/login/oauth/authorize?client_id=${GITHUB_CLIENT_ID}`
  useOpenWindow(url, { target: '_self' })
}
</script>

<template>
  <div w-full flex-y-center gap-2>
    <CommonTooltip content="使用 QQ 登录">
      <div group bg-active btn-action-icon hover:bg-blue @click="onClick">
        <div i-ri-qq-fill text-blue group-hover:text-white />
      </div>
    </CommonTooltip>

    <CommonTooltip content="使用微信登录">
      <div group bg-active btn-action-icon hover:bg-green @click="onClick">
        <div i-ri-wechat-fill text-green group-hover:text-white />
      </div>
    </CommonTooltip>

    <CommonTooltip content="使用 GitHub 登录">
      <div group bg-active btn-action-icon hover:bg-purple @click="openGitHubAuth">
        <div i-ri-github-fill text-purple group-hover:text-white />
      </div>
    </CommonTooltip>
  </div>
</template>
