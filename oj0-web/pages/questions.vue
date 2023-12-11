<script setup lang="ts">
import { ACCESS_ENUM } from '~/constants'

definePageMeta({
  name: 'Questions',
  access: ACCESS_ENUM.USER, // 需要用户权限
  // middleware: 'auth',
})

const router = useRouter()

const columns = getQuestionsTableColumns()

const selectedRadioValue = ref<string>()

const { loading } = useLoading()

const paginator = useTablePagination(search)

const tableData = ref<Question[]>()
function search() {
  tableData.value = Array.from({ length: 6 }, (_, idx) => {
    return {
      id: idx + 1,
      state: getRandomInteger(2),
      title: getRandomStr(6),
      solutionNum: getRandomInteger(10),
      tags: Array(3).fill('').map(() => {
        return questionTagOptions[getRandomInteger(questionTagOptions.length)].value as string
      }),
      acceptedNum: getRandomInteger(100, 20),
      difficulty: 'easy',
      answer: getRandomInteger(2) < 1 ? 'hhh' : '',
    }
  })
}
search()

function getRandomQuestion() {
  router.push('/resolve/1111')
}
</script>

<template>
  <div min-h-500px w-full>
    <QuestionsFilterRadioGroup v-model="selectedRadioValue" />

    <div grid="~ cols-10" mt-5 gap-4 lt-lg:hidden>
      <QuestionsSearchForm col-span-9 />

      <div col-span-1 flex justify-end gap-3>
        <div mt-2px h-32px flex-center>
          <div flex-center gap-2 btn-solid @click="getRandomQuestion">
            <button i-ri-shuffle-line />
            <span lt-xl:hidden>随机一题</span>
          </div>
        </div>
      </div>
    </div>

    <a-table
      mt-4
      row-key="id"
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :bordered="false"
      :pagination="paginator.paginationProps.value"
      @page-change="paginator.onPageChange"
      @page-size-change="paginator.onPageSizeChange"
    >
      <template #state="{ record }">
        <div v-if="record.state === 1" w-full flex-center text-lg>
          <div i-ri-check-line text-green />
        </div>
      </template>
      <template #title="{ record }">
        <a v-if="record.title" cursor-pointer hover="underline text-primary">
          {{ record.title }}
        </a>
      </template>
      <template #answer="{ record }">
        {{ record.answer ? '已有题解' : '暂未发布' }}
      </template>
      <template #acceptPercent="{ record }">
        {{ record.acceptedNum ?? 0 }} %
      </template>
      <template #tags="{ record }">
        <div v-if="record.tags.length" w-250px>
          <CommonTooltip :content="record.tags.reduce((prev: string, cur: string) => `${prev}, ${cur}`, '').slice(1)">
            <a-overflow-list>
              <a-tag v-for="tag in record.tags" :key="tag" bordered color="transparent" :style="{ color: 'var(--c-text-base)' }">
                {{ tag }}
              </a-tag>
            </a-overflow-list>
          </CommonTooltip>
        </div>
      </template>
      <template #difficulty="{ record }">
        <span v-if="record.difficulty === 'easy'" text-green>简单</span>
        <span v-if="record.difficulty === 'medium'" text-orange>中等</span>
        <span v-if="record.difficulty === 'hard'" text-hard>困难</span>
      </template>
    </a-table>
  </div>
</template>
