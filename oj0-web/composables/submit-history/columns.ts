import type { TableColumnData } from '@arco-design/web-vue'

export function getSubmitHistoryTableColumns(): TableColumnData[] {
  return [
    {
      title: '序号',
      dataIndex: 'id',
      slotName: 'id',
      align: 'center',
    },
    {
      title: '题目',
      dataIndex: 'questionId',
      slotName: 'questionId',
      align: 'center',
    },
    {
      title: '编程语言',
      dataIndex: 'language',
      slotName: 'language',
      align: 'center',
    },
    {
      title: '判题结果',
      dataIndex: 'state',
      slotName: 'state',
      align: 'center',
    },
    {
      title: '消耗内存',
      dataIndex: 'useMemory',
      slotName: 'useMemory',
      align: 'center',
    },
    {
      title: '执行用时',
      dataIndex: 'useTime',
      slotName: 'useTime',
      align: 'center',
    },
    {
      title: '提交人',
      dataIndex: 'userId',
      slotName: 'userId',
      align: 'center',
    },
    {
      title: '提交时间',
      dataIndex: 'createTime',
      slotName: 'createTime',
      align: 'center',
    },
  ]
}
