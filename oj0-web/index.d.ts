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
    submitNum?: number // 题目提交数
    acceptedNum?: number // 题目通过数
    judgeCase?: string // 判题用例
    judgeConfig?: string // 判题配置
    userId?: number // 创建用户 id
    createTime?: Date
    updateTime?: Date
  }

  /**
   * 题目（特殊处理 tags/judgeCase/judgeConfig）
   */
  interface Question {
    id: number
    title?: string
    content?: string
    tags?: string[] // 标签
    difficulty?: string // 题目难度
    answer?: string // 题目答案
    submitNum?: number // 题目提交数
    acceptedNum?: number // 题目通过数
    judgeCase?: JudgeCase[] // 判题用例
    judgeConfig?: JudgeConfig // 判题配置
    userId?: number // 创建用户 id
    createTime?: Date
    updateTime?: Date
  }

  /**
   * 判题结果信息
   */
  interface JudgeInfo {
    /**
     * 程序执行信息
     * 当提交信息的 status = 2 时，判断 message：
     *  - Accepted: 通过
     *  - 其余则是解答错误
     */
    message?: string
    memory?: number // 消耗内存
    time?: number // 执行耗时
  }

  /**
   * 题目提交
   */
  interface QuestionSubmit {
    id: number
    language?: string // 编程语言
    code?: string // 用户提交的代码
    judgeInfo?: JudgeInfo // 判题信息
    status?: number // 判题状态
    questionId?: number // 题目 id
    userId?: number // 提交人
    createTime?: Date // 创建时间
    updateTime?: Date // 更新时间
    userVO?: User
    questionVO?: Question
  }

  /**
   * 第三方平台登录绑定信息
   */
  interface ExtraAuthBound {
    canUnbound?: boolean
    github?: boolean
    gitee?: boolean
    qq?: boolean
    wechat?: boolean
  }

  /**
   * 做题分析
   */
  interface ResolveAnalysisItem {
    questionId?: number
    title?: string
    difficulty?: string
    count?: number
    lastSubmitTime?: Date
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
