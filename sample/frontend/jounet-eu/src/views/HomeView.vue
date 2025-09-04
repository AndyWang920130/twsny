<script setup lang="ts">
import TheWelcome from '../components/TheWelcome.vue'
import {Button} from 'ant-design-vue';

</script>

<template>
  <main>
    <Button v-action:create>has permission1</Button>
    <Button v-action:add>has permission2</Button>
    <TheWelcome />
  </main>
</template>
