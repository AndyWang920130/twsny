import {createRouter, createWebHashHistory, createWebHistory, RouteRecordRaw} from "vue-router";
// @ts-ignore
import HelloWord from './components/HelloWorld.vue'
// @ts-ignore
import AntWebsitesTable from './components/AntWebsitesTable.vue'
// @ts-ignore
import Zelda from './components/entertainment/game/zelda/Zelda.vue'
// @ts-ignore
import Cooking from './components/Cooking.vue'
// @ts-ignore
import Clothes from './components/life/home/clothes/Clothes.vue'
// @ts-ignore
import FileManagement from './components/work/FileManagement.vue'
import {ref} from "vue";

// @ts-ignore
// @ts-ignore
// @ts-ignore
// @ts-ignore
// @ts-ignore
// @ts-ignore
const routes : Array<RouteRecordRaw> =
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
        // {
        //     path: '/fileManagement',
        //     name: 'FileManagement',
        //     component: FileManagement
        // },
        {
          path: '/fileManagement/:folderId*',
          name: 'FileManagement',
          component: FileManagement
        }
    ]

const router = createRouter({
    history: createWebHashHistory(), // 路由模式：history模式
    routes: routes
})
export default router;
export const defaultPath = {
    root:"/",
    websites: "/websites",
    zelda: "/zelda",
    cooking: "/cooking",
    clothes: "/clothes",
    fileManagement: '/fileManagement'
}