import dayjs from 'dayjs'
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

export { dayjs, ANotification, Message, Modal }
