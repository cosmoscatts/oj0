<script setup lang="ts">
import notFoundSvg from '~/public/404.svg'
import errorSvg from '~/public/500.svg'

defineProps({
  error: Object,
})

const handleError = () => clearError({ redirect: '/' })
</script>

<template>
  <div relative h-screen flex justify-center of-auto text-base>
    <NavLogo absolute left-4rem top-3rem h-30px />

    <div flex="~ col gap-2" min-h-400px items-center text-center mt="[calc(3rem+80px)]">
      <template v-if="error?.statusCode === 404">
        <div h-300px flex-center>
          <img :src="notFoundSvg" h-140px>
        </div>
      </template>
      <template v-else>
        <div h-300px flex-center>
          <img :src="errorSvg" h-250px>
        </div>
      </template>
      <button mt-5 btn-solid @click="handleError">
        {{ error?.statusCode === 404 ? '去首页看看吧~' : '清除错误' }}
      </button>
    </div>
  </div>
</template>
