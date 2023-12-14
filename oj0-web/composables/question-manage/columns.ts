import type { TableColumnData } from '@arco-design/web-vue'

export function getQuestionManageTableColumns(): TableColumnData[] {
  return [
    {
      title: '序号',
      dataIndex: 'id',
      slotName: 'id',
      align: 'center',
    },
    {
      title: '标题',
      dataIndex: 'title',
      slotName: 'title',
      align: 'center',
    },
    {
      title: '内容',
      dataIndex: 'content',
      slotName: 'content',
      align: 'center',
      width: 160,
      ellipsis: true,
      tooltip: true,
    },
    {
      title: '难度',
      dataIndex: 'difficulty',
      slotName: 'difficulty',
      align: 'center',
    },
    {
      title: '标签',
      dataIndex: 'tags',
      slotName: 'tags',
      align: 'center',
      width: 200,
    },
    {
      title: '创建人',
      dataIndex: 'userId',
      slotName: 'userId',
      align: 'center',
      width: 120,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      slotName: 'createTime',
      align: 'center',
      width: 180,
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      slotName: 'updateTime',
      align: 'center',
      width: 180,
    },
    {
      title: '操作',
      dataIndex: 'action',
      slotName: 'action',
      align: 'center',
      width: 150,
    },
  ]
}
