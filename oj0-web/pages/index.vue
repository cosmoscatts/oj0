<script setup lang="ts">
import { codeToHtml } from 'shikiji'
import { ACCESS_ENUM } from '~/constants'
import { popularQuestionTags } from '~/config/question'
import avatar from '~/public/avatar.jpg'

definePageMeta({
  name: 'Home',
  title: '首页 - OJ0',
  access: ACCESS_ENUM.NOT_LOGIN,
  middleware: 'auth',
})

const router = useRouter()
function checkMoreQuestionInfo() {
  router.push('/questions')
}

const checkoutRandomQuestion = useThrottleFn(async () => {
  try {
    const { data, message } = await QuestionApi.getRandomQuestionId()
    if (!data) {
      Message.error(message || '获取随机题目发生异常')
      return
    }
    router.push(`/resolve/${data}`)
  }
  catch {
    Message.error('获取随机题目发生异常')
  }
}, 1000)

const htmlCode = ref('')

async function getCodeHtml() {
  const code = `public class Main {
  public static void main(String[] args) {
        Integer x = Integer.parseInt(args[0]);
        if(x < 0) {
          System.out.println(false);
          return;
        }
        int cur = 0;
        int num = x;
        while(num != 0) {
            cur = cur * 10 + num % 10;
            num /= 10;
        }
        System.out.println(cur == x);
  }
}`
  htmlCode.value = await codeToHtml(code, {
    lang: 'java',
    theme: isDark.value ? 'vitesse-dark' : 'vitesse-light',
  })
}
getCodeHtml()
watch(isDark, getCodeHtml)

const data = [
  {
    icon: 'i-ph-signpost-duotone',
    title: '技术栈',
    content: '基于 SpringCloud 微服务, Docker, MQ, Vue3, Vite, Nuxt, Arco Design',
  },
  {
    icon: 'i-ph-stack-duotone',
    title: '基础功能',
    content: '管理员管理用户，发布题目；用户可以浏览题库，答题，查看题目答案等',
  },
  {
    icon: 'i-ph-trophy-duotone',
    title: '拓展点',
    content: '实现了第三方登录及绑定，个人中心页面，做题统计等；着力美化页面，用户体验好',
  },
  {
    icon: 'i-ph-brackets-curly-bold',
    title: '代码',
    content: '代码规范简洁，使用多种设计模式，可维护性高',
  },
]

const refEl = ref(null)
const refEl2 = ref(null)
const refEl3 = ref(null)
const refEl4 = ref(null)
const refEl5 = ref(null)
const init = ref(false)
const init2 = ref(false)
const init3 = ref(false)
const init4 = ref(false)
const init5 = ref(false)
const elVisible = useElementVisibility(refEl)
const elVisible2 = useElementVisibility(refEl2)
const elVisible3 = useElementVisibility(refEl3)
const elVisible4 = useElementVisibility(refEl4)
const elVisible5 = useElementVisibility(refEl5)

watchOnce(elVisible, (n) => {
  if (n)
    init.value = true
})
watchOnce(elVisible2, (n) => {
  if (n)
    init2.value = true
})
watchOnce(elVisible3, (n) => {
  if (n)
    init3.value = true
})
watchOnce(elVisible4, (n) => {
  if (n)
    init4.value = true
})
watchOnce(elVisible5, (n) => {
  if (n)
    init5.value = true
})
</script>

<template>
  <div flex="col center" ha w-full>
    <div relative w-full bg-base md:py-40 sm:py-32 min-h="[calc(100vh-100px)]">
      <HomeHeroBackground absolute inset-x-0 top--150px hidden w-full lg:block />

      <div mx-auto mt-50px max-w-7xl flex flex-col gap-16 px-4 lt-sm:mt-200px sm:gap-y-32 lg:px-8 sm:px-6>
        <div
          relative z-1 text-center class="slide-enter" :style="{
            '--enter-stage': 2,
            '--enter-step': '200ms',
          }"
        >
          <h1 text-5xl font-bold tracking-tight text-gray-900 sm:text-7xl dark:text-white>
            The Elegant<br><span block text-primary lg:inline-block>Online Judge System</span>
          </h1>
        </div>

        <div
          text-center text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl dark:text-white class="slide-enter"
          :style="{
            '--enter-stage': 6,
            '--enter-step': '200ms',
          }"
        >
          简约并不简单
        </div>

        <div ref="refEl" mt-32 flex flex-col py-24 sm:py-32>
          <div
            text-3xl font-bold lg:text-5xl sm:text-4xl :class="init ? 'slide-enter' : ''" :style="{
              '--enter-stage': 2,
              '--enter-step': '200ms',
            }"
          >
            多种题型，一网打尽
          </div>
          <div grid grid-cols-6 mx-auto mt-10 w-full gap-8 lt-lg:grid-cols-4 lt-md:grid-cols-2>
            <div
              v-for="tag, idx in popularQuestionTags" :key="tag" col-span-1 border border-base rounded-10px bg-code
              px-5 py-3 text-center text-6
              :class="init
                ? idx > 3
                  ? 'lt-lg:hidden slide-enter'
                  : 'slide-enter'
                : idx > 3
                  ? 'lt-lg:hidden'
                  : ''"
              :style="{
                '--enter-stage': 2 + 1 * idx,
                '--enter-step': '200ms',
              }"
            >
              {{ tag }}
            </div>
          </div>
        </div>

        <div ref="refEl2" flex flex-col py-24 sm:py-32>
          <div grid mx-auto max-w-7xl gap-16 px-4 lg:grid-cols-2 lg:items-center sm:gap-y-24 lg:px-8 sm:px-6>
            <div>
              <h2
                text-3xl font-bold tracking-tight text-gray-900 lg:text-5xl sm:text-4xl dark:text-white :class="init2 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 2,
                  '--enter-step': '200ms',
                }"
              >
                <span>注意<br><span text-primary>答题规范</span></span>
              </h2>

              <p
                class="mt-6 text-lg/8 text-gray-600 dark:text-gray-300" :class="init2 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 4,
                  '--enter-step': '200ms',
                }"
              >
                <span>
                  OJ 系统中，对用户输入的代码做出了一定的限制。这样做的好处是便于系统进行统一处理和判题。因此，OJ 系统将用户输入代码的类名限制为
                  Main.java，这样一来，减少了编译代码时类名不一致的风险；同时，也省去了从用户代码中获取了类名的麻烦，极大地简化了开发。
                </span>
              </p>
              <div
                mt-8 flex flex-wrap gap-x-3 gap-y-1.5 :class="init2 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 6,
                  '--enter-step': '200ms',
                }"
              >
                <a
                  class="focus-visible:ring-primary-500 dark:focus-visible:ring-primary-400 inline-flex flex-shrink-0 cursor-pointer items-center gap-x-2 rounded-md bg-gray-900 px-3 py-2 text-sm font-medium text-white shadow-sm disabled:cursor-not-allowed dark:bg-white disabled:bg-gray-900 hover:bg-gray-800 dark:text-gray-900 disabled:opacity-75 focus-visible:outline-0 focus:outline-none focus-visible:ring-2 focus-visible:ring-inset dark:disabled:bg-white dark:hover:bg-gray-100"
                  @click="checkMoreQuestionInfo"
                >
                  <span i-ph-layout-duotone h-5 w-5 flex-shrink-0 />
                  <span>查看更多信息</span>
                </a>
                <a
                  class="focus-visible:ring-primary-500 dark:focus-visible:ring-primary-400 inline-flex flex-shrink-0 cursor-pointer items-center gap-x-2 rounded-md bg-gray-50 px-3 py-2 text-sm font-medium text-gray-700 shadow-sm ring-1 ring-gray-300 ring-inset disabled:cursor-not-allowed dark:bg-gray-800 disabled:bg-gray-50 hover:bg-gray-100 dark:text-gray-200 disabled:opacity-75 focus-visible:outline-0 focus:outline-none focus-visible:ring-2 dark:ring-gray-700 dark:disabled:bg-gray-800 dark:hover:bg-gray-700/50"
                  @click="checkoutRandomQuestion"
                >
                  <span class="i-ph-app-window-duotone h-5 w-5 flex-shrink-0" aria-hidden="true" />
                  <span>做一题试试吧</span>
                </a>
              </div>
            </div>

            <pre
              max-w-none prose prose-primary dark:prose-invert :class="init2 ? 'slide-enter' : ''" :style="{
                '--enter-stage': 8,
                '--enter-step': '200ms',
              }"
            >
              <div class="relative my-5 [&>pre]:!my-0 [&>pre]:!rounded-t-none">
                <div class="not-prose relative flex items-center gap-1.5 border border-b-0 border-gray-200 rounded-t-md bg-code px-4 py-3 dark:border-dark-700 dark:bg-dark-900">
                  <span class="text-sm/6 text-gray-700 dark:text-gray-200">Main.java</span>
                </div>
                <div mt--11.5 border border-gray-200 rounded-b-md bg-white p0 dark:border-dark-700 dark:bg-hex-121212 v-html="htmlCode" />
              </div>
            </pre>
          </div>
        </div>

        <div ref="refEl3" flex flex-col py-24 sm:py-32>
          <div grid mx-auto max-w-7xl gap-16 px-4 lg:grid-cols-2 lg:items-center sm:gap-y-24 lg:px-8 sm:px-6>
            <div
              relative overflow-hidden rounded-xl bg-white shadow ring-1 ring-gray-200 divide-y divide-gray-200
              dark:bg-dark-900 dark:ring-dark-800 dark:divide-dark-800 :class="init3 ? 'slide-enter' : ''" :style="{
                '--enter-stage': 8,
                '--enter-step': '200ms',
              }"
            >
              <div flex flex-col px-4 py-5 sm:p-6>
                <div>
                  <q text-gray-600 dark:text-gray-300>
                    OJ0 这个项目从 2023 年 12 月初开始正式开发，目前基本功能都已开发完成。项目中存在不少可以优化的地方，但是个人开发者难免时间有限，不过有时间我会持续修复的。
                  </q>
                  <div relative mt-6 flex items-center gap-3>
                    <span relative h-10 w-10 inline-flex flex-shrink-0 items-center justify-center rounded-full text-base>
                      <img h-10 w-10 rounded-full text-base :src="avatar">
                    </span>
                    <div>
                      <a focus:outline-none><span absolute inset-0 /></a>
                      <p text-sm font-semibold text-gray-900 dark:text-white>
                        赤径
                      </p>
                      <p text-sm text-gray-500 dark:text-gray-400>
                        Creator of oj0
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div>
              <h2
                text-3xl font-bold tracking-tight text-gray-900 lg:text-5xl sm:text-4xl dark:text-white :class="init3 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 2,
                  '--enter-step': '200ms',
                }"
              >
                <span>多策略 多语言<br><span text-primary>丰富的其他功能</span></span>
              </h2>

              <p
                class="mt-6 text-lg/8 text-gray-600 dark:text-gray-300" :class="init3 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 4,
                  '--enter-step': '200ms',
                }"
              >
                <span>
                  不同的题型，不同的判题策略，应有尽有。目前只支持 Java 语言，未来会支持其他多种语言。实现了第三方登录，如 Github 或者 Gitee 等等；实现了个人中心及做题统计功能，更好地分析，查漏补缺。
                </span>
              </p>
              <div
                mt-8 flex flex-wrap gap-x-3 gap-y-1.5 :class="init3 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 6,
                  '--enter-step': '200ms',
                }"
              >
                <a
                  class="focus-visible:ring-primary-500 dark:focus-visible:ring-primary-400 inline-flex flex-shrink-0 cursor-pointer items-center gap-x-2 rounded-md bg-gray-900 px-3 py-2 text-sm font-medium text-white shadow-sm disabled:cursor-not-allowed dark:bg-white disabled:bg-gray-900 hover:bg-gray-800 dark:text-gray-900 disabled:opacity-75 focus-visible:outline-0 focus:outline-none focus-visible:ring-2 focus-visible:ring-inset dark:disabled:bg-white dark:hover:bg-gray-100"
                  @click="router.push('/user-profile')"
                >
                  <span i-ph-layout-duotone h-5 w-5 flex-shrink-0 />
                  <span>编辑个人信息</span>
                </a>
                <a
                  class="focus-visible:ring-primary-500 dark:focus-visible:ring-primary-400 inline-flex flex-shrink-0 cursor-pointer items-center gap-x-2 rounded-md bg-gray-50 px-3 py-2 text-sm font-medium text-gray-700 shadow-sm ring-1 ring-gray-300 ring-inset disabled:cursor-not-allowed dark:bg-gray-800 disabled:bg-gray-50 hover:bg-gray-100 dark:text-gray-200 disabled:opacity-75 focus-visible:outline-0 focus:outline-none focus-visible:ring-2 dark:ring-gray-700 dark:disabled:bg-gray-800 dark:hover:bg-gray-700/50"
                  @click="router.push('/summary')"
                >
                  <span class="i-ph-app-window-duotone h-5 w-5 flex-shrink-0" aria-hidden="true" />
                  <span>查看做题统计</span>
                </a>
              </div>
            </div>
          </div>
        </div>

        <div ref="refEl4" py-24 sm:py-32>
          <div mx-auto max-w-7xl flex flex-col gap-16 px-4 sm:gap-y-24 lg:px-8 sm:px-6>
            <div flex flex-col items-center text-center>
              <h2
                text-3xl font-bold tracking-tight text-gray-900 lg:text-5xl sm:text-4xl dark:text-white :class="init4 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 2,
                  '--enter-step': '200ms',
                }"
              >
                <span>
                  架构合理<br><span text-primary>体验更如意</span>
                </span>
              </h2>
            </div>

            <div grid grid-cols-1 gap-8 sm:grid-cols-2 xl:grid-cols-4>
              <HomeWrapperItem
                v-for="item, idx in data" :key="item.title" :="item" :class="init4 ? 'slide-enter' : ''" :style="{
                  '--enter-stage': 4 + idx * 2,
                  '--enter-step': '200ms',
                }"
              />
            </div>
          </div>
        </div>

        <div ref="refEl5" pt-24 sm:pt-32>
          <div mx-auto max-w-7xl flex flex-col gap-16 px-4 sm:gap-y-24 lg:px-8 sm:px-6>
            <div
              flex flex-col items-center text-center :class="init5 ? 'slide-enter' : ''" :style="{
                '--enter-stage': 2,
                '--enter-step': '200ms',
              }"
            >
              <h2 text-3xl font-bold tracking-tight text-gray-900 lg:text-5xl sm:text-4xl dark:text-white>
                <span>
                  纵有疾风起，<br><span text-primary>人生不言弃。共勉！</span>
                </span>
              </h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
:is([prose=""],.prose) :where(pre):not(:where(.not-prose,.not-prose *)) {
  padding: 0 1.25rem;
}
</style>
