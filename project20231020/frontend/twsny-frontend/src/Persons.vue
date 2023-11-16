<!--
该示例创建了一个可复用网格组件，并结合外部数据使用它。
-->

<script setup>
    import DemoGrid from './components/Grid.vue'
    import { ref, watchEffect } from 'vue'

    const searchQuery = ref('')
    const gridColumns = ['name', 'power']
    const gridData = [
        { name: 'Chuck Norris', power: Infinity },
        { name: 'Bruce Lee', power: 9000 },
        { name: 'Jackie Chan', power: 7000 },
        { name: 'Jet Li', power: 8000 }
    ]

    const API_URL = "http://211.149.248.84:8080/api/v1/persons"
    const result = ref(null)
    watchEffect(async () => {
        // 该 effect 会立即运行，
        // 并且在 currentBranch.value 改变时重新运行
        const url = `${API_URL}`
        result.value = await (await fetch(url)).json()
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
</template>