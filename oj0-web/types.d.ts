export { }
declare global {
  /**
   * Null or whatever
   */
  type Nullable<T> = T | null | undefined

  /**
   * Array, or not yet
   */
  type Arrayable<T> = T | Array<T>

  /**
   * Function
   */
  type Fn<T = void> = () => T

  /**
   * 用户
   */
  interface User {

  }

  /**
   * 菜单
   */
  interface Menu {

  }
}
