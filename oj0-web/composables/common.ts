import dayjs from 'dayjs'
import lodash from 'lodash'
import { Notification as ANotification, Message, Modal } from '@arco-design/web-vue'

export function batchInvoke(functions: Nullable<Fn>[]) {
  functions.forEach(fn => fn && fn())
}

/**
 * 获取随机整数
 */
export function getRandomInteger(end = 100, start = 0): number {
  return Math.floor(Math.random() * (end - start) + start)
}

/**
 * 获取随机字符串
 *
 * port from nanoid
 * https://github.com/ai/nanoid
 */
const urlAlphabet = 'useandom-26T198340PX75pxJACKVERYMINDBUSHWOLF_GQZbfghjklqvwyzrict'
export function getRandomStr(size = 16, dict = urlAlphabet): string {
  let id = ''
  let i = size
  const len = dict.length
  while (i--) id += dict[(Math.random() * len) | 0]
  return id
}

export function useLoading(initValue = false) {
  const loading = ref(initValue)
  return {
    loading,
    startLoading() {
      loading.value = true
    },
    endLoading() {
      loading.value = false
    },
    toggleLoading() {
      loading.value = !loading.value
    },
  }
}

export function clone<T = any>(source: T) {
  return JSON.parse(JSON.stringify(source)) as T
}

export function cloneDeep<T = any>(source: T) {
  return lodash.cloneDeep<T>(source)
}

export function formatDate(date?: Date | string, partten = 'YY-MM-DD HH:mm:ss') {
  if (!date)
    return ''
  return dayjs(date).format(partten)
}

export { dayjs, ANotification, Message, Modal }
