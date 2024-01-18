<script setup lang="ts">
import * as echarts from 'echarts'
import { RESOLVE_LANGUAGE_ENUM } from '~/constants'

const { userId } = defineProps<{
  userId?: string | number
}>()

const acceptedData = ref<QuestionSubmit[]>([]) // 题目通过的提交信息，包含重复题目
const questionTagMap = ref<Record<string, string[]>>({}) // 题目 id 和 标签数组的映射
const chartData = ref<number[]>([])

async function getChartData() {
  const data = await getQuestionClassification()
  questionTagMap.value = data
}
getChartData()

/**
 * 查询所有判题成功的提交信息并过滤出通过的信息
 */
async function fetchAcceptedData() {
  if (!userId)
    return
  const { data: { records } } = await QuestionSubmitApi.list({
    userId,
    status: 2,
    sortField: 'createTime',
    sortOrder: 'descend',
    pageSize: -1,
  })
  acceptedData.value = records?.filter(i => i.judgeInfo && i.judgeInfo?.message === 'Accepted') || []
  createChartData()
}
fetchAcceptedData()
watch(() => userId, fetchAcceptedData)

const javaAcceptedNum = computed(() => {
  if (!acceptedData.value?.length)
    return 0
  const javaDataIds = acceptedData.value.filter(i => i.language === RESOLVE_LANGUAGE_ENUM.JAVA)
    .map(i => i.questionId)
  const ids = [...new Set(javaDataIds)]
  return ids.length
})
const cppAcceptedNum = computed(() => {
  if (!acceptedData.value?.length)
    return 0
  const javaDataIds = acceptedData.value.filter(i => i.language === RESOLVE_LANGUAGE_ENUM.CPP)
    .map(i => i.questionId)
  const ids = [...new Set(javaDataIds)]
  return ids.length
})
const goAcceptedNum = computed(() => {
  if (!acceptedData.value?.length)
    return 0
  const javaDataIds = acceptedData.value.filter(i => i.language === RESOLVE_LANGUAGE_ENUM.GO)
    .map(i => i.questionId)
  const ids = [...new Set(javaDataIds)]
  return ids.length
})

function createChartData() {
  const result = Array(6).fill(0)
  const idSet = [...new Set(acceptedData.value?.map(i => i.questionId) || [])]
  if (!idSet.length || !questionTagMap.value || !Object.keys(questionTagMap.value).length)
    return result
  idSet.forEach((id) => {
    const tags = questionTagMap.value[String(id!)] || []
    if (tags.length) {
      for (const tag of tags) {
        if (tag === '基本')
          result[0]++
        else if (tag === '算法')
          result[1]++
        else if (tag === '数据结构')
          result[2]++
        else if (tag === '技巧')
          result[3]++
        else if (tag === '数学')
          result[4]++
        else if (tag === '其他')
          result[5]++
      }
    }
  })
  chartData.value = result.map(i => i > 0 ? Number((i / idSet.length).toFixed(2)) : 0)
}

watch(questionTagMap, createChartData)

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
        { name: '基本', max: 1 },
        { name: '算法', max: 1 },
        { name: '数据结构', max: 1 },
        { name: '技巧', max: 1 },
        { name: '数学', max: 1 },
        { name: '其他', max: 1 },
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
            value: chartData.value,
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

watch([chartData, isDark], () => {
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
              解题数 <span ml-1 font-bold>{{ javaAcceptedNum }}</span>
            </div>
          </div>
          <div row-span-1 flex-y-center justify-between>
            <div select-none rounded-3 bg-border px-2.5 py-1>
              C++
            </div>
            <div>
              解题数 <span ml-1 font-bold>{{ cppAcceptedNum }}</span>
            </div>
          </div>
          <div row-span-1 flex-y-center justify-between>
            <div select-none rounded-3 bg-border px-2.5 py-1>
              Go
            </div>
            <div>
              解题数 <span ml-1 font-bold>{{ goAcceptedNum }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
