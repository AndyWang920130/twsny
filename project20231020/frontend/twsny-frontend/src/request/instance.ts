import axios from 'axios'

// let process = {
//     env: {VUE_APP_BASE_API : "http://localhost:8080/api/v1"}
// }

// let process = {
//     env: {
//         VUE_APP_TEST_BASE_API : "http://211.149.248.84:8080/api/v1",
//         VUE_APP_DEV_BASE_API : "http://localhost:8080/api/v1",
//     }
//
// }

// @ts-ignore
const apiUrl = import.meta.env.VITE_VUE_APP_API_URL
// @ts-ignore
console.log('import', import.meta)
// @ts-ignore
console.log('import.BASE_URL', import.meta.env.BASE_URL)
// @ts-ignore
console.log('import.env.MODE', import.meta.env.MODE)
// @ts-ignore
console.log('import.env.PROD', import.meta.env.PROD)
// @ts-ignore
console.log('import.env.DEV', import.meta.env.DEV)
// @ts-ignore
console.log("VITE_APP_TITLE:" + import.meta.env.VITE_APP_TITLE)
// @ts-ignore
console.log("VUE_APP_API_URL:" + import.meta.env.VUE_APP_API_URL)
// @ts-ignore
console.log("VITE_VUE_APP_API_URL:" + import.meta.env.VITE_VUE_APP_API_URL)


console.log("apiUrl: " + apiUrl)

const instance = axios.create({
    // baseURL: "http://211.149.248.84:8081",
    // baseURL: process.env.VUE_APP_TEST_BASE_API,
    // baseURL: process.env.VUE_APP_TEST_BASE_API,
    baseURL: apiUrl,
    timeout: 10000,  //响应时间
    headers: {}
})

//请求拦截器
instance.interceptors.request.use(
    config => {
        // 在这里可以设置请求头、请求参数等return config
        console.log("hears:" + config.headers)
        return config
    }, err => {
        // @ts-ignore
        // Promise.reject(err).then(r => console.log(r))
        return Promise.reject(err)
    }
)
//响应拦截器
instance.interceptors.response.use(
    response => {
        return response
    }, err => {
        // @ts-ignore
        // Promise.reject(err).then(r => console.log(r))
        return Promise.reject(err)
    }
)

export default instance;