import type { ACCESS_ENUM } from './constants'

import 'vue-router'

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
    icon?: string
    access?: string // 菜单项所需要的用户权限
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
  }
}

export {}
