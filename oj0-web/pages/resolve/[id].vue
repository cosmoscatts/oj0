<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'ResolveQuestion',
  access: ACCESS_ENUM.USER, // 需要用户权限
  layout: 'none',
  // middleware: 'auth',
})

const route = useRoute()

const id = computed(() => route.params.id) // 题目 id
</script>

<template>
  <div h-screen w-screen>
    <ResolveActionBar />

    <div px-30px>
      做题 {{ id }}
    </div>
  </div>
</template>
