<script setup lang="ts">
import * as echarts from 'echarts'

const { userId } = defineProps<{
  userId?: string | number
}>()

const yearInfo = getSummaryYearOptionData()

const year = ref(yearInfo.year)
const yearSubmitDataDateMap = ref<Record<string, number>>({})
const chartData = ref<(string | number)[][]>([])
const yearSubmitNum = ref(0)

const yearOptions = [...yearInfo.yearOptions]

/**
 * 查询用户某年年的所有提交记录
 */
async function fetchSubmitData() {
  if (!userId || !year.value)
    return

  const [createTimeMin, createTimeMax] = [`${year.value}-01-01 00:00:00`, `${year.value}-12-31 23:59:59`]
  const { data: { records } } = await QuestionSubmitApi.list({ userId, createTimeMin, createTimeMax, pageSize: -1 })
  yearSubmitDataDateMap.value = records?.reduce((prev, cur) => {
    const date = formatDate(cur.createTime, 'YYYY-MM-DD')
    let cnt = 0
    if (date in prev)
      cnt = prev[date]
    prev[date] = cnt + 1
    return prev
  }, {} as Record<string, number>) || {}
  yearSubmitNum.value = records?.length || 0
}

async function getVirtualData() {
  await fetchSubmitData()

  const date = +echarts.time.parse(`${year.value}-01-01`)
  const end = +echarts.time.parse(`${year.value}-12-31`)
  const dayTime = 3600 * 24 * 1000
  const data = []
  for (let time = date; time <= end; time += dayTime) {
    const dateStr = dayjs(time).format('YYYY-MM-DD')
    let value = 0
    if (yearSubmitDataDateMap.value && Object.keys(yearSubmitDataDateMap.value).length)
      value = yearSubmitDataDateMap.value[dateStr] || 0

    data.push([
      echarts.time.format(time, '{yyyy}-{MM}-{dd}', false),
      value,
    ])
  }
  chartData.value = data
}

getVirtualData()
watch([() => userId, year], getVirtualData)

const option = computed(() => {
  const textColor = isDark.value ? '#f3f3f3' : '#232323'
  return {
    visualMap: {
      show: false,
      min: 0,
      max: 30,
      inRange: {
        color: [isDark.value ? '#171717' : '#F4F4F4', '#22c55e', '#16a34a', '#14532d'],
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
        borderWidth: 1,
        borderColor: isDark.value ? '#545251' : '#dccfcf',
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
      borderColor: isDark.value ? '#222' : '#eee',
      backgroundColor: isDark.value ? 'rgb(17, 17, 17)' : 'rgb(250, 250, 250)',
      formatter(params: any) {
        return `<b>${params.data[0]}:</b> <b style="margin-left: 5px;">${params.data[1]} 次</b>`
      },
    },
    series: {
      type: 'heatmap',
      coordinateSystem: 'calendar',
      data: chartData.value,
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

// 是否是本人
const authStore = useAuthStore()
const isSelf = computed(() => userId === authStore.user?.id)
</script>

<template>
  <div ha w-full flex="col center" of-x-auto of-y-hidden border border-base rounded py-4 pb-0>
    <div w-full flex-y-center justify-between px-4 text-lg font-bold>
      <span><span>{{ isSelf ? '您' : 'TA' }}</span>过去一年共提交 {{ yearSubmitNum }} 次</span>
      <a-select v-model="year" :options="yearOptions" size="mini" w-100px rounded />
    </div>
    <div min-w-48rem flex-center lt-sm="min-w-740px ml-340px">
      <div ref="refEl" h-150px w-full />
    </div>
  </div>
</template>
