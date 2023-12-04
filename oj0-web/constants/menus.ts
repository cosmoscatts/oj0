import { ACCESS_ENUM } from './access-enum'

export const MENUS: Menu[] = [
  {
    id: 101,
    name: '首页',
    path: '/',
    access: ACCESS_ENUM.NOT_LOGIN,
    icon: '',
  },
  {
    id: 102,
    name: '题库',
    path: '/questions',
    access: ACCESS_ENUM.USER,
    icon: '',
  },
  {
    id: 103,
    name: '题目管理',
    path: '/questions-manage',
    access: ACCESS_ENUM.ADMIN,
    icon: '',
  },
]
