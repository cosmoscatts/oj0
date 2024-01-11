---
outline: deep
---

# 在线文档编辑器 `ByteMD`

<br>

[ByteMD](https://github.com/bytedance/bytemd) 是由字节跳动开源的一款在线文档编辑器，它使用 `Svelte` 构建而成，可以在其他库/框架（如 `React`、`Vue` 和 `Angular`）中使用。

## 特性

- 轻量且与框架无关: `ByteMD` 使用 `Svelte` 构建。它使用纯粹的 `JS DOM` 操作元素，而不导入任何 `UI` 框架，这使它轻量且易于适应其他库/框架。
- 易于扩展: `ByteMD` 采用插件系统来扩展基本的 `Markdown` 语法，这使得可以轻松添加其他功能，如代码语法高亮、数学方程和 `Mermaid` 流程图。如果这些插件不符合需求，还可以编写自己的插件。
- 默认安全: `ByteMD` 对跨站脚本 `XSS` 攻击（`<script>`和`<img onerror>`）进行了正确处理，无需引入额外的 `DOM` 清理步骤。
- `SSR` 兼容: `ByteMD` 可以在服务器端渲染 `SSR` 环境中使用，无需额外配置。由于其在缓慢的网络连接中具有更好的 `SEO` 和快速内容加载时间，`SSR` 在某些情况下被广泛使用。

## 使用

### 安装

::: code-group
```sh [npm]
npm install @bytemd/vue-next
```

```sh [yarn]
yarn add @bytemd/vue-next
```

```sh [pnpm]
pnpm install @bytemd/vue-next
```
:::

**插件**

`ByteMD` 拥有丰富的插件系统，按需使用即可。

| Package | Status | Description |
| --- | --- | --- |
| [@bytemd/plugin-breaks](https://github.com/bytedance/bytemd/tree/main/packages/plugin-breaks) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-breaks.svg?label=)](https://npm.im/@bytemd/plugin-breaks) | Support breaks |
| [@bytemd/plugin-frontmatter](https://github.com/bytedance/bytemd/tree/main/packages/plugin-frontmatter) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-frontmatter.svg?label=)](https://npm.im/@bytemd/plugin-frontmatter) | Parse frontmatter |
| [@bytemd/plugin-gemoji](https://github.com/bytedance/bytemd/tree/main/packages/plugin-gemoji) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-gemoji.svg?label=)](https://npm.im/@bytemd/plugin-gemoji) | Support Gemoji shortcodes |
| [@bytemd/plugin-gfm](https://github.com/bytedance/bytemd/tree/main/packages/plugin-gfm) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-gfm.svg?label=)](https://npm.im/@bytemd/plugin-gfm) | Support GFM (autolink literals, strikethrough, tables, tasklists) |
| [@bytemd/plugin-highlight](https://github.com/bytedance/bytemd/tree/main/packages/plugin-highlight) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-highlight.svg?label=)](https://npm.im/@bytemd/plugin-highlight) | Highlight code blocks |
| [@bytemd/plugin-highlight-ssr](https://github.com/bytedance/bytemd/tree/main/packages/plugin-highlight-ssr) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-highlight-ssr.svg?label=)](https://npm.im/@bytemd/plugin-highlight-ssr) | Highlight code blocks (SSR compatible) |
| [@bytemd/plugin-math](https://github.com/bytedance/bytemd/tree/main/packages/plugin-math) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-math.svg?label=)](https://npm.im/@bytemd/plugin-math) | Support math formula |
| [@bytemd/plugin-math-ssr](https://github.com/bytedance/bytemd/tree/main/packages/plugin-math-ssr) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-math-ssr.svg?label=)](https://npm.im/@bytemd/plugin-math-ssr) | Support math formula (SSR compatible) |
| [@bytemd/plugin-medium-zoom](https://github.com/bytedance/bytemd/tree/main/packages/plugin-medium-zoom) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-medium-zoom.svg?label=)](https://npm.im/@bytemd/plugin-medium-zoom) | Zoom images like Medium |
| [@bytemd/plugin-mermaid](https://github.com/bytedance/bytemd/tree/main/packages/plugin-mermaid) | [![npm](https://img.shields.io/npm/v/@bytemd/plugin-mermaid.svg?label=)](https://npm.im/@bytemd/plugin-mermaid) | Support Mermaid diagram |

::: code-group
```sh [npm]
npm install @bytemd/@bytemd/plugin-gfm
```

```sh [yarn]
yarn add @bytemd/@bytemd/plugin-gfm
```

```sh [pnpm]
pnpm install @bytemd/@bytemd/plugin-gfm
```
:::

**代码高亮**

高亮的第三方插件是 [highlighgt.js](https://github.com/highlightjs/highlight.js)

::: code-group
```sh [npm]
npm install highlight.js
```

```sh [yarn]
yarn add highlight.js
```

```sh [pnpm]
pnpm install highlight.js
```
:::

### 如何高亮代码

首先需要引入样式文件，这里为了支持明暗模式切换，单独将 `highlight.js` 的样式特殊处理下。

::: code-group
```ts [main.ts]
import '~/styles/hljs-atom-one-light.css'
import '~/styles/hljs-atom-one-dark.css'
```

```css [hljs-atom-one-light.css]
.hljs-atom-one-light {
  pre code.hljs {
    display: block;
    overflow-x: auto;
    padding: 1em;
  }
  code.hljs {
    padding: 3px 5px;
  }
  /*
  
  Atom One Light by Daniel Gamage
  Original One Light Syntax theme from https://github.com/atom/one-light-syntax
  
  base:    #fafafa
  mono-1:  #383a42
  mono-2:  #686b77
  mono-3:  #a0a1a7
  hue-1:   #0184bb
  hue-2:   #4078f2
  hue-3:   #a626a4
  hue-4:   #50a14f
  hue-5:   #e45649
  hue-5-2: #c91243
  hue-6:   #986801
  hue-6-2: #c18401
  
  */
  .hljs-comment,
  .hljs-quote {
    color: #a0a1a7;
    font-style: italic;
  }
  .hljs-doctag,
  .hljs-keyword,
  .hljs-formula {
    color: #a626a4;
  }
  .hljs-section,
  .hljs-name,
  .hljs-selector-tag,
  .hljs-deletion,
  .hljs-subst {
    color: #e45649;
  }
  .hljs-literal {
    color: #0184bb
  }
  .hljs-string,
  .hljs-regexp,
  .hljs-addition,
  .hljs-attribute,
  .hljs-meta .hljs-string {
    color: #50a14f
  }
  .hljs-attr,
  .hljs-variable,
  .hljs-template-variable,
  .hljs-type,
  .hljs-selector-class,
  .hljs-selector-attr,
  .hljs-selector-pseudo,
  .hljs-number {
    color: #986801
  }
  .hljs-symbol,
  .hljs-bullet,
  .hljs-link,
  .hljs-meta,
  .hljs-selector-id,
  .hljs-title {
    color: #4078f2;
  }
  .hljs-built_in,
  .hljs-title.class_,
  .hljs-class .hljs-title {
    color: #c18401;
  }
  .hljs-emphasis {
    font-style: italic;
  }
  .hljs-strong {
    font-weight: bold;
  }
  .hljs-link {
    text-decoration: underline;
  }
}
```

```css [hljs-atom-one-dark.css]
.hljs-atom-one-dark {
  pre code.hljs {
    display: block;
    overflow-x: auto;
    padding: 1em;
  }
  code.hljs {
    padding: 3px 5px;
  }
  /*
  
  Atom One Dark by Daniel Gamage
  Original One Dark Syntax theme from https://github.com/atom/one-dark-syntax
  
  base:    #282c34
  mono-1:  #abb2bf
  mono-2:  #818896
  mono-3:  #5c6370
  hue-1:   #56b6c2
  hue-2:   #61aeee
  hue-3:   #c678dd
  hue-4:   #98c379
  hue-5:   #e06c75
  hue-5-2: #be5046
  hue-6:   #d19a66
  hue-6-2: #e6c07b
  
  */
  .hljs-comment,
  .hljs-quote {
    color: #5c6370;
    font-style: italic;
  }
  .hljs-doctag,
  .hljs-keyword,
  .hljs-formula {
    color: #c678dd;
  }
  .hljs-section,
  .hljs-name,
  .hljs-selector-tag,
  .hljs-deletion,
  .hljs-subst {
    color: #e06c75;
  }
  .hljs-literal {
    color: #56b6c2;
  }
  .hljs-string,
  .hljs-regexp,
  .hljs-addition,
  .hljs-attribute,
  .hljs-meta .hljs-string {
    color: #98c379;
  }
  .hljs-attr,
  .hljs-variable,
  .hljs-template-variable,
  .hljs-type,
  .hljs-selector-class,
  .hljs-selector-attr,
  .hljs-selector-pseudo,
  .hljs-number {
    color: #d19a66;
  }
  .hljs-symbol,
  .hljs-bullet,
  .hljs-link,
  .hljs-meta,
  .hljs-selector-id,
  .hljs-title {
    color: #61aeee;
  }
  .hljs-built_in,
  .hljs-title.class_,
  .hljs-class .hljs-title {
    color: #e6c07b;
  }
  .hljs-emphasis {
    font-style: italic;
  }
  .hljs-strong {
    font-weight: bold;
  }
  .hljs-link {
    text-decoration: underline;
  }
}
```
:::

**简单使用**

```ts
import highlightjs from 'highlight.js'

highlightjs.highlightAll()
```

### 实现汉化

```vue
<script setup lang="ts">
import zhHans from 'bytemd/locales/zh_Hans.json'
import { Editor } from '@bytemd/vue-next'
</script>

<template>
<Editor :locale="zhHans" />
</template>
```

### 完整应用

```vue [EditorMarkdown.vue]
<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import gemoji from '@bytemd/plugin-gemoji'
import math from '@bytemd/plugin-math'
import mermaid from '@bytemd/plugin-mermaid'
import { Editor } from '@bytemd/vue-next'
import zhHans from 'bytemd/locales/zh_Hans.json'
import highlightjs from 'highlight.js'

const { mode = 'split' } = defineProps<{
  mode?: string
}>()

const value = defineModel<string>()

onMounted(() => {
  useTimeoutFn(highlightjs.highlightAll, 200)
})
/**
 * 定义编辑器需要用到的插件
 */
const plugins = [
  gfm(),
  gemoji(),
  math(),
  mermaid(),
]

function handleChange(newValue: string) {
  value.value = newValue
}

const themeClass = computed(() => isDark.value ? 'hljs-atom-one-dark' : 'hljs-atom-one-light')
</script>

<template>
  <ClientOnly>
    <div w-full class="markdown-body" :class="themeClass">
      <Editor
        :value="value"
        :plugins="plugins"
        :mode="mode"
        :locale="zhHans"
        @change="handleChange"
      />
    </div>
  </ClientOnly>
</template>

<style scoped>
:deep(.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child) {
  display: none;
}
</style>
```
