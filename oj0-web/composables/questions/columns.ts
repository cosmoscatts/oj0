import type { TableColumnData } from '@arco-design/web-vue'

export function getQuestionsTableColumns(): TableColumnData[] {
  return [
    {
      title: '状态',
      dataIndex: 'state',
      slotName: 'state',
      align: 'center',
    },
    {
      title: '题号',
      dataIndex: 'id',
      slotName: 'id',
      align: 'center',
    },
    {
      title: '题目',
      dataIndex: 'title',
      slotName: 'title',
      align: 'center',
    },
    {
      title: '题解',
      dataIndex: 'answer',
      slotName: 'answer',
      align: 'center',
    },
    {
      title: '通过率',
      dataIndex: 'acceptPercent',
      slotName: 'acceptPercent',
      align: 'center',
    },
    {
      title: '标签',
      dataIndex: 'tags',
      slotName: 'tags',
      align: 'center',
    },
    {
      title: '难度',
      dataIndex: 'difficulty',
      slotName: 'difficulty',
      align: 'center',
    },
    {
      title: '操作',
      dataIndex: 'action',
      slotName: 'action',
      align: 'center',
    },
  ]
}
