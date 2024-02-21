<script setup lang="ts">
import * as echarts from 'echarts'

const data = ref<ResolveAnalysisChartData>({
  accepted: [0, 0, 0],
  unaccepted: [0, 0, 0],
  unStarted: [0, 0, 0],
})

const chartData = computed(() => {
  return [
    {
      name: '已通过题目',
      children: [
        {
          name: '已通过题目-简单',
          value: data.value.accepted?.[0] || 0,
          itemStyle: {
            color: '#86efac',
          },
        },
        {
          name: '已通过题目-中等',
          value: data.value.accepted?.[1] || 0,
          itemStyle: {
            color: '#4ade80',
          },
        },
        {
          name: '已通过题目-困难',
          value: data.value.accepted?.[2] || 0,
          itemStyle: {
            color: '#16a34a',
          },
        },
      ],
      itemStyle: {
        color: '#4ade80',
      },
    },
    {
      name: '提交未通过题目',
      children: [
        {
          name: '提交未通过题目-简单',
          value: data.value.unaccepted?.[0] || 0,
          itemStyle: {
            color: '#fca5a5',
          },
        },
        {
          name: '提交未通过题目-中等',
          value: data.value.unaccepted?.[1] || 0,
          itemStyle: {
            color: '#f87171',
          },
        },
        {
          name: '提交未通过题目-困难',
          value: data.value.unaccepted?.[2] || 0,
          itemStyle: {
            color: '#dc2626',
          },
        },
      ],
      itemStyle: {
        color: '#f87171',
      },
    },
    {
      name: '未开始题目',
      children: [
        {
          name: '未开始题目-简单',
          value: data.value.unStarted?.[0] || 0,
          itemStyle: {
            color: isDark.value ? '#d6d3d1' : '#d4d4d4',
          },
        },
        {
          name: '未开始题目-中等',
          value: data.value.unStarted?.[1] || 0,
          itemStyle: {
            color: isDark.value ? '#a8a29e' : '#a3a3a3',
          },
        },
        {
          name: '未开始题目-困难',
          value: data.value.unStarted?.[2] || 0,
          itemStyle: {
            color: isDark.value ? '#57534e' : '#525252',
          },
        },
      ],
      itemStyle: {
        color: isDark.value ? '#a8a29e' : '#a3a3a3',
      },
    },
  ]
})

const option = computed(() => {
  const textColor = isDark.value ? '#f3f3f3' : '#232323'
  return {
    series: {
      type: 'sunburst',
      data: chartData.value,
      nodeClick: 'none',
      radius: [20, '100%'],
      itemStyle: {
        borderRadius: 7,
        borderWidth: 2,
        borderColor: isDark.value ? '#222' : '#eee',
      },
      label: {
        show: false,
      },
    },
    tooltip: {
      position(point: any) {
        return point
      },
      textStyle: {
        color: textColor,
      },
      borderColor: isDark.value ? '#222' : '#eee',
      backgroundColor: isDark.value ? 'rgb(17, 17, 17)' : 'rgb(250, 250, 250)',
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

watch([isDark, chartData], () => {
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
  <div ha w-full flex-center>
    <div ref="refEl" h-200px w-full />
  </div>
</template>
