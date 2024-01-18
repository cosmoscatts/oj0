<script setup lang="ts">
const router = useRouter()
function goHome() {
  router.push('/')
}
</script>

<template>
  <div flex-center cursor-pointer @click="goHome">
    <LogoDarkSvg v-if="isDark" hidden h-full wa md:block />
    <LogoSvg v-else hidden h-full wa md:block />
    <LogoShortSvg h-full wa md:hidden />
  </div>
</template>
