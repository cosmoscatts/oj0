<script setup lang="ts">
const authStore = useAuthStore()
const hasLogin = authStore.getHasLogin()
</script>

<template>
  <nav h-full w-full border="b base" flex-y-center>
    <div mx-a h-full max-w-1200px w-full flex-y-center justify-between px-6 lt-md:hidden>
      <NavLogo h-24px />

      <NavListMenu h-full flex-1 />

      <div flex-y-center gap-3>
        <DarkToggle />
        <NavLoginBtns v-if="!hasLogin" />
        <NavAvatarDropdown v-else />
      </div>
    </div>

    <!-- 小屏幕 -->
    <div h-full w-full flex-y-center justify-between px-3 md:hidden>
      <NavLogo h-24px />

      <div flex-y-center gap-3>
        <DarkToggle />
        <NavDropdownPanel />
        <NavLoginBtns v-if="!hasLogin" />
      </div>
    </div>
  </nav>
</template>
