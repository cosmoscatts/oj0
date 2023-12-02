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
    username: string
    name?: string
    password: string
    role: string
    avatar?: string
  }

  /**
   * 菜单
   */
  interface Menu {
    id: number
    name: string
    path?: string
    icon?: string
    access?: string // 菜单项所需要的用户权限
    parentId?: number
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
