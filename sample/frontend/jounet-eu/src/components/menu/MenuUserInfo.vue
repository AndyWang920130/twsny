<script setup lang="ts">
import type { UserInfoT } from '@/apis/UserD';
import { Dropdown, Button, Menu, MenuItem, Space, Avatar} from 'ant-design-vue';
import { getWithExpiry, isLoggedIn, loginToken, remove, updateLoginStatus } from '@/apis/utils/localStorager';
import { LoginConfig } from '@/types/CommonD';
import { ref } from 'vue';
import router from '@/router';
// const props = withDefaults(defineProps<UserInfo>(), {
//     "name": "default"
// })

const props = defineProps<UserInfoT>()
// const isLoggedIn = ref(!!getWithExpiry(LoginConfig.localStorageKey))

const loginOut = () => {
    console.log("loginOut")
    remove(LoginConfig.localStorageKey)
    updateLoginStatus()
    router.push("/login")
}

const loginButtonClick = () => {
    router.push("/login")
}

const profileButtonClick = () => {
    router.push("/user/profile")
}

const passwordChangeButtonClick = () => {
    router.push("/user/passwordChange")
}

const userSettingButtonClick = () => {
    router.push("/user/userSetting")
}
</script>
<template>
    <div class="user-info">
    <template v-if="isLoggedIn">
        <Avatar :src="props.avatar" />
        <Dropdown placement="bottom">
            <span style="color: white;">{{ loginToken }}</span>
            <template #overlay>
            <Menu>
                <MenuItem>
                <a href="javascript:void(0)" @click="profileButtonClick">
                    Profile
                </a>
                </MenuItem>
                <MenuItem>
                <a href="javascript:void(0)" @click="passwordChangeButtonClick">
                    Change Pwd
                </a>
                </MenuItem>
                <MenuItem>
                <a href="javascript:void(0)" @click="userSettingButtonClick">
                    Settings
                </a>
                </MenuItem>
                <MenuItem>
                <a href="javascript:void(0)" @click="loginOut">
                     Sign Out
                </a>
                </MenuItem>
            </Menu>
            </template>
      </Dropdown>
    </template>
    
    <template v-else>
        <Button type="primary" @click="loginButtonClick">Login</Button>
    </template>
    </div>
</template>
<style scoped>

.user-info {
  gap: 12px;
}
</style>
