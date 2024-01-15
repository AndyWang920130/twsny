<template>
  <a-layout>
    <a-layout-header class="header">
      <div class="logo"/>
      <a-menu
          v-model:selectedKeys="navSelectedKeys"
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
            v-model:selectedKeys="subNavSelectedKeys"
            v-model:openKeys="openKeys"
            mode="inline"
            :style="{ height: '100%', borderRight: 0 }"
            :items="subNavMenuItems"
            @select="navSubMenuSelected"
            @openChange="subNavMenuOpenChanged"
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
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script lang="ts" setup>
import {Ref, ref, h, reactive, computed} from 'vue';
import { UserOutlined, LaptopOutlined, NotificationOutlined, PieChartOutlined } from '@ant-design/icons-vue';
import Router from './Router.vue'
import NotFound from "./life/website/WebsitesManagement.vue";
import HelloWord from "./HelloWorld.vue";
import Card from "./Card.vue";
import AntWebsitesTable from "./life/website/WebsitesManagement.vue";

import {
  defaultNavMenuItems,
  getSubNavMenuItems
} from "../utils/layout"
import {useRouter} from "vue-router";
const router = useRouter()


// const path = defaultPath
const navMenuItems = ref([]);
// const navKeyMap = defaultNavKeyMap

const navSelected = ref();
const navSelectedKeys = ref<string[]>([]);
const breadcrumbArr : Ref<string[]> = ref([]);

const subNavMenuItems = ref([]);
const subNavOpened = ref()
const openKeys = ref([]);
const subNavSelected = ref()
const subNavSelectedKeys = ref<string[]>([]);

// const navSelected = ref();
// const navSelectedKeys = ref<string[]>([]);
//
// const breadcrumbArr : Ref<string[]> = ref([]);
//
// const subNavMenuItems = ref([])
// const subNavOpened  = ref([])
// const openKeys = ref([]);
// const subNavSelected = ref()
// const subNavSelectedKeys = ref<string[]>([]);
const initSelectedData = (selectedKeys : String) => {
  navSelectedKeys.value.push(selectedKeys)
  subNavMenuItems.value.splice(0)
  subNavMenuItems.value.push(...getSubNavMenuItems(navSelectedKeys.value[0]).value)
  subNavOpened.value = subNavMenuItems.value[0]
  openKeys.value.push(subNavOpened.value.key)
  subNavSelected.value = subNavOpened.value.children[0]
  subNavSelectedKeys.value.push(subNavSelected.value.key)
}

const initData = () => {
  navMenuItems.value.push(...defaultNavMenuItems.value)
  navSelected.value = defaultNavMenuItems.value[0]
  initSelectedData(navSelected.value.key)
}

const initBreadcrumb = () => {
  breadcrumbArr.value.splice(0)
  const breadcrumb1 = navSelected.value.label
  const breadcrumb2 = subNavOpened.value.label
  const breadcrumb3 = subNavSelected.value.label

  breadcrumbArr.value.push(breadcrumb1)
  breadcrumbArr.value.push(breadcrumb2)
  breadcrumbArr.value.push(breadcrumb3)
}


const init = () => {
  initData()
  initBreadcrumb()
  router.push(subNavSelected.value.url)
}

init()

const navMenuSelected = (menu) => {
  navSelected.value = defaultNavMenuItems.value.filter(item => menu.key === item.key)[0]
  initSelectedData(navSelected.value.key)
  // subNavMenuItems.value = reactive(navKeyMap.get(navSelected.value.key))
  // subNavOpened.value = subNavMenuItems.value[0]
  // subNavSelected.value = subNavOpened.value.children[0]

  // subNavSelectedKeys.value = subNavMenuItems.value[0].children ? subNavMenuItems.value[0].children[0].key : subNavMenuItems.value[0].key
  // initBreadcrumb()
}

const subNavMenuOpenChanged = (openKeys, openKey) => {
  console.log("openKeys changed: " + openKeys)
}

const navSubMenuSelected = (menu) => {
  // navSelected.value = navMenuItems.value.filter(item => menu.key === item.key)[0]
  // const menuItem = subNavMenuItems.value.filter(item => item.key === menu.key)
  // if (menuItem) {
  //   subNavOpened.value = menuItem[0]
  //   return;
  // }

  // const subNavChildrenItems = ref(subNavOpened.value.children);
  // subNavSelected.value  = subNavChildrenItems.value.filter(item => menu.key === item.key)[0]
  // subNavOpened.value = subNavMenuItems.value[0]
  // const subNavMenuChildrenItems = ref (subNavMenuItems.value)

  const subNavChildrenItems = ref([])
  subNavMenuItems.value.map(item => {
    subNavChildrenItems.value = item.children
    const subNavChildrenItemArray = subNavChildrenItems.value.filter(childrenItem => childrenItem.key === menu.key)
    if (subNavChildrenItemArray.length > 0) {
      subNavOpened.value = item
      subNavSelected.value = subNavChildrenItemArray[0]
    }
  })
  // if (currentUrl.value) window.location.href = "#" + menu.item.url
  initBreadcrumb()
  router.push(subNavSelected.value.url)
}

// const toPage = () => {
//   router.push('/zelda')
// }
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