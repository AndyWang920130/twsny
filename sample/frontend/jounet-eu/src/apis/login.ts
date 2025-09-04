import type { LoginFormT } from "./UserD";

// 模拟登录逻辑
export function authenticate(loginVm : LoginFormT) {
    const username = loginVm.username
    const password = loginVm.password

    // 模拟登录逻辑
    if (username === 'admin' && password === '123456') return true;
    return false;
}

export function loginWithKeycloak_github() {
  window.location.href = "http://localhost:8081/oauth2/authorization/keycloak-github-auth";
}

export function loginWithKeycloak() {
  window.location.href = "http://localhost:8081/oauth2/authorization/keycloak-auth";
}

export function authTwsny() {
  window.location.href = "http://localhost:8081/oauth2/authorization/twsny-auth";
}