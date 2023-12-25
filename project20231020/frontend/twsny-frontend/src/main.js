// import './assets/main.css'
import 'ant-design-vue/dist/reset.css';

import {createApp, ref} from 'vue'
import { message } from 'ant-design-vue';

import App from './app.vue'
import App1 from './App1.vue'
import App2 from './app2.vue'
import Persons from './Persons.vue'
import FetchData from './FetchData.vue'
import WebsitesTable from './WebsitesTable.vue'
import Layout from './components/Layout.vue'
import Card1 from './components/Card.vue'
import AntWebsitesTable from './components/AntWebsitesTable.vue'
import Router from './components/Router.vue'
import Add from './components/Add.vue'
import Image from './components/common/FileUpload.vue'
import Modal from './components/common/Modal.vue'
import FileUploadTest from './components/FileUploadTest.vue'
import HelloWord from "./components/HelloWorld.vue";
import Zelda from "./components/entertainment/game/zelda/Zelda.vue";
import Cooking from "./components/Cooking.vue";
import Clothes from "./components/life/home/clothes/Clothes.vue";
import FileManagement from "./components/work/FileManagement.vue";



let app;
app = createApp(App)
// createApp(App2).mount('#app')
// createApp(Persons).mount('#app')
// createApp(FetchData).mount('#app')
// app = createApp(WebsitesTable)
// app = createApp(Layout)
// app = createApp(Card1)
// app = createApp(AntWebsitesTable)
// app = createApp(Router)
// app = createApp(Add)
// app = createApp(Image)
// app = createApp(FileUploadTest)
// app = createApp(App1)
// app = createApp(Modal)

// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
// const routes =
//     [
//         {
//             path: '/',
//             name: 'HelloWord',
//             component: HelloWord
//         },
//         {
//             path: '/websites',
//             name: 'AntWebsitesTable',
//             component: AntWebsitesTable
//         },
//         {
//             path: '/zelda',
//             name: 'Zelda',
//             component: Zelda
//         },
//         {
//             path: '/cooking',
//             name: 'Cooking',
//             component: Cooking
//         },
//         {
//             path: '/clothes',
//             name: 'Clothes',
//             component: Clothes
//         },
//         {
//             path: '/fileManagement',
//             name: 'FileManagement',
//             component: FileManagement
//         },
//     ]


// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
// const router = VueRouter.createRouter({
//     // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
//     history: VueRouter.createWebHashHistory(),
//     routes, // `routes: routes` 的缩写
// })

/* 会自动注册 Button 下的子组件, 例如 Button.Group */
app.mount('#app')

//整个应用支持路由。
// app.use(router)


app.config.globalProperties.$message = message;