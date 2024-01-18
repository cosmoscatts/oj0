<script setup lang="ts">
import type { ColorMode, ThemeColors } from '~/composables/settings/definition'

const themes = await import('~/constants/themes.json').then(r => r.default) as [string, ThemeColors][]
const settings = useUserSettings()

const currentTheme = computed(() => settings.value.themeColors?.['--theme-color-name'] || themes[0][1]['--theme-color-name'])

function updateTheme(theme: ThemeColors) {
  settings.value.themeColors = theme
  setThemeColor()
}

const colorMode = useColorMode()

function setColorMode(mode: ColorMode) {
  colorMode.preference = mode
}

const modes = [
  {
    icon: 'i-ri-moon-line',
    label: '深色',
    mode: 'dark',
  },
  {
    icon: 'i-ri-sun-line',
    label: '浅色',
    mode: 'light',
  },
  {
    icon: 'i-ri-computer-line',
    label: '跟随系统',
    mode: 'system',
  },
] as const
</script>

<template>
  <div flex="~ col" max-w-650px gap-6 p5 lt-md:w-full>
    <div space-y-2>
      <p font-medium>
        颜色
      </p>
      <div flex="~ gap4 wrap" w-full>
        <button
          v-for="{ icon, label, mode } in modes"
          :key="mode"
          flex="~ gap-1 center" border="~ base rounded" flex-1 ws-nowrap bg-base p4 btn-text
          :tabindex="colorMode.preference === mode ? 0 : -1"
          :class="colorMode.preference === mode ? 'pointer-events-none' : 'filter-saturate-0'"
          @click="setColorMode(mode)"
        >
          <span :class="`${icon}`" />
          {{ label }}
        </button>
      </div>
    </div>
    <div space-y-2>
      <p font-medium>
        主题颜色
      </p>
      <div flex="~ gap4 wrap" p2>
        <button
          v-for="[key, theme] in themes" :key="key"
          :style="{
            'background': key,
            '--local-ring-color': key,
          }"
          :class="currentTheme === theme['--theme-color-name'] ? 'ring-2' : 'scale-90'"
          :title="theme['--theme-color-name']"
          h-8 w-8 rounded-full transition-all
          ring="$local-ring-color offset-3 offset-$c-bg-base"
          @click="updateTheme(theme)"
        />
      </div>
    </div>
  </div>
</template>
