<script setup lang="ts">
const { width = 900 } = defineProps<{
  width?: number
  title?: string
}>()
const emit = defineEmits<{
  (event: 'ok'): void
  (event: 'reset'): void
}>()
const visible = defineModel<boolean>()

const { width: windowWidth } = useWindowSize()
const responsiveWidth = computed(() => {
  if (windowWidth.value < width)
    return windowWidth.value
  return width
})

function handleOk() {
  emit('ok')
}

function reset() {
  emit('reset')
}
</script>

<template>
  <a-drawer
    :width="responsiveWidth"
    :visible="visible"
    :mask-closable="false"
    unmount-on-close
    @cancel="visible = false"
    @ok="handleOk"
  >
    <template #header>
      <slot name="header">
        <div w-full flex-y-center justify-between>
          <div>{{ title }}</div>
          <div btn-action-icon @click="visible = false">
            <div i-ri-close-line />
          </div>
        </div>
      </slot>
    </template>

    <slot />

    <template #footer>
      <slot name="footer">
        <div flex-y-center justify-end gap-2>
          <div btn-solid @click="handleOk">
            保存
          </div>
          <div filter-saturate-0 btn-text @click="reset">
            重置
          </div>
        </div>
      </slot>
    </template>
  </a-drawer>
</template>
