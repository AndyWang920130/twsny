<template>
  <a-layout>
    <a-layout-header class="header">
      <div class="logo" />
      <a-menu
          v-model:selectedKeys="selectedKeys1"
          theme="dark"
          mode="horizontal"
          :style="{ lineHeight: '64px' }"
          :items="navMenuItems"
          @select="navMenuSelected"
      >
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-sider width="200" style="background: #fff">
        <a-menu
            v-model:selectedKeys="selectedKeys2"
            v-model:openKeys="openKeys"
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
            :items="subNavMenuItems"
            @select="navSubMenuSelected"
        >
        </a-menu>
      </a-layout-sider>
      <a-layout style="padding: 0 24px 24px">
        <a-breadcrumb
            style="margin: 16px 0"
        >
          <a-breadcrumb-item>{{breadcrumbArr[0]}}</a-breadcrumb-item>
          <a-breadcrumb-item>{{breadcrumbArr[1]}}</a-breadcrumb-item>
          <a-breadcrumb-item>{{breadcrumbArr[2]}}</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content
            :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
            id = "app">
<!--          <component :is="currentView" />-->
<!--          <Router :routerPath="currentUrl"></Router>-->
          <Router></Router>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {Ref, ref, h, reactive, computed} from 'vue';
import { UserOutlined, LaptopOutlined, NotificationOutlined, PieChartOutlined } from '@ant-design/icons-vue';
import Router from './Router.vue'
import NotFound from "./AntWebsitesTable.vue";
import HelloWord from "./HelloWorld.vue";
import Card from "./Card.vue";
import AntWebsitesTable from "./AntWebsitesTable.vue";

import {
  defaultNavMenuItems,
  defaultLifeSubNavMenuItems,
  defaultWorkSubNavMenuItems,
  defaultEntertainmentSubNavMenuItems,
  defaultNavKeyMap
} from "../utils/layout"
import {defaultPath} from "../utils/router"

const path = defaultPath
const navMenuItems = defaultNavMenuItems

const navKeyMap = defaultNavKeyMap
const selectedKeys1 = ref<string[]>(["life"]);
const breadcrumbArr : Ref<string[]> = ref([]);

const subNavMenuItems = ref(navKeyMap.get("life"))
let selectedKeys2 = ref<string[]>(['lifeSub1_children1']);
let openKeys = ref<string[]>(['lifeSub1']);

const initBreadcrumb = () => {
  breadcrumbArr.value.splice(0)
  const breadcrumb1 = selectedKeys1.value[0]
  const breadcrumb2 = openKeys.value[0]
  const breadcrumb3 = selectedKeys2.value[0]
  breadcrumbArr.value.push(breadcrumb1)
  breadcrumbArr.value.push(breadcrumb2)
  breadcrumbArr.value.push(breadcrumb3)
}
initBreadcrumb()

const navMenuSelected = (menu) => {
  console.log("menu item title: " + menu.item.title)
  console.log("menu key: " + menu.key)
  const navMenuKey = menu.key
  const subNavItems = navKeyMap.get(navMenuKey)
  subNavMenuItems.value = subNavItems
  selectedKeys2 = ref<string[]>([subNavMenuItems.value[0].children ? subNavMenuItems.value[0].children[0].key : subNavMenuItems.value[0].key])
  openKeys = ref<string[]>([subNavMenuItems.value[0].key])

  initBreadcrumb()
}

const currentUrl = ref<string>()
const navSubMenuSelected = (menu) => {
  currentUrl.value = menu.item.url
  if (currentUrl.value) window.location.href = "#" + menu.item.url

  initBreadcrumb()
}

</script>
<style scoped>
#components-layout-demo-top-side-2 .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}

.ant-row-rtl #components-layout-demo-top-side-2 .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: #fff;
}
</style>