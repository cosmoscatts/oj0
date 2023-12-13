<script setup lang="ts">
defineProps({
  error: Object,
})

const handleError = () => clearError({ redirect: '/' })
</script>

<template>
  <div min-h-screen flex="col center" text-base>
    <div flex="~ col gap-2" items-center text-center>
      <div i-ri-error-warning-line text-5xl /><span text-xl>发生了一些错误···</span>
      <button mt-5 btn-solid @click="handleError">
        清除错误
      </button>
    </div>
  </div>
</template>
