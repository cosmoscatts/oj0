<template>
  <div>
    <RouterView v-slot="{ Component, route }">
      <CommonTransition>
        <component :is="Component" :key="route.path" />
      </CommonTransition>
    </RouterView>
  </div>
</template>
