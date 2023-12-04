<template>
  <div>
    <RouterView v-slot="{ Component, route }">
      <CommonTransition>
        <component :is="Component" :key="route.fullPath" />
      </CommonTransition>
    </RouterView>
  </div>
</template>
