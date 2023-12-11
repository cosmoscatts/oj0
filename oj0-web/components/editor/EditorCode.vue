<script setup lang="ts">
import * as monaco from 'monaco-editor'

const { language = 'java' } = defineProps<{
  language?: string
}>()

const modelValue = defineModel<string>()

const refEl = ref()
const codeEditor = ref()

watch(() => language, () => {
  if (codeEditor.value) {
    monaco.editor.setModelLanguage(
      toRaw(codeEditor.value).getModel(),
      language,
    )
  }
})

onMounted(() => {
  if (!refEl.value)
    return

  codeEditor.value = monaco.editor.create(refEl.value, {
    value: modelValue.value,
    language,
    automaticLayout: true,
    colorDecorators: true,
    minimap: {
      enabled: true,
    },
    readOnly: false,
    theme: 'vs-dark',
    // lineNumbers: "off",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
  })

  // 编辑 监听内容变化
  codeEditor.value.onDidChangeModelContent(() => {
    modelValue.value = toRaw(codeEditor.value).getValue()
  })
})
</script>

<template>
  <div id="code-editor" ref="refEl" h-full w-full />
</template>
