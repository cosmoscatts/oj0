<script setup lang="ts">
const visible = ref(false)

/**
 * 当屏幕宽度发生变化时，应关闭 dropdown
 */
const { width } = useWindowSize()
watch(width, () => {
  if (visible.value)
    visible.value = false
})
</script>

<template>
  <a-dropdown v-model:popup-visible="visible" trigger="click">
    <NavAvatar cursor-pointer :size="28" />
    <template #content>
      <div w-250px p-4>
        <div h-50px flex-y-center shrink-0 gap-3>
          <NavAvatar />

          <div pl-3>
            <div text-xl font-bold>
              赤徑
            </div>
            <div mt-1 text-primary>
              I have no bio.
            </div>
          </div>
        </div>

        <a-divider />

        <div>
          <a-doption>
            <div flex-y-center gap-4>
              <div i-ri-profile-line />
              个人中心
            </div>
          </a-doption>
          <a-doption>
            <div flex-y-center gap-4>
              <div i-ri-logout-box-r-line />
              退出登陆
            </div>
          </a-doption>
        </div>
      </div>
    </template>
  </a-dropdown>
</template>
