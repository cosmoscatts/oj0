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
