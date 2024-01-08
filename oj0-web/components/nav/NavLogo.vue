<script setup lang="ts">
import logo from '~/public/logo.svg'
import logoDark from '~/public/logo-dark.svg'
import logoShort from '~/public/logo-short.svg'

const currentLogo = computed(() => {
  return isDark.value ? logoDark : logo
})

const router = useRouter()
function goHome() {
  router.push('/')
}
</script>

<template>
  <div flex-center cursor-pointer @click="goHome">
    <img :src="currentLogo" hidden h-full md:block>
    <img h-full :src="logoShort" md:hidden>
  </div>
</template>
