---
outline: deep
---

# 主流实现方案

<br>

> 在实际开发过程中，我们应该避免重复造轮子，能用现成的，就不要自己去重新写。

## 用现成的 `OJ` 系统

网上有很多开源的 `OJ` 项目，比如 `青岛 OJ`、`HustOJ` 等，可以直接下载开源代码自己部署。

比较推荐的是 [judge0](https://github.com/judge0/judge0)，这是一个非常成熟的商业 `OJ` 项目，支持 60 多种编程语言！

## 用现成的服务

如果你不希望完整部署一套大而全的代码，只是想复用他人已经实现的、最复杂的判题逻辑，那么可以直接使用现成的别人实现好的服务。

比如 `judge0` 提供的判题 `API`，非常方便易用。只需要通过 `HTTP` 调用 `submissions` 判题接口，把用户的代码、输入值、预期的执行结果作为请求参数发送给 `judge0` 的服务器，它就能自动帮你编译执行程序，并且返回程序的运行结果。

如下图，发送了一段打印 `hello world` 的程序，得到了程序执行的时间、状态等：

![hello world](/public/intros/implements/img1.png)

:::info
**Judge0 API:** [https://rapidapi.com/judge0-official/api/judge0-ce](https://rapidapi.com/judge0-official/api/judge0-ce)
<br>
**官方文档：** [https://ce.judge0.com/#submissions-submission-post](https://ce.judge0.com/#submissions-submission-post)
:::

:::tip `API` 的作用
接受代码并返回执行结果
:::

**使用步骤**

- 1.注册并开通订阅
- 2.测试 `language` 接口
- 3.测试执行代码接口 `submissions`

**示例接口参数及返回结果**

::: code-group

```json [params.json]
{
  "source_code": "#include <stdio.h>\n\nint main(void) {\n  char name[10];\n  scanf(\"%s\", name);\n  printf(\"hello, %s\n\", name);\n  return 0;\n}",
  "language_id": "76",
  "stdin": "Judge0",
  "expected_output": "hello, Judge0"
}
```

```json [result.json]
{
  "source_code": "includestdiohintmainvoidcharname10scanfsnameprintfhellosname\nreturn0=\n",
  "language_id": "76",
  "stdin": "Judgew==\n",
  "expected_output": "helloJudge0=\n",
  "stdout": null,
  "status_id": 6,
  "created_at": "2023-12-27T13:50:30.433Z",
  "finished_at": "2023-12-27T13:50:31.022Z",
  "time": null,
  "memory": null,
  "stderr": null,
  "token": "8be000ad-2edb-4262-b367-7095a694e028",
  // ...
}
```
:::

## 自主开发

判题服务和代码沙箱都要自己实现，适合学习，但不适用于商业项目；本项目采用的是自主开发判题服务和代码沙箱。

## 把 `AI` 来当做代码沙箱

现在 `AI` 的能力已经十分强大了，我们可以把各种本来很复杂的功能直接交给 `AI` 来实现。比如把 `AI` 当做代码沙箱，我们直接扔给他一段代码、输入参数，问他能否得到预期的结果，就实现了在线判题逻辑！

## 移花接木

可以通过程序来操作模拟浏览器的方式，用别人已经开发好的 `OJ` 系统来帮助我们判题。

比如使用 `Puppeteer` 和 `无头浏览器`，把我们系统用户提交的代码，像人一样输入到别人的 `OJ` 网页中，让程序点击提交按钮，并且等别人的 `OJ` 系统返回判题结果后，再把这个结果返回给我们自己的用户。

:::warning 缺点
这种方式的最大的问题就是把核心流程交给了别人，如果别人服务挂了，你的服务也就挂了；而且别人 `OJ` 系统不支持的题目，可能你也支持不了。
:::
