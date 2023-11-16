import axios from 'axios'

// let process = {
//     env: {VUE_APP_BASE_API : "http://localhost:8080/api/v1"}
// }

let process = {
    env: {
        VUE_APP_TEST_BASE_API : "http://211.149.248.84:8081/api/v1",
        VUE_APP_DEV_BASE_API : "http://localhost:8080/api/v1",
    }

}

const instance = axios.create({
    // baseURL: "http://211.149.248.84:8081",
    baseURL: process.env.VUE_APP_DEV_BASE_API,
    // baseURL: process.env.VUE_APP_TEST_BASE_API,
    timeout: 3500,  //响应时间
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
        Promise.reject(err).then(r => console.log(r))
    }
)
//响应拦截器
instance.interceptors.response.use(
    response => {
        return response
    }, err => {
        // @ts-ignore
        Promise.reject(err).then(r => console.log(r))
    }
)

export default instance;