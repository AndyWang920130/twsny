import './assets/main.css'
import 'ant-design-vue/dist/reset.css';

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import customDirectiveAction  from '@/directives/action'
import customStore from "@/store"

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(customDirectiveAction)
app.use(customStore)
app.mount('#app')
