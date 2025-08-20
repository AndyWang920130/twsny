<script setup lang="ts">
import router from '@/router'
import { ref } from 'vue'
import { Dropdown, Button, Menu, MenuItem, Alert } from 'ant-design-vue';
import { setWithExpiry, updateLoginStatus } from '@/apis/localStorager'
import { LoginConfig } from '@/types/CommonD'
import { authenticate, loginWithKeycloak, loginWithTwsnyOdc } from '@/apis/login';
import type { LoginFormT } from '@/apis/UserD';

  const username = ref('')
  const password = ref('')
  const errorMessage = ref('')

  const handleLogin = () => {
    errorMessage.value = ''
    if (!username.value || !password.value) {
      errorMessage.value = '请输入用户名和密码'
      return
    }

    const loginVM = ref<LoginFormT>({
      "username" : username.value,
      "password" : password.value
    })


    if (authenticate(loginVM.value)) {
        setWithExpiry(LoginConfig.localStorageKey, loginVM.value.username, 60 * 60 * 1000)
        updateLoginStatus()
        // 可以跳转到主页，如：
        router.push('/home')
    } else {
        errorMessage.value = '用户名或密码错误'
    }
  }


  const thirdLogin_keyCloak = () => {
    console.log('thirdLogin_keyCloak')
    loginWithKeycloak()
  }

    const twsny_odc = () => {
    console.log('twsny_odc')
    loginWithTwsnyOdc()
  }

</script>
<template>
  <div class="login-container">
    <h2>登录</h2>
    <form @submit.prevent="handleLogin">
      <div class="form-item">
        <label for="username">用户名</label>
        <input
          id="username"
          v-model="username"
          type="text"
          placeholder="请输入用户名"
        />
      </div>
      <div class="form-item">
        <label for="password">密码</label>
        <input
          id="password"
          v-model="password"
          type="password"
          placeholder="请输入密码"
        />
      </div>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      <button type="submit">登录</button> 

      <div style="text-align: right; margin-top: 16px;">
        <Dropdown placement="bottom">
          <span style="color: green;">THIRD LOGIN</span>
          <DownOutlined />
          <template #overlay>
            <Menu>
              <MenuItem key="1">
                <a href="javascript:void(0)" @click="thirdLogin_keyCloak">
                      KEY CLOAK
                </a>
              </MenuItem>
              <MenuItem key="2">
                <a href="javascript:void(0)" @click="twsny_odc">
                      TWSNY ODC
                </a>
              </MenuItem>
              <MenuItem key="3">
                <a href="javascript:void(0)" @click="console.log('WE CHAT')">
                      WE CHAT
                </a>
              </MenuItem>
              <MenuItem key="4">
                <a href="javascript:void(0)" @click="console.log('ALI PAY')">
                      ALI PAY
                </a>
              </MenuItem>
            </Menu>
          </template>
        </Dropdown>
      </div>
      
    </form>
  </div>
</template>



<style scoped>
.login-container {
  width: 300px;
  /* margin: 100px auto; */
  padding: 32px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.login-container h2 {
  text-align: center;
  margin-bottom: 24px;
}

.form-item {
  margin-bottom: 16px;
}

.form-item label {
  display: block;
  margin-bottom: 4px;
}

.form-item input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.error {
  color: red;
  margin-bottom: 16px;
  text-align: center;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #369870;
}
</style>
