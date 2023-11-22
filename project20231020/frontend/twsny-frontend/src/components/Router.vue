<script setup>
import { ref, computed } from 'vue'
import HelloWord from './HelloWorld.vue'
import Card from './Card.vue'
import AntWebsitesTable from './AntWebsitesTable.vue'
import NotFound from './AntWebsitesTable.vue'

const routes = {
  '/': HelloWord,
  '/Card': Card,
  '/AntWebsitesTable': AntWebsitesTable
}

const currentPath = ref(window.location.hash)

window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})

const currentView = computed(() => {
  return routes[currentPath.value.slice(1) || '/'] || NotFound
})
</script>

<template>
  <a href="#/">HelloWord</a> |
  <a href="#/Card">Card</a> |
  <a href="#/AntWebsitesTable">AntWebsitesTable</a>
  <component :is="currentView" />
</template>

<style lang="less" scoped>
//button, a {
//  display: inline;
//}
</style>