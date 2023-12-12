<script setup lang="ts">
import { Codemirror } from 'vue-codemirror'
import { java } from '@codemirror/lang-java'
import { cpp } from '@codemirror/lang-cpp'
import { StreamLanguage } from '@codemirror/language'
import { go } from '@codemirror/legacy-modes/mode/go'
import { oneDark } from '@codemirror/theme-one-dark'
import { tomorrow } from 'thememirror'
import { RESOLVE_LANGUAGE_ENUM } from '~/constants'

const { language = RESOLVE_LANGUAGE_ENUM.JAVA } = defineProps<{
  language?: string
}>()

const modelValue = defineModel<string>()

const extensions = computed(() => {
  const result = []
  result.push(
    isDark.value
      ? oneDark
      : tomorrow,
  )
  if (language === RESOLVE_LANGUAGE_ENUM.JAVA)
    result.push(java())

  if (language === RESOLVE_LANGUAGE_ENUM.CPP)
    result.push(cpp())

  if (language === RESOLVE_LANGUAGE_ENUM.GO)
    result.push(StreamLanguage.define(go))

  return result
})
</script>

<template>
  <ClientOnly>
    <Codemirror
      v-model="modelValue"
      :style="{ height: '100%', width: '100%' }"
      :autofocus="false"
      :indent-with-tab="true"
      :tab-size="2"
      :extensions="extensions"
    />
  </ClientOnly>
</template>
