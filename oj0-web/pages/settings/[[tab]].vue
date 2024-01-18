<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Settings',
  title: '设置中心 - OJ0',
  access: ACCESS_ENUM.USER,
  middleware: 'auth',
})

const route = useRoute()
const tab = computed(() => route.params?.tab as string || 'info')

const selectedTab = ref(tab.value)

const router = useRouter()
function back() {
  router.go(-1)
}
</script>

<template>
  <div>
    <div mb-2 me-8 flex-y-center gap-1 md:pl-5>
      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
    </div>

    <div grid="cols-4 gap-3" mt-4 hidden w-full md:grid>
      <div col-span-1>
        <SettingsTabs v-model="selectedTab" />
      </div>

      <div col-span-3>
        <CommonTransition>
          <SettingsUserInterface v-if="selectedTab === 'interface'" />

          <SettingsUserFavorites v-if="selectedTab === 'favorites'" />
          <SettingsUserSolutions v-if="selectedTab === 'solutions'" />
          <SettingsUserResolveAnalysis v-if="selectedTab === 'resolve-analysis'" />

          <SettingsUserInformation v-if="selectedTab === 'info'" />
          <SettingsUserAccount v-if="selectedTab === 'account'" />
        </CommonTransition>
      </div>
    </div>

    <div flex="~ col gap-4" mt-4 w-full md:hidden>
      <SettingsTabs v-model="selectedTab" />

      <CommonTransition>
        <SettingsUserInterface v-if="selectedTab === 'interface'" />

        <SettingsUserFavorites v-if="selectedTab === 'favorites'" />
        <SettingsUserSolutions v-if="selectedTab === 'solutions'" />
        <SettingsUserResolveAnalysis v-if="selectedTab === 'resolve-analysis'" />

        <SettingsUserInformation v-if="selectedTab === 'info'" />
        <SettingsUserAccount v-if="selectedTab === 'account'" />
      </CommonTransition>
    </div>
  </div>
</template>
