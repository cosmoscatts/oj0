---
outline: deep
---

# 项目简介

<br>

<span text-xl><b><span text-brand>OJ</span><span text-brand-red>0</span></b></span> 
是一个风格简约的在线判题评测系统，虽然项目不大，但麻雀虽小，五脏俱全，该有的功能都有。它的命名是由 <ruby text-brand-yellow>Online<rt>O</rt></ruby><ruby text-brand-red>Judge<rt>J</rt></ruby>
缩写而成，而 `0` 则是我的个人习惯。  

简单介绍下 `OJ` 系统的一般功能，首先用户会在题目中选择一个想做的题目，跳转至做题页面完成答题后，提交代码等待做题结果；
系统会运行用户提交的代码，得到运行结果，再和标准结果进行比对，从而判断用户提交的代码正确与否。

从此可见，整个 `OJ` 系统的难点其实在于如何实现判题功能；而本项目中，实现了 `代码沙箱` 来获取用户提交代码的运行结果，将判题功能与主业务解耦开来，代码执行又与判题功能解耦开来；代码沙箱只负责执行代码，而判题模块只负责将代码交给沙箱执行，并根据运行结果返回题目提交信息。

## 关键页面展示

[项目地址 https://oj.hilyc.cn](https://oj.hilyc.cn)

![首页](/public/pages/page1.png)

![用户管理](/public/pages/page2.png)

![题目管理](/public/pages/page3.png)

![题库](/public/pages/page4.png)

![历史提交](/public/pages/page5.png)

![个人中心](/public/pages/page6.png)

![做题统计](/public/pages/page7.png)

## 基本概念

### 什么是 AC ？

`AC` 代表的是题目运行通过，答案正确。

### 几种测评模式

- 普通测评

由管理员设置题目的输入、输出用例，例如：输入 1，你要输出 2，结果才是正确的；这种模式主要是对比用例文件，将用户的代码和输入用例交给判题机执行，得到执行的输出结果，再和设定好的输出用例作对比。

- 特殊测评（SPJ）

由管理员设置题目的输入、输出用例，例如：我输入 1，用户的答案只要是 > 0 或 < 2 都是正确的；不是通过对比用例文件是否一致这种死板的程序来检验，而是要专门根据这道题目写一个特殊的判断程序，程序接收题目的输入、标准输出用例、用户的结果，特判程序根据这些值来比较是否正确。

- 交互测评

交互测评：让用户输入一个例子，就给一个输出结果，交互比较灵活，没办法通过简单的、死板的输入输出文件来搞定。

## 现有的一些 OJ 系统

- [https://github.com/HimitZH/HOJ](https://github.com/HimitZH/HOJ)
- [https://github.com/QingdaoU/OnlineJudge](https://github.com/QingdaoU/OnlineJudge)
- [https://github.com/hzxie/voj](https://github.com/hzxie/voj)
- [https://github.com/vfleaking/uoj](https://github.com/vfleaking/uoj)
- [https://github.com/zhblue/hustoj](https://github.com/zhblue/hustoj)
- [https://github.com/hydro-dev/Hydro](https://github.com/hydro-dev/Hydro)

## 注意点

- 安全性

不能让用户随便引入包、随便遍历、暴力破解，需要使用正确的算法。

- 异步化

::: tip
试想一下，如果用户提交答案后，需要一直等待判题结果而干不了任何事，这样的体验是非常糟糕的。
:::

判题过程需要是异步的。

- 时间、内存限制

用户提交的代码会在我们的服务器上运行，因此，需要限制代码的运行时间和消耗内存。


