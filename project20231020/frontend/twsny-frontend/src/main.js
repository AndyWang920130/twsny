// import './assets/main.css'
import 'ant-design-vue/dist/reset.css';

import { createApp } from 'vue'
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
import FileUploadTest from './components/FileUploadTest.vue'



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
app = createApp(App1)

/* 会自动注册 Button 下的子组件, 例如 Button.Group */
app.mount('#app')

app.config.globalProperties.$message = message;