// import './assets/main.css'
import 'ant-design-vue/dist/reset.css';

import { createApp } from 'vue'
import { message } from 'ant-design-vue';

import App from './App.vue'
import App1 from './App1.vue'
import App2 from './App2.vue'
import Persons from './Persons.vue'
import FetchData from './FetchData.vue'
import WebsitesTable from './WebsitesTable.vue'
import Layout from './components/Layout.vue'
import Card1 from './components/Card.vue'
import AntWebsitesTable from './components/AntWebsitesTable.vue'

let app;
// createApp(App).mount('#app')
// createApp(App1).mount('#app')
// createApp(App2).mount('#app')
// createApp(Persons).mount('#app')
// createApp(FetchData).mount('#app')
// app = createApp(WebsitesTable)
// app = createApp(Layout)
// app = createApp(Card1)
app = createApp(AntWebsitesTable)

/* 会自动注册 Button 下的子组件, 例如 Button.Group */
app.mount('#app')

app.config.globalProperties.$message = message;