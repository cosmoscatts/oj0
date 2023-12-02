<script setup lang="ts"></script>

<template>
  <a-layout h-full w-full border="1 red" of-hidden>
    <a-layout-header h-50px>
      <TheNav />
    </a-layout-header>
    <a-layout :style="{ minHeight: 'calc(100% - 50px)', overflow: 'hidden auto' }">
      <a-layout-content>
        <TheMainContent />
      </a-layout-content>
      <a-layout-footer h-50px>
        <TheFooter />
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
