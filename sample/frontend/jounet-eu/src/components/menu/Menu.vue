<script lang="ts" setup>
    import { ref, watch } from 'vue';
    import { Layout, LayoutHeader, LayoutContent, LayoutFooter, Breadcrumb, BreadcrumbItem, Menu, MenuItem, Avatar, Button} from 'ant-design-vue';
    import type { Key } from 'ant-design-vue/es/_util/type';
    import router from '@/router';
    import { useRoute } from 'vue-router';
    import { getWithExpiry } from '@/apis/utils/localStorager';
    import { LoginConfig } from '@/types/CommonD';
    import MenuUserInfo from './MenuUserInfo.vue';
    import type { UserInfoT } from '@/apis/UserD';

    const route = useRoute()
    const selectedKeys = ref<string[]>([route.path]);
    // 接收对象并解构
    const handleClick = ({ key, keyPath }: { key: Key,  keyPath? : Key[] }) => {
        router.push(String(key))
    }

    // 接收对象并解构 这样的格式同样支持
    // const handleClick1 = ({ key, keyPath }: MenuInfo) => {
    //     console.log(key);
    // }

    watch(route, () => {
      selectedKeys.value = [route.path]
    })

    const  loginToken = getWithExpiry(LoginConfig.localStorageKey)

    const userInfo = ref<UserInfoT>({
      "name" : loginToken,
      "nickName" : loginToken,
      "avatar" : "/images/userAvatar.jpg"
    })
</script>

<template>
  <Layout style="height: 100vh; display: flex; flex-direction: column;">
    <LayoutHeader style="height: 64px; line-height: 64px; display: flex; align-items: center; justify-content: space-between;">
      <div style="display: flex; align-items: center;">
          <div class="logo" />
          <Menu
            v-model:selectedKeys="selectedKeys"
            theme="dark"
            mode="horizontal"
            :style="{ lineHeight: '64px' }"
            @click="handleClick"
          >
            <MenuItem key="/home">HOME</MenuItem>
            <MenuItem key="/about">ABOUT</MenuItem>
            <!-- <MenuItem key="/login">LOGIN</MenuItem> -->
          </Menu>
      </div>

      <div class="user-info">
        <MenuUserInfo v-bind="userInfo"></MenuUserInfo>
      </div>
    </LayoutHeader>
    <LayoutContent style="flex: 1; overflow: auto; padding: 0 50px;">
      <Breadcrumb style="margin: 16px 0">
        <BreadcrumbItem>Home</BreadcrumbItem>
        <BreadcrumbItem>List</BreadcrumbItem>
        <BreadcrumbItem>App</BreadcrumbItem>
      </Breadcrumb>
      
      <div :style="{ background: '#fff', padding: '24px', minHeight: '360px'  }">
        <slot></slot>
      </div>
    </LayoutContent>
    <LayoutFooter style="text-align: center">
      Twsny Design ©2025 Created by Andy UED
    </LayoutFooter>
  </Layout>
</template>

<style scoped>
.site-layout-content {
  min-height: 280px;
  padding: 24px;
  background: #fff;
}
.logo {
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
  background-image: url("/images/logo.jpg");
  background-size: cover; /*cover 会放大图片直到背景区域被完全覆盖,图片可能会被部分裁剪（左右或上下）以保持宽高比例; contain 按比例缩放背景图，使图片完整显示在背景区域内，可能会留空白; */
  background-repeat: no-repeat; /* 防止平铺 */
  background-position: center; /* 居中对齐 */
}

.user-info {
  display: flex; 
  align-items: center; 
  gap: 12px;
}
/* .avatar {
  float: right;
  width: 31px;
  height: 31px;
  margin: 16px 0 16px 24px;
  border-radius: 100%;
  background: rgba(255, 255, 255, 0.3);
} */

[data-theme='dark'] .site-layout-content {
  background: #141414;
}

</style>
