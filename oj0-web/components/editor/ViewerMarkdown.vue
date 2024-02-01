<script setup lang="ts">
import MarkdownIt from 'markdown-it'
import Shiki from '@shikijs/markdown-it'

const { value = '' } = defineProps<{
  value?: string
}>()

const html = ref('')

let mdLight: MarkdownIt
let mdDark: MarkdownIt
const hasRender = ref(false)

async function init() {
  const _md = MarkdownIt()
  mdLight = _md.use(await Shiki({
    theme: 'vitesse-light',
  }))
  const _md2 = MarkdownIt()
  mdDark = _md2.use(await Shiki({
    theme: 'vitesse-dark',
  }))
}

async function tranfrom(val: string) {
  if (!val)
    return

  if (!mdLight || !mdDark)
    await init()

  const md = isDark.value ? mdDark : mdLight
  let renderStr = md.render(val)
  if (renderStr.includes('<pre') && renderStr.includes('<code')) {
    if (isDark.value)
      renderStr = renderStr.replaceAll('background-color:#121212;', 'background-color:#ffffff06;')

    else
      renderStr = renderStr.replaceAll('background-color:#ffffff;', 'background-color:#00000006;')
  }
  html.value = renderStr
  if (!hasRender.value)
    hasRender.value = true
}
onMounted(() => tranfrom(value))
watch(() => value, () => tranfrom(value))
watch(isDark, () => tranfrom(value))
</script>

<template>
  <ClientOnly>
    <div v-show="!hasRender" h-full w-full flex-center>
      <div i-svg-spinners-3-dots-fade bg="$c-text-base" text-3xl />
    </div>
    <div v-show="hasRender" class="markdown-body" relative w-full>
      <div v-html="html" />
    </div>
  </ClientOnly>
</template>
