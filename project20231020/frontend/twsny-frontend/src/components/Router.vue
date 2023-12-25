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

const routes = ref(
    [
      {
        path: '/',
        name: 'HelloWord',
        component: HelloWord
      },
      {
        path: '/websites',
        name: 'AntWebsitesTable',
        component: AntWebsitesTable
      },
      {
        path: '/zelda',
        name: 'Zelda',
        component: Zelda
      },
      {
        path: '/cooking',
        name: 'Cooking',
        component: Cooking
      },
      {
        path: '/clothes',
        name: 'Clothes',
        component: Clothes
      },
      {
        path: '/fileManagement',
        name: 'FileManagement',
        component: FileManagement
      },
      // {
      //   path: '/fileManagement/:folderId',
      //   name: 'FileManagement',
      //   component: FileManagement
      // }
    ]
)

// const routes = {
//   '/': HelloWord,
//   '/websites': AntWebsitesTable,
//   '/zelda': Zelda,
//   '/cooking': Cooking,
//   '/clothes': Clothes,
//   '/fileManagement/:folderId': FileManagement
// }

// window.location.href = props
const currentPath = ref(window.location.hash)

window.addEventListener('hashchange', () => {
  currentPath.value = window.location.hash
})

const currentView = computed(() => {
  const path = currentPath.value.slice(1) || '/'
  return routes.value.filter(route => path === route.path)[0].component
  // return routes[currentPath.value.slice(1) || '/'] || NotFound
})
</script>

<template>
  <component :is="currentView" />
<!--  <RouterView :slot="currentView">-->
<!--    <component-->
<!--        :is="currentView"-->
<!--        view-prop="folderId"-->
<!--    />-->
<!--  </RouterView>-->
</template>

<style lang="less" scoped>
</style>