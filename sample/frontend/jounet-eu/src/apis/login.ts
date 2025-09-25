import type { LoginFormT } from "./UserD";
import { api } from "./utils/api";

// 模拟登录逻辑
export function authenticate(loginVm : LoginFormT) : Promise<boolean> {
    const username = loginVm.username
    const password = loginVm.password

    // 模拟登录逻辑
    // if (username === 'admin' && password === '123456') return true;
    return api.postForm("http://localhost:9000/login", {}, loginVm)
    .then(res =>  true)
    .catch(err => false)
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

export function authTwsny2() {
  window.location.href = "http://localhost:8081/oauth2/authorization/twsny-auth2";
}

export function oidcGithub() {
  window.location.href = "http://localhost:8081/oauth2/authorization/github";
}


