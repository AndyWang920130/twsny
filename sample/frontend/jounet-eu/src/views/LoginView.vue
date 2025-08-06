<script setup lang="ts">
import Login from '@/components/login/Login.vue'; 
import { Image } from 'ant-design-vue';
</script>
<template>
    <div style="display: grid; grid-template-columns: 3fr 2fr; padding: 0 2rem;">
      <div>
        <!-- <Image src="https://www.antdv.com/#error" fallback="/images/fallback.png" /> -->
      </div>
      <div><Login/></div>
    </div>
</template>

<style scoped>
</style>
