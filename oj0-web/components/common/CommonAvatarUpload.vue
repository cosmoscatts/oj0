<script setup lang="ts">
import type { FileItem } from '@arco-design/web-vue/es/upload/interfaces'

const visible = defineModel<boolean>('visible')
const avatar = defineModel<string>('avatar')

async function onChange(_: FileItem[], fileItem: FileItem) {
  avatar.value = ''
  const loading = Message.loading('上传中···')
  const { code, data, message } = await UploadApi.upload({ file: fileItem.file })
  loading.close()
  if (code !== 0) {
    Message.error(message || '上传失败')
    return
  }
  avatar.value = data
  Message.success('上传成功')
}

/**
 * 检查上传的是否为图片
 */
function checkImageBeforeUpload(file: File): Promise<boolean | File> {
  return new Promise((resolve) => {
    if (!file.type.startsWith('image')) {
      Message.error('请上传图片')
      return resolve(false)
    }
    resolve(true)
  })
}
</script>

<template>
  <div>
    <a-avatar :size="100" trigger-type="mask">
      <template #trigger-icon>
        <a-upload
          :auto-upload="false"
          list-type="picture-card"
          :show-file-list="false"
          @change="onChange"
          @before-upload="checkImageBeforeUpload"
        >
          <template #upload-button>
            <div btn-action-icon hover="bg-$c-bg-selection">
              <div i-ri-camera-2-line />
            </div>
          </template>
        </a-upload>
        <div v-if="avatar" btn-action-icon hover="bg-$c-bg-selection" @click="visible = true">
          <div i-ri-eye-2-line />
        </div>
      </template>
      <img :src="avatar" h-full w-full>
    </a-avatar>
    <div v-if="avatar">
      <a-image-preview v-model:visible="visible" :src="avatar" />
    </div>
  </div>
</template>
