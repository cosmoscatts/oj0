<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Home',
  title: '首页 - OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const emojiArray = [
  '\\(o_o)/',
  '(o^^)o',
  '(˚Δ˚)b',
  '(^-^*)',
  '(^_^)b',
  '(╯‵□′)╯',
  '(=\'X\'=)',
  '(>_<)',
  '\\(°ˊДˋ°)/',
  'ㄟ(▔▽▔)ㄏ',
]
function getEmoji(): string {
  return emojiArray[Math.floor(Math.random() * emojiArray.length)]
}

onMounted(() => useLottie({
  containerId: '#lottie',
  path: 'https://lottie.host/87e660f6-9b48-43fe-878e-daa18eab5ebb/7uuyUSkDbI.json',
}))
</script>

<template>
  <div flex="col center" h-full min-h-600px w-full>
    <div id="lottie" h-300px w-400px />
    <div mt-30px text-center font-bold>
      <h1 text="3xl" m="t-2 b-2">
        Hello, {{ getEmoji() }}.
      </h1>
      <p m="t-3 b-2" mx-a w-40vw text-2xl>
        纵有疾风起，人生不言弃！
      </p>
    </div>
  </div>
</template>
