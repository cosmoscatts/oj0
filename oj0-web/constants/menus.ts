import { ACCESS_ENUM } from './access-enum'
import IconHome from '~icons/ri/home-2-fill'
import IconQuestions from '~icons/ri/file-list-line'
import IconQuestionsManage from '~icons/ri/crop-line'

export const MENUS: Menu[] = [
  {
    id: 101,
    name: '首页',
    path: '/',
    access: ACCESS_ENUM.NOT_LOGIN,
    icon: IconHome,
  },
  {
    id: 102,
    name: '题库',
    path: '/questions',
    access: ACCESS_ENUM.USER,
    icon: IconQuestions,
  },
  {
    id: 103,
    name: '题目管理',
    path: '/questions-manage',
    access: ACCESS_ENUM.ADMIN,
    icon: IconQuestionsManage,
  },
]
