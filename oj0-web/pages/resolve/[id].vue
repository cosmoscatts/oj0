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

    <div :style="{ height: `calc(100vh - 6rem)` }" mb-2 of-hidden px-30px>
      <a-split
        :style="{
          height: '100%',
          width: '100%',
          minWidth: '500px',
        }" flex
      >
        <template #first>
          <div h-full w-full of-x-hidden of-y-auto border="1 base">
            1
          </div>
        </template>
        <template #second>
          <div>
            <a-split direction="vertical" :style="{ height: 'calc(100vh - 6rem)', overflow: 'hidden' }">
              <template #first>
                <div h-full w-full of-x-hidden of-y-auto border="1 base">
                  2
                </div>
              </template>
              <template #second>
                <div h-full w-full of-x-hidden of-y-auto border="1 base">
                  3
                </div>
              </template>
            </a-split>
          </div>
        </template>
      </a-split>
    </div>
  </div>
</template>
