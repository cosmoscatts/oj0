import type { TableColumnData } from '@arco-design/web-vue'

export function getUserManageTableColumns(): TableColumnData[] {
  return [
    {
      title: '序号',
      dataIndex: 'id',
      slotName: 'id',
      align: 'center',
    },
    {
      title: '用户账号',
      dataIndex: 'userAccount',
      slotName: 'userAccount',
      align: 'center',
    },
    {
      title: '用户昵称',
      dataIndex: 'userName',
      slotName: 'userName',
      align: 'center',
    },
    {
      title: '用户头像',
      dataIndex: 'userAvatar',
      slotName: 'userAvatar',
      align: 'center',
    },
    {
      title: '用户简介',
      dataIndex: 'userProfile',
      slotName: 'userProfile',
      align: 'center',
    },
    {
      title: '用户角色',
      dataIndex: 'userRole',
      slotName: 'userRole',
      align: 'center',
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      slotName: 'createTime',
      align: 'center',
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      slotName: 'updateTime',
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
