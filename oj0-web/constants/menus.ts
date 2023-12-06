import { ACCESS_ENUM } from './access-enum'
import IconHome from '~icons/ri/home-2-fill'
import IconUserManage from '~icons/ri/user-2-line'
import IconQuestions from '~icons/ri/file-list-line'
import IconQuestionManage from '~icons/ri/crop-line'
import IconSubmitHistory from '~icons/ri/history-line'

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
    name: '用户管理',
    path: '/user-manage',
    access: ACCESS_ENUM.ADMIN,
    icon: IconUserManage,
  },
  {
    id: 103,
    name: '题目管理',
    path: '/question-manage',
    access: ACCESS_ENUM.ADMIN,
    icon: IconQuestionManage,
  },
  {
    id: 104,
    name: '题库',
    path: '/questions',
    access: ACCESS_ENUM.USER,
    icon: IconQuestions,
  },
  {
    id: 105,
    name: '历史提交',
    path: '/submit-history',
    access: ACCESS_ENUM.USER,
    icon: IconSubmitHistory,
  },
]
