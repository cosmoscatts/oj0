---
outline: deep
---

# 在线代码编辑器 `Monaco Editor`

<br>

[Monaco Editor](https://github.com/microsoft/monaco-editor) 是由微软开源的一款在线代码编辑器，功能十分齐全。

![Monaco Editor](/public/refers/monaco/img1.png)

## 使用

### 安装

::: code-group
```sh [npm]
npm install monaco-editor
```

```sh [yarn]
yarn add monaco-editor
```

```sh [pnpm]
pnpm install monaco-editor
```
:::

**代码高亮**

:::info
高亮的第三方插件是 [shikiji](https://shikiji.netlify.app/packages/monaco)。
:::

::: code-group
```sh [npm]
npm install -D shikiji
npm install -D shikiji-monaco
```

```sh [yarn]
yarn add -D shikiji
yarn add -D shikiji-monaco
```

```sh [pnpm]
pnpm install -D shikiji
pnpm install -D shikiji-monaco
```
:::

### 完整应用

```vue [EditorCode.vue]
<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { getHighlighter } from 'shikiji'
import { shikijiToMonaco } from 'shikiji-monaco'
import { RESOLVE_LANGUAGE_ENUM } from '~/constants'

const { language = RESOLVE_LANGUAGE_ENUM.JAVA, submitCode } = defineProps<{
  language?: string
  submitCode?: string
}>()

const modelValue = defineModel<string>()

const hasInitShikiji = ref(false)
async function initShikiji() {
  const highlighter = await getHighlighter({
    themes: [
      'vitesse-dark',
      'vitesse-light',
    ],
    langs: [
      'java',
      'cpp',
      'go',
    ],
  })

  monaco.languages.register({ id: 'java' })
  monaco.languages.register({ id: 'cpp' })
  monaco.languages.register({ id: 'go' })

  shikijiToMonaco(highlighter, monaco)
  hasInitShikiji.value = true
}
initShikiji()

let editor: monaco.editor.IStandaloneCodeEditor
const refCodeEditor = ref()

watch(() => language, () => {
  if (editor) {
    monaco.editor.setModelLanguage(
      editor.getModel()!,
      language,
    )
  }
})

watch(() => submitCode, (n) => {
  if (editor && !!n)
    editor.setValue(modelValue.value || '')
})

watch(isDark, (n) => {
  if (editor)
    monaco.editor.setTheme(n ? 'vitesse-dark' : 'vitesse-light')
})

onMounted(async () => {
  if (!refCodeEditor.value)
    return

  await until(hasInitShikiji).toBe(true, { timeout: 5000 })
  editor = monaco.editor.create(refCodeEditor.value, {
    value: modelValue.value,
    language,
    automaticLayout: true,
    colorDecorators: true,
    scrollbar: {
      horizontalScrollbarSize: 10,
      verticalScrollbarSize: 10,
    },
    scrollBeyondLastLine: false,
    fontSize: 15,
    minimap: {
      enabled: true,
    },
    readOnly: false,
    theme: isDark.value ? 'vitesse-dark' : 'vitesse-light',
  })

  editor.onDidChangeModelContent(() => {
    modelValue.value = editor.getValue()
  })
})
</script>

<template>
  <div id="code-editor" ref="refCodeEditor" relative h-full w-full>
    <div v-if="!hasInitShikiji" absolute left-0 top-0 z-100 h-full w-full flex-center>
      <div i-svg-spinners-3-dots-fade bg="$c-text-base" text-3xl />
    </div>
  </div>
</template>
```
