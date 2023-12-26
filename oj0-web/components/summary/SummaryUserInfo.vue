<script setup lang="ts">
import * as echarts from 'echarts'

const option = computed(() => {
  return {
    title: {
      show: false,
    },
    legend: {
      show: false,
    },
    radar: {
      shape: 'circle',
      radius: '60%',
      indicator: [
        { name: '数据结构', max: 1 },
        { name: '算法', max: 1 },
        { name: '基础架构', max: 1 },
        { name: '设计', max: 1 },
      ],
      axisName: {
        color: isDark.value ? '#f3f3f3' : '#232323',
        fontSize: 10,
      },
      nameGap: 8,
      axisLabel: {
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: isDark.value ? '#545251' : '#dccfcf',
        },
      },
      splitArea: {
        areaStyle: {
          color: isDark.value ? ['#191919', '#222'] : ['#f2f2f2', '#00000006'],
        },
      },
    },
    series: [
      {
        type: 'radar',
        data: [
          {
            value: [0.6, 0.4, 0, 0],
          },
        ],
        symbol: 'none',
        lineStyle: {
          color: 'rgb(204, 125, 36)',
        },
        areaStyle: { // 3 - dark 7
          color: isDark.value ? 'rgb(172, 97, 23)' : 'rgb(235, 202, 145)',
        },
      },
    ],
  }
})

let chart: echarts.ECharts | null
const refEl = ref()

function create() {
  if (!refEl.value)
    return
  chart = echarts.init(refEl.value)
  option.value && chart.setOption(option.value)
}

watch(option, () => {
  if (chart)
    chart.setOption(option.value)
})
onMounted(create)
onBeforeMount(() => {
  if (chart)
    chart.dispose()
})
</script>

<template>
  <div h-200px border border-base rounded flex="~ col" p-4>
    <div flex-y-center text-sm font-bold>
      <div text-sm font-bold>
        用户信息
      </div>
    </div>

    <div grid="~ cols-2" mt-3 flex-1 gap-2>
      <div col-span-1 flex="~ col">
        <div>
          <span select-none rounded-3 bg-dm px-2.5 py-1 font-bold>技能</span>
        </div>
        <div flex="1 center" mt-3>
          <div ref="refEl" h-100px w-full />
        </div>
      </div>
      <div col-span-1 flex="~ col">
        <div pl-2>
          <span select-none rounded-3 bg-dm px-2.5 py-1 font-bold>语言</span>
        </div>
        <div grid="~ rows-3" mt-3 flex-1 px-2 text-sm>
          <div row-span-1 flex-y-center justify-between>
            <div select-none rounded-3 bg-border px-2.5 py-1>
              Java
            </div>
            <div>
              解题数 <span ml-1 font-bold>1</span>
            </div>
          </div>
          <div row-span-1 flex-y-center justify-between>
            <div select-none rounded-3 bg-border px-2.5 py-1>
              C++
            </div>
            <div>
              解题数 <span ml-1 font-bold>1</span>
            </div>
          </div>
          <div row-span-1 flex-y-center justify-between>
            <div select-none rounded-3 bg-border px-2.5 py-1>
              Python
            </div>
            <div>
              解题数 <span ml-1 font-bold>1</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
