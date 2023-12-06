<script setup lang="ts">
const props = defineProps<{
  formModel: any
  search?: () => void
  reset?: () => void
}>()

const rows = computed(() => (Math.ceil(Object.keys(props.formModel).length / 3)))
const dividerHeight = computed(() => (`${34 + (rows.value - 1) * 50}px`))
const buttonGroupFlex = computed(() => (['86px', '172px'][Number(rows.value === 1)]))
const direction = computed(() => ['vertical', 'horizontal'][Number(rows.value === 1)] as 'vertical' | 'horizontal')
</script>

<template>
  <div>
    <a-row hidden md:block>
      <a-col :flex="1">
        <a-form
          :model="formModel"
          :label-col-props="{ span: 6 }"
          :wrapper-col-props="{ span: 18 }"
          label-align="left"
        >
          <a-row :gutter="16">
            <slot />
          </a-row>
        </a-form>
      </a-col>
      <a-divider :style="{ height: dividerHeight }" direction="vertical" />
      <a-col :flex="buttonGroupFlex">
        <a-space :direction="direction" :size="18">
          <slot name="buttons">
            <a-button type="primary" font-bold @click="search">
              <template #icon>
                <IconSearch />
              </template>
              搜索
            </a-button>
            <a-button font-bold @click="reset">
              <template #icon>
                <IconRefresh />
              </template>
              重置
            </a-button>
          </slot>
        </a-space>
      </a-col>
    </a-row>
    <div flex justify-end md:hidden>
      <a-button type="primary" font-bold @click="search">
        <template #icon>
          <IconSearch />
        </template>
        搜索
      </a-button>
    </div>
  </div>
</template>
