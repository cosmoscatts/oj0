<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  access: ACCESS_ENUM.USER, // 需要用户权限
  // middleware: 'auth',
})

const selectedBtn = ref()
</script>

<template>
  <div min-h-500px w-full>
    <div flex="~ gap4 wrap" gap-4>
      <button flex="~ gap-1 center" border="~ base rounded" flex-1 ws-nowrap bg-base p4 filter-saturate-0 btn-text>
        <span class="i-ri-moon-line" /> 数据库
      </button>

      <button flex="~ gap-1 center" border="~ base rounded" flex-1 ws-nowrap bg-base p4 filter-saturate-0 btn-text>
        <span class="i-ri-moon-line" /> 数据库
      </button>

      <button flex="~ gap-1 center" border="~ base rounded" flex-1 ws-nowrap bg-base p4 filter-saturate-0 btn-text>
        <span class="i-ri-moon-line" /> 数据库
      </button>

      <button flex="~ gap-1 center" border="~ base rounded" flex-1 ws-nowrap bg-base p4 filter-saturate-0 btn-text>
        <span class="i-ri-moon-line" /> 数据库
      </button>
    </div>
  </div>
</template>
