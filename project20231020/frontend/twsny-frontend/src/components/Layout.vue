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
          <a-breadcrumb-item>Home</a-breadcrumb-item>
          <a-breadcrumb-item>List</a-breadcrumb-item>
          <a-breadcrumb-item>App</a-breadcrumb-item>
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
interface navMenuItem {
  key: string,
  label: string,
  title: string
}

let breadcrumbArr = ref([]);

const navMenuKey = {
  "life": "life",
  "work": "work",
  "entertainment": "entertainment"
}

const path = {
  root:"/",
  websites: "/websites",
  zelda: "/zelda",
  cooking: "/cooking",
  clothes: "/clothes",
}

const navKeyMap = new Map()


const selectedKeys1 = ref<string[]>(['work']);
const navMenuItems = ref<navMenuItem[]>([])
const initNavMenuItems = () => {
  const navMenuItem1 = {key : navMenuKey.life, label : "生活", title: "生活"}
  const navMenuItem2 = {key : navMenuKey.work, label : "工作", title: "工作"}
  const navMenuItem3 = {key : navMenuKey.entertainment, label : "娱乐", title: "娱乐"}
  navMenuItems.value.push(navMenuItem1)
  navMenuItems.value.push(navMenuItem2)
  navMenuItems.value.push(navMenuItem3)
}

initNavMenuItems()

const lifeSubNavMenuItems = reactive([
  {
    key: 'lifeSub3',
    icon: () => h(PieChartOutlined),
    label: '网站管理',
    title: '网站管理',
    url: path.websites
  },
  {
    key: 'lifeSub1',
    icon: () => h(PieChartOutlined),
    label: '居家',
    title: '居家',
    children: [
      {
        key: 'lifeSub1_children1',
        label: '美食',
        title: '美食',
        url: path.cooking
      },
      {
        key: 'lifeSub1_children2',
        label: '园艺',
        title: '园艺',
        url: path.root
      },
      {
        key: 'lifeSub1_children3',
        label: '衣橱',
        title: '衣橱',
        url: path.clothes
      },
    ]
  },
  {
    key: 'lifeSub2',
    icon: () => h(PieChartOutlined),
    label: '旅行',
    title: '旅行',
    children: [
      {
        key: 'lifeSub2_children1',
        label: '行程',
        title: '行程',
        url: path.root
      },
      {
        key: 'lifeSub2_children2',
        label: '酒店',
        title: '酒店',
        url: path.root
      }
     ]
  },
    ]
)

const workSubNavMenuItems = reactive([
      {
        key: 'workSub1',
        icon: () => h(PieChartOutlined),
        label: '编程',
        title: '编程',
        children: [
          {
            key: 'workSub1_children1',
            label: '后端',
            title: '后端',
            url: path.websites
          },
          {
            key: 'workSub1_children2',
            label: '前端',
            title: '前端',
            url: path.websites
          },
          {
            key: 'workSub1_children3',
            label: '部署',
            title: '部署',
            url: path.websites
          },
        ]
      },
      {
        key: 'workSub2',
        icon: () => h(PieChartOutlined),
        label: '专利',
        title: '专利',
        children: [
          {
            key: 'workSub2_children1',
            label: '检索',
            title: '检索',
            url: path.websites
          },
          {
            key: 'workSub2_children2',
            label: '编写',
            title: '编写',
            url: path.websites
          },
          {
            key: 'workSub2_children3',
            label: '申请',
            title: '申请',
            url: path.websites
          }
        ]
      },
    ]
)

const entertainmentSubNavMenuItems = reactive([
      {
        key: 'entertainmentSub1',
        icon: () => h(PieChartOutlined),
        label: '游戏',
        title: '游戏',
        children: [
          {
            key: 'entertainmentSub1_children1',
            label: '英雄联盟',
            title: '英雄联盟',
          },
          {
            key: 'entertainmentSub1_children2',
            label: '塞尔达',
            title: '塞尔达',
            url: path.zelda
          },
        ]
      },
      {
        key: 'entertainmentSub2',
        icon: () => h(PieChartOutlined),
        label: '其他',
        title: '其他',
        children: [
          {
            key: 'entertainmentSub2_children1',
            label: '抖音',
            title: '抖音',
          },
          {
            key: 'entertainmentSub2_children2',
            label: '网购',
            title: '网购',
          }
        ]
      },
    ]
)

navKeyMap.set(navMenuKey.life, lifeSubNavMenuItems)
navKeyMap.set(navMenuKey.work, workSubNavMenuItems)
navKeyMap.set(navMenuKey.entertainment, entertainmentSubNavMenuItems)

const subNavMenuItems = ref(workSubNavMenuItems)
let selectedKeys2 = ref<string[]>(['workSub1_children1']);
let openKeys = ref<string[]>(['workSub1']);

const initSubNavMenuItem = () => {
  subNavMenuItems.value = workSubNavMenuItems
}

initSubNavMenuItem()

const initBreadcrumb = () => {
  const breadcrumb1 = selectedKeys1
  const breadcrumb2 = openKeys
  const breadcrumb3 = selectedKeys2
  breadcrumbArr.value = [breadcrumb1, breadcrumb2, breadcrumb3]
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
}
// const componentMap = new Map()
//
// const initComponentMap = () => {
//   componentMap.set(path.websites, AntWebsitesTable)
//   componentMap.set(path.root, HelloWord)
// }

// initComponentMap()

const currentUrl = ref<string>()
const navSubMenuSelected = (menu) => {
  currentUrl.value = menu.item.url
  if (currentUrl.value) window.location.href = "#" + menu.item.url
}

// const currentPath = ref(window.location.hash)

// window.addEventListener('hashchange', () => {
//   currentPath.value = window.location.hash
// })
//
// const currentView = computed(() => {
//   return componentMap.get(currentPath.value.slice(1) || '/')
// })

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