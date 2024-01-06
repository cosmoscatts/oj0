<script setup lang="ts">
const { icon = 'i-ph-signpost-duotone' } = defineProps<{
  icon?: string
  title: string
  content: string
}>()
</script>

<template>
  <div
    relative isolate flex flex-1 flex-col rounded-xl shadow ring-1 ring-gray-200 transition-shadow duration-200 before:absolute before:inset--2px before:z--1 before:hidden dark:ring-dark-800 before:lg:block
    class="group before:h-[calc(100%+4px)] before:w-[calc(100%+4px)] before:rounded-[13px]"
  >
    <div
      flex flex-1 flex-col overflow-hidden rounded-xl bg-white
      divide-y divide-gray-200 dark:bg-dark-900 hover:bg-opacity-90
      dark:bg-gradient-to-b dark:divide-dark-800 dark:hover:bg-opacity-90
      class="from-dark-700/50 to-dark-900/50"
    >
      <div
        flex flex-1 flex-col gap-x-8 gap-y-4 rounded-xl px-4 py-5
        class="dark:bg-dark-900/50 sm:p-6"
      >
        <div>
          <a focus:outline-none><span absolute inset-0 /></a>
          <div pointer-events-none mb-2>
            <div :class="icon" h-8 w-8 flex-shrink-0 text-gray-900 dark:text-white />
          </div>
          <p truncate font-bold text-base text-gray-900 dark:text-white>
            {{ title }}
          </p>
          <p mt-1 text-15px text-gray-500 dark:text-gray-400>
            {{ content }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>
