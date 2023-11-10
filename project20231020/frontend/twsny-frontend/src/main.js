import './assets/main.css'

import { createApp } from 'vue'

import App from './App.vue'
import App1 from './App1.vue'
import App2 from './App2.vue'
import Persons from './Persons.vue'
import FetchData from './FetchData.vue'
import Websites from './Websites.vue'


// createApp(App).mount('#app')
// createApp(App1).mount('#app')
// createApp(App2).mount('#app')
// createApp(Persons).mount('#app')
// createApp(FetchData).mount('#app')
createApp(Websites).mount('#app')