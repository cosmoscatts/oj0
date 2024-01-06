<script setup lang="ts">
import MarkdownIt from 'markdown-it'
import Shikiji from 'markdown-it-shikiji'

const { value = '' } = defineProps<{
  value?: string
}>()

const html = ref('')

let mdLight: MarkdownIt
let mdDark: MarkdownIt

async function init() {
  const _md = MarkdownIt()
  mdLight = _md.use(await Shikiji({
    theme: 'vitesse-light',
  }))
  const _md2 = MarkdownIt()
  mdDark = _md2.use(await Shikiji({
    theme: 'vitesse-dark',
  }))
}

async function tranfrom(val: string) {
  if (!mdLight || !mdDark)
    await init()

  const md = isDark.value ? mdDark : mdLight
  let renderStr = md.render(val)
  if (isDark.value)
    renderStr = renderStr.replaceAll('background-color:#121212;', 'background-color:#ffffff06;')

  else
    renderStr = renderStr.replaceAll('background-color:#ffffff;', 'background-color:#00000006;')

  html.value = renderStr
}
tranfrom(value)

watch(() => value, () => tranfrom(value))
watch(isDark, () => tranfrom(value))
</script>

<template>
  <ClientOnly>
    <div w-full class="markdown-body">
      <div v-html="html" />
    </div>
  </ClientOnly>
</template>
