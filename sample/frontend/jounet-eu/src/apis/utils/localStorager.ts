import { LoginConfig } from "@/types/CommonD"
import { ref } from "vue"

export const isLoggedIn = ref(!!getWithExpiry(LoginConfig.localStorageKey))

export const loginToken = ref(getWithExpiry(LoginConfig.localStorageKey))

export function updateLoginStatus() {
  loginToken.value = getWithExpiry(LoginConfig.localStorageKey);
  isLoggedIn.value = !!getWithExpiry(LoginConfig.localStorageKey);;
  console.log("loginToken: " +  loginToken.value)
  console.log("isLoggedIn: " +  isLoggedIn.value)
}

export function setWithExpiry(key : string, value : string, ttl : number) {
  const now = Date.now()

  const item = {
    value: value,
    expiry: now + ttl, // ttl是毫秒，比如1小时: 1000 * 60 * 60
  }

  localStorage.setItem(key, JSON.stringify(item))
}

export function getWithExpiry(key : string) {
  const itemStr = localStorage.getItem(key)
  if (!itemStr) {
    return null
  }
  try {
    const item = JSON.parse(itemStr)
    const now = Date.now()

    if (now > item.expiry) {
      // 已过期，移除
      localStorage.removeItem(key)
      return null
    }
    return item.value
  } catch(e) {
    return null
  }
}

export function remove(key : string) {
const itemStr = localStorage.getItem(key)
  if (!itemStr) return

  localStorage.removeItem(key)
}