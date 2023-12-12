<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'UserProfile',
  title: '个人中心 - OJ0',
  access: ACCESS_ENUM.USER,
  // middleware: 'auth',
})

const selectedTab = ref('info')

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
        <UserProfileTabs v-model="selectedTab" />
      </div>

      <div col-span-3>
        <CommonTransition>
          <UserProfileInformation v-if="selectedTab === 'info'" />
          <UserProfileAccountSettings v-if="selectedTab === 'account'" />
        </CommonTransition>
      </div>
    </div>

    <div flex="~ col gap-4" mt-4 w-full md:hidden>
      <UserProfileTabs v-model="selectedTab" />

      <CommonTransition>
        <UserProfileInformation v-if="selectedTab === 'info'" />
        <UserProfileAccountSettings v-if="selectedTab === 'account'" />
      </CommonTransition>
    </div>
  </div>
</template>
