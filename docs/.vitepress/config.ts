import type { DefaultTheme } from 'vitepress'
import { defineConfig } from 'vitepress'
import vite from './vite.config'

const INTRODUCTIONS: DefaultTheme.NavItemWithLink[] = [
  { text: '项目简介', link: '/introductions/' },
  { text: '核心业务流程', link: '/introductions/business' },
  { text: '主流实现方案', link: '/introductions/implements' },
  { text: '功能', link: '/introductions/function' },
  { text: '技术选型', link: '/introductions/technology' },
  { text: '架构设计', link: '/introductions/architecture' },
  { text: '可扩展点', link: '/introductions/extend' },
]

const GUIDES: DefaultTheme.NavItemWithLink[] = [
  { text: '开始', link: '/guides/' },
  { text: '准备工作', link: '/guides/prepare' },
  { text: '环境', link: '/guides/env' },
]

const REFERENCES: DefaultTheme.NavItemWithLink[] = [
  { text: 'Bytemd', link: '/references/bytemd' },
  { text: 'Monaco Editor', link: '/references/monaco' },
  { text: '第三方登录', link: '/references/login' },
  { text: '图床', link: '/references/picture' },
]

const ABOUTS: DefaultTheme.NavItemWithLink[] = [
  { text: '项目', link: '/abouts/project' },
  { text: '作者', link: '/abouts/me' },
]

const VERSIONS: DefaultTheme.NavItemWithLink[] = [
  { text: 'v1.0.0 (当前)', link: '/' },
  { text: '更新日志', link: '/update-log' }
]

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: '/oj/',
  lang: 'zh-Hans',
  title: 'OJ0',
  description: 'An Elegant Online Judge System',
  markdown: {
    image: {
      lazyLoading: true,
    },
    theme: {
      light: 'vitesse-light',
      dark: 'vitesse-dark',
    },
    codeTransformers: [
      {
        // Render custom themes with codeblocks
        name: 'shikiji:inline-theme',
        preprocess(code, options) {
          const reg = /\btheme:([\w,-]+)\b/
          const match = options.meta?.__raw?.match(reg)
          if (!match?.[1])
            return
          const theme = match[1]
          const themes = theme.split(',').map(i => i.trim())
          if (!themes.length)
            return
          if (themes.length === 1) {
            // @ts-expect-error anyway
            delete options.themes
            // @ts-expect-error anyway
            options.theme = themes[0]
          }
          else if (themes.length === 2) {
            // @ts-expect-error anyway
            delete options.theme
            // @ts-expect-error anyway
            options.themes = {
              light: themes[0],
              dark: themes[1],
            }
          }
          else {
            throw new Error(`Only 1 or 2 themes are supported, got ${themes.length}`)
          }
          return code
        },
      },

      {
        name: 'shikiji:remove-escape',
        postprocess(code) {
          return code.replace(/\[\\\!code/g, '[!code')
        },
      },
    ],
  },
  vite,
  cleanUrls: true,
  themeConfig: {
    logo: '/logo.svg',
    nav: [
      {
        text: '项目介绍',
        items: [
          {
            items: INTRODUCTIONS,
          },
        ],
      },
      {
        text: '使用指南',
        items: [
          {
            items: GUIDES,
          },
        ],
      },
      {
        text: '参考',
        items: [
          {
            items: REFERENCES,
          },
        ],
      },
      {
        text: '关于',
        items: ABOUTS,
      },
      {
        text: 'v1.0.0',
        items: VERSIONS,
      },
    ],

    sidebar: Object.assign(
      {},
      {
        '/': [
          {
            text: '项目介绍',
            items: INTRODUCTIONS,
          },
          {
            text: '使用指南',
            items: GUIDES,
          },
          {
            text: '参考',
            items: REFERENCES,
          },
          {
            text: '关于',
            items: ABOUTS,
          },
        ],
      },
    ),

    editLink: {
      pattern: 'https://github.com/cosmoscatts/oj0/edit/main/docs/:path',
      text: '在 GitHub 上编辑此页面',
    },
    search: {
      provider: 'local',
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/cosmoscatts/oj0' },
    ],

    footer: {
      message: 'Released under the MIT License.',
      copyright: 'Copyright © 2023-PRESENT COSMOSCATTS.',
    },

    docFooter: {
      prev: '上一页',
      next: '下一页'
    },

    outline: {
      label: '页面导航'
    },

    lastUpdated: {
      text: '最后更新于',
      formatOptions: {
        dateStyle: 'short',
        timeStyle: 'medium'
      }
    },

    langMenuLabel: '多语言',
    returnToTopLabel: '回到顶部',
    sidebarMenuLabel: '菜单',
    darkModeSwitchLabel: '主题',
    lightModeSwitchTitle: '切换到浅色模式',
    darkModeSwitchTitle: '切换到深色模式',
  },

  head: [
    ['meta', { name: 'theme-color', content: '#ffffff' }],
    ['link', { rel: 'icon', href: '/oj/logo.svg' }],
    ['meta', { name: 'author', content: 'Cosmoscatts' }],
    ['meta', { property: 'og:title', content: 'OJ0' }],
    ['meta', { property: 'og:description', content: 'An Elegant Online Judge System' }],
    ['meta', { name: 'twitter:card', content: 'summary_large_image' }],
    ['meta', { name: 'viewport', content: 'width=device-width, initial-scale=1.0, viewport-fit=cover' }],
  ],
})
