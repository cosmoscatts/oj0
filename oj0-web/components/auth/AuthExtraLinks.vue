<template>
  <div w-full flex-y-center gap-2>
    <CommonTooltip content="使用 QQ 登录">
      <div group bg-active btn-action-icon hover:bg-blue>
        <div i-ri-qq-fill text-blue group-hover:text-white />
      </div>
    </CommonTooltip>

    <CommonTooltip content="使用微信登录">
      <div group bg-active btn-action-icon hover:bg-green>
        <div i-ri-wechat-fill text-green group-hover:text-white />
      </div>
    </CommonTooltip>

    <CommonTooltip content="使用 GitHub 登录">
      <div group bg-active btn-action-icon hover:bg-purple>
        <div i-ri-github-fill text-purple group-hover:text-white />
      </div>
    </CommonTooltip>
  </div>
</template>
