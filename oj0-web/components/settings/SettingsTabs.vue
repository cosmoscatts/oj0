<script setup lang="ts">
const modelValue = defineModel<string>()

function handleClick(value: string) {
  modelValue.value = value
}
</script>

<template>
  <div w-full h-full>
    <div w-full hidden md:block border="e base" h-full>
      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'interface' ? 'text-primary' : ''" @click="handleClick('interface')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-compasses-2-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>外观</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>

      <div h-1px bg-border my-2 />

      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'favorites' ? 'text-primary' : ''" @click="handleClick('favorites')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-star-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>收藏夹</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>
      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'solutions' ? 'text-primary' : ''" @click="handleClick('solutions')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-file-copy-2-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>我的题解</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>
      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'resolve-analysis' ? 'text-primary' : ''" @click="handleClick('resolve-analysis')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-pie-chart-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>做题分析</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>

      <div h-1px bg-border my-2 />

      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'info' ? 'text-primary' : ''" @click="handleClick('info')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-account-circle-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>个人资料</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>
      <a group block w-full focus:outline-none cursor-pointer :class="modelValue === 'account' ? 'text-primary' : ''" @click="handleClick('account')">
        <div
          w-full flex w-fit px5 py3 md:gap2 gap4 items-center transition-250
          group-hover:bg-active group-focus-visible:ring="2 current"
        >
          <div flex-1 flex items-center md:gap2 gap4>
            <div flex items-center justify-center flex-shrink-0 class>
              <div i-ri-user-settings-line text-xl md:text-size-inherit />
            </div>
            <div flex="~ col gap-0.5">
              <p><span>账号安全</span></p>
            </div>
          </div>
          <div i-ri:arrow-right-s-line text-xl text-secondary-light />
        </div>
      </a>
    </div>

    <div w-full flex="~ gap4 wrap" gap-4 md:hidden>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'interface' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('interface')">
        <span i-ri-compasses-2-line /> 外观
      </button>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'favorites' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('favorites')">
        <span i-ri-star-line /> 收藏夹
      </button>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'solutions' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('solutions')">
        <span i-ri-file-copy-2-line /> 我的题解
      </button>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'resolve-analysis' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('resolve-analysis')">
        <span i-ri-pie-chart-line /> 做题分析
      </button>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'info' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('info')">
        <span i-ri-account-circle-line  /> 个人资料
      </button>
      <button  flex="~ gap-1 center" border="~ base rounded" :class="modelValue === 'account' ? '' : 'filter-saturate-0'" flex-1 ws-nowrap bg-base p4 btn-text @click="handleClick('account')">
        <span i-ri-user-settings-line /> 账号安全
      </button>
    </div>
  </div>
</template>
