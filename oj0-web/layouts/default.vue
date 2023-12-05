<script setup lang="ts"></script>

<template>
  <div h-full w-full>
    <a-layout v-if="isHydrated" h-full w-full of-hidden>
      <a-layout-header h-60px>
        <TheNav />
      </a-layout-header>
      <a-layout :style="{ minHeight: 'calc(100% - 60px)', overflow: 'hidden auto' }">
        <a-layout-content p-30px>
          <TheMainContent />
        </a-layout-content>
        <a-layout-footer h-30px>
          <TheFooter />
        </a-layout-footer>
      </a-layout>
    </a-layout>
  </div>
</template>
