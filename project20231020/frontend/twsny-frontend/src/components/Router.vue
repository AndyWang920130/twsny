<script setup>
import { ref, computed } from 'vue'
import HelloWord from './HelloWorld.vue'
import Card from './Card.vue'
import AntWebsitesTable from './AntWebsitesTable.vue'
import Zelda from './entertainment/game/zelda/Zelda.vue'
import Cooking from './Cooking.vue'
import Clothes from './life/home/clothes/Clothes.vue'
import NotFound from './AntWebsitesTable.vue'
import FileManagement from './work/FileManagement.vue'

// const props = defineProps(['routerPath'])
// console.log("routerPath: " + props.routerPath)

// const routes = {
//   '/': HelloWord,
//   '/Card': Card,
//   '/AntWebsitesTable': AntWebsitesTable
// }

const routes = {
  '/': HelloWord,
  '/websites': AntWebsitesTable,
  '/zelda': Zelda,
  '/cooking': Cooking,
  '/clothes': Clothes,
  '/fileManagement': FileManagement
}

// window.location.href = props
const currentPath = ref(window.location.hash)

window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})

const currentView = computed(() => {
  return routes[currentPath.value.slice(1) || '/'] || NotFound
})
</script>

<template>
  <component :is="currentView" />
</template>

<style lang="less" scoped>
</style>