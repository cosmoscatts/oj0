<script setup lang="ts">
import logo from '~/public/logo.svg'

const router = useRouter()

function back() {
  router.push('/questions')
}
</script>

<template>
  <div mb-5 me-8 h-14 w-full flex-y-center justify-between border-b border-base px-30px>
    <div flex-center gap-1>
      <div mr-2 h-8 w-8 flex-center cursor-pointer select-none>
        <img :src="logo" rounded-lg>
      </div>

      <CommonTooltip content="回退">
        <div i-ri:arrow-left-line text-xl btn-text @click="back" />
      </CommonTooltip>
      <a-divider direction="vertical" />
      <CommonTooltip content="上一题">
        <div i-ri-arrow-left-s-line text-xl filter-saturate-0 btn-text @click="back" />
      </CommonTooltip>
      <CommonTooltip content="下一题">
        <div i-ri-arrow-right-s-line text-xl filter-saturate-0 btn-text @click="back" />
      </CommonTooltip>
      <CommonTooltip content="随机一题">
        <div i-ri-shuffle-line filter-saturate-0 btn-text @click="back" />
      </CommonTooltip>
    </div>

    <div flex-center gap-3>
      <button flex-center btn-outline>
        <div i-ri-play-mini-fill mr-2 />
        运行
      </button>
      <button flex-center btn-outline>
        <div i-ri-upload-cloud-fill mr-2 />
        提交
      </button>
    </div>

    <NavAvatar :size="36" />
  </div>
</template>
