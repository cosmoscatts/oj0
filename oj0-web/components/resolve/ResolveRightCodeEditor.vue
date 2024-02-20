<script setup lang="ts">
import { RESOLVE_LANGUAGE_ENUM } from '~/constants'

const { submitId } = defineProps<{ submitId?: string }>()

const selectedLanguage = ref(RESOLVE_LANGUAGE_ENUM.JAVA)

const DEFAULT_CODE = `public class Main {
    public static void main(String[] args) {
        
    }
}
`

const code = ref(DEFAULT_CODE)

async function fetchSubmitCode() {
  if (!submitId)
    return
  const { data } = await QuestionSubmitApi.getById({ id: submitId })
  code.value = data.code || ''
}
onMounted(fetchSubmitCode)

function getCode() {
  return code.value
}

function getLanguage() {
  return selectedLanguage.value
}

const options = computed(() => {
  return questionResolveLanguageOptions.map((i) => {
    if (i.value === RESOLVE_LANGUAGE_ENUM.JAVA)
      return i
    return {
      ...i,
      disabled: true,
    }
  })
})

const submitCode = computed(() => {
  if (submitId)
    return code.value
  return undefined
})

function reset() {
  useConfirm({
    title: '还原确认',
    content: '确定要将代码还原到默认状态吗？',
    ok: () => {
      code.value = DEFAULT_CODE
      Message.success('还原成功')
    },
  })
}

const { copy: _copy, copied } = useClipboard()

function copy() {
  _copy((code.value || '').trim())
  Message.success('复制成功')
}

defineExpose({
  getCode,
  getLanguage,
})
</script>

<template>
  <div h-full w-full of-hidden flex="~ col">
    <div flex-y-center justify-between gap-3 border-b border-base px-3 py-2>
      <button flex="~ gap-1 center" mt-1.5px ws-nowrap px-2 py-1>
        <span i-carbon-code text-green /> 代码
      </button>

      <div flex-y-center justify-end gap-3>
        <a-select v-model="selectedLanguage" :options="options" size="mini" w-100px rounded />

        <CommonTooltip content="还原到默认" flex items-center>
          <button i-carbon-reset op50 hover:op100 @click="reset" />
        </CommonTooltip>

        <CommonTooltip content="复制代码" flex items-center>
          <button title="Copy" op50 transition hover:op100 :disabled="copied" :class="copied ? 'i-carbon-checkmark text-green' : 'i-carbon-copy'" @click="copy" />
        </CommonTooltip>
      </div>
    </div>

    <div flex="~ 1 col" of-auto>
      <EditorCode v-model="code" :language="selectedLanguage" :submit-code="submitCode" />
    </div>
  </div>
</template>
