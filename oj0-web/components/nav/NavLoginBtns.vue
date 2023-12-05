<script setup lang="ts">
const modalVisible = ref(false)

function register() {
  modalVisible.value = true
}

function login() {
  modalVisible.value = true
}
</script>

<template>
  <div h-full flex-center gap-3>
    <a cursor-pointer op-75 hover:op-100 @click="register">注册</a>
    <div>或</div>
    <a cursor-pointer op-75 hover:op-100 @click="login">登录</a>

    <AuthModal v-model="modalVisible" />
  </div>
</template>
