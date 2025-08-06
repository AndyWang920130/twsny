<script setup lang="ts">
import { RouterLink, RouterView, useRoute } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import Menu from './components/menu/Menu.vue'
import { computed } from 'vue'

const route = useRoute()
const isLoginPage = computed(() => route.path === '/login')
</script>

<template>
    <Menu>
        <RouterView />
    </Menu>  
</template>

<style scoped>
</style>
