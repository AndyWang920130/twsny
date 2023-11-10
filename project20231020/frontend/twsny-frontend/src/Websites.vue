<!--
该示例创建了一个可复用网格组件，并结合外部数据使用它。
-->

<script setup>
import DemoGrid from './components/Grid.vue'
import {getWebsites} from "./service/website"
import { ref, watchEffect } from 'vue'

const searchQuery = ref('')
const gridColumns = ['url', 'type', 'description']
const gridData = [
  { name: 'Chuck Norris', power: Infinity },
  { name: 'Bruce Lee', power: 9000 },
  { name: 'Jackie Chan', power: 7000 },
  { name: 'Jet Li', power: 8000 }
]

const gridData1 = getWebsites();
</script>

<template>
  <form id="search">
    Search <input name="query" v-model="searchQuery">
  </form>
  <DemoGrid
      :data="gridData1"
      :columns="gridColumns"
      :filter-key="searchQuery">
  </DemoGrid>
</template>