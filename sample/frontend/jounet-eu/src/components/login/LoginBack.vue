<script setup lang="ts">
import { setWithExpiry, updateLoginStatus } from '@/apis/utils/localStorager';
import { LoginConfig } from '@/types/CommonD';
import router from '@/router';
console.log("111111")
const urlParams = new URLSearchParams(window.location.search);
const token = urlParams.get('token');
console.log("token: " + token)
if (token) {
    setWithExpiry(LoginConfig.localStorageKey, token,  60 * 60 * 1000)
    updateLoginStatus()
    router.push('/home')
}
</script>
<template>
    login back page
</template>
<style scoped></style>
