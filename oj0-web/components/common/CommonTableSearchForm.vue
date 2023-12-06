<script setup lang="ts">
const { form = {} } = defineProps<{
  form: any
}>()
const emits = defineEmits<{
  (event: 'search'): void
  (event: 'reset'): void
}>()

const rows = computed(() => (Math.ceil(Object.keys(form).length / 3)))
const dividerHeight = computed(() => (`${34 + (rows.value - 1) * 38}px`))
const buttonGroupFlex = computed(() => (['58px', '124px'][Number(rows.value === 1)]))
const direction = computed(() => ['vertical', 'horizontal'][Number(rows.value === 1)] as 'vertical' | 'horizontal')

function search() {
  emits('search')
}

function reset() {
  emits('reset')
}
</script>

<template>
  <a-row lt-lg:hidden>
    <a-col :flex="1">
      <a-form :model="form" :label-col-props="{ span: 6 }" :wrapper-col-props="{ span: 18 }" label-align="left">
        <a-row :gutter="16">
          <slot />
        </a-row>
      </a-form>
    </a-col>
    <a-divider :style="{ height: dividerHeight }" direction="vertical" />
    <a-col :flex="buttonGroupFlex">
      <a-space :direction="direction">
        <slot name="buttons">
          <div btn-solid @click="search">
            搜索
          </div>
          <div filter-saturate-0 btn-text @click="reset">
            重置
          </div>
        </slot>
      </a-space>
    </a-col>
  </a-row>
</template>
