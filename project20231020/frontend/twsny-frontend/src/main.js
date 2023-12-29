// import './assets/main.css'
import 'ant-design-vue/dist/reset.css';

import {createApp, ref} from 'vue'
import { message } from 'ant-design-vue';
import router from './router'

import App from './app.vue'
import App1 from './App1.vue'
import App2 from './app2.vue'
import Persons from './Persons.vue'
import FetchData from './FetchData.vue'
import WebsitesTable from './WebsitesTable.vue'
import Layout from './components/Layout.vue'
import Card1 from './components/Card.vue'
import AntWebsitesTable from './components/life/website/WebsitesManagement.vue'
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



// let app;
const app = createApp(App)
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
//整个应用支持路由。
app.use(router)

/* 会自动注册 Button 下的子组件, 例如 Button.Group */
app.mount('#app')




app.config.globalProperties.$message = message;