<template>
  <div h-200px border border-base rounded flex="~ col" p-4>
    <div text-sm font-bold>
      解题数量
    </div>
    <div grid="~ cols-3" mt-3 flex-1 gap-2>
      <div col-span-1 flex-center>
        <a-progress
          :percent="0.4"
          type="circle"
          size="large"
          track-color="var(--color-primary-light-1)"
        />
      </div>
      <div col-span-2 grid="~ rows-3 gap-1">
        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm>
            <div>简单</div>
            <div>141/981</div>
          </div>
          <a-progress size="large" :percent="0.2" color="green" />
        </div>

        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm>
            <div>中等</div>
            <div>141/981</div>
          </div>
          <a-progress size="large" :percent="0.2" color="orange" />
        </div>

        <div row-span-1 flex="~ col" gap-1>
          <div flex-y-center justify-between text-sm>
            <div>困难</div>
            <div>141/981</div>
          </div>
          <a-progress size="large" :percent="0.2" color="red" />
        </div>
      </div>
    </div>
  </div>
</template>
