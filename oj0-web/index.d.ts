import 'vue-router'
import type { FunctionalComponent } from 'vue'
import type { ACCESS_ENUM } from './constants'

declare global {
  /**
   * Null or whatever
   */
  type Nullable<T> = T | null | undefined

  /**
   * Function
   */
  type Fn<T = void> = () => T

  /**
   * 返回结果
   */
  interface Result<T> {
    code?: number
    data: T
    message?: string
  }

  /**
   * 分页返回结果
   */
  interface PageResult<T> {
    code?: number
    data: {
      records: T[]
      total: number
    }
    message?: string
  }

  /**
   * 分页
   */
  interface Pagination {
    current: number
    pageSize: number
    total?: number
  }

  /**
   * 用户
   */
  interface User {
    id: number
    userAccount?: string // 用户账号
    userName?: string // 用户昵称
    unionId?: string // 开放平台id
    mpOpenId?: string // 公众号openId
    userAvatar?: string // 用户头像
    userProfile?: string // 用户简介
    userRole?: string // 用户角色 notLogin/user/admin
    userPassword?: string // 用户密码
    createTime?: Date // 创建时间
    updateTime?: Date // 更新时间
  }

  /**
   * 菜单
   */
  interface Menu {
    id: number
    name: string
    path: string
    icon?: FunctionalComponent
    access?: string // 菜单项所需要的用户权限
  }

  interface JudgeCase {
    input?: string
    output?: string
  }

  interface JudgeConfig {
    timeLimit?: number
    memoryLimit?: number
    stackLimit?: number
  }

  /**
   * 题目 (实体)
   */
  interface QuestionDO {
    id: number
    title?: string
    content?: string
    tags?: string // 标签
    difficulty?: string // 题目难度
    answer?: string // 题解
    submitNum?: number
    acceptedNum?: number
    judgeCase?: string // 判题用例
    judgeConfig?: string // 判题配置
    userId?: number // 创建用户 id
    createTime?: Date
    updateTime?: Date
  }

  /**
   * 题目
   */
  interface Question {
    id: number
    title?: string
    content?: string
    tags?: string[] // 标签
    difficulty?: string // 题目难度
    answer?: string // 题解
    submitNum?: number
    acceptedNum?: number
    judgeCase?: JudgeCase[] // 判题用例
    judgeConfig?: JudgeConfig // 判题配置
    userId?: number // 创建用户 id
    createTime?: Date
    updateTime?: Date
  }

  /**
   * 题目提交
   */
  interface QuestionSubmit {
    id: number
    questionId?: number // 题目 id
    questionTitle: string // 题目标题
    language?: string // 编程语言
    state?: number // 0 - 等待中；1 - 解答错误；2 - 通过；
    useMemory?: number // 消耗内存
    useTime?: number // 用时
    userId?: number // 提交人
    createTime?: Date
  }
}

declare module '#app' {
  interface PageMeta {
    access?: string
  }
}

declare module 'vue-router' {
  interface RouteMeta {
    access?: string
    title?: string
  }
}

export {}
