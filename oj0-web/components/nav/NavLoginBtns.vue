<script setup lang="ts">
import { AUTH_ACTION_ENUM } from '~/constants'

const modalVisible = ref(false)
const type = ref<string>()

function register() {
  type.value = AUTH_ACTION_ENUM.REGISTER
  modalVisible.value = true
}

function login() {
  type.value = AUTH_ACTION_ENUM.LOGIN
  modalVisible.value = true
}

function changeActionType(newType: string) {
  type.value = newType
}

provide('changeActionType', changeActionType)
</script>

<template>
  <div h-full flex-center gap-3>
    <a cursor-pointer op-75 hover:op-100 @click="register">注册</a>
    <div>或</div>
    <a cursor-pointer op-75 hover:op-100 @click="login">登录</a>

    <AuthModal v-model="modalVisible" :type="type" />
  </div>
</template>
