<!--
该示例创建了一个可复用网格组件，并结合外部数据使用它。
-->

<!--<script setup>-->
<!--import DemoGrid from './components/Grid.vue'-->
<!--import { getWebsites } from "./service/website"-->
<!--import { ref, watchEffect } from 'vue'-->

<!--const searchQuery = ref('')-->
<!--const gridColumns = ['url', 'directory', 'description']-->
<!--const gridData = ref(null)-->
<!--getWebsites().then(response => {-->
<!--  gridData.value = response.data.content-->
<!--})-->

<!--</script>-->

<script setup>
import DemoGrid from './components/Grid.vue'
import { getWebsites } from "./service/website"
import { ref, watchEffect } from 'vue'

const searchQuery = ref('')
const gridColumns = ['url', 'directory', 'description']
const gridData =  ref('')
getWebsites().then(response => {
  gridData.value = response.data.content
})
</script>

<template>
  <form id="search">
    Search <input name="query" v-model="searchQuery">
  </form>
  <DemoGrid
      :data="gridData"
      :columns="gridColumns"
      :filter-key="searchQuery">
  </DemoGrid>

<!--  <div class="buttons">-->
<!--    <button @click="create">Create</button>-->
<!--    <button @click="update">Update</button>-->
<!--    <button @click="del">Delete</button>-->
<!--  </div>-->
</template>

<style>
button + button {
  margin-left: 5px;
}
</style>