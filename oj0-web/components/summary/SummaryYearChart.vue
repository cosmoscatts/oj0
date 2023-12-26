<script setup lang="ts">
import * as echarts from 'echarts'

function getVirtualData(year: string) {
  const date = +echarts.time.parse(`${year}-01-01`)
  const end = +echarts.time.parse(`${year}-12-31`)
  const dayTime = 3600 * 24 * 1000
  const data = []
  for (let time = date; time <= end; time += dayTime) {
    data.push([
      echarts.time.format(time, '{yyyy}-{MM}-{dd}', false),
      Math.floor(Math.random() * 10000),
    ])
  }
  return data
}

const option = computed(() => {
  const textColor = isDark.value ? '#f3f3f3' : '#232323'
  return {
    visualMap: {
      show: false,
      min: 0,
      max: 10000,
      inRange: {
        color: ['#f1e8e6', '#22c55e', '#16a34a', '#14532d'],
      },
    },
    calendar: {
      range: '2023',
      left: 26,
      top: 40,
      cellSize: [13, 13],
      splitLine: {
        show: false,
      },
      itemStyle: {
        borderWidth: 0,
      },
      dayLabel: {
        color: textColor,
      },
      monthLabel: {
        color: textColor,
      },
      yearLabel: {
        show: false,
      },
    },
    tooltip: {
      show: true,
      backgroundColor: isDark.value ? 'rgb(17, 17, 17)' : 'rgb(250, 250, 250)',
    },
    series: {
      type: 'heatmap',
      coordinateSystem: 'calendar',
      data: getVirtualData('2023'),
      markArea: {
        itemStyle: {
          color: 'red',
        },
      },
    },
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

watch(isDark, () => {
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
  <div ha w-full flex="col center" of-x-auto of-y-hidden border border-base rounded py-4 pb-0>
    <div w-full flex-y-center justify-between px-4 text-lg font-bold>
      <span>过去一年共提交 173 次</span>
      <span>2023</span>
    </div>
    <div min-w-48rem flex-center lt-sm="min-w-740px ml-340px">
      <div ref="refEl" h-150px w-full />
    </div>
  </div>
</template>
