<template>
  <CommonTooltip content="切换主题">
    <button
      text-xl filter-saturate-0 btn-text
      i="carbon-sun dark:carbon-asleep"
      @click="toggleDark"
    />
  </CommonTooltip>
</template>
