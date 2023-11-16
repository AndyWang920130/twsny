// @ts-ignore
import { request } from '../request/request'

// export const getWebsites = (id: number) => {
//     const url = api.device + `/${id}/create-code`;
//     return serviceRequest.get(url, {
//         responseType: "blob",
//     });
// };

// export const getWebsites = () => {
//     request({
//         url: '/websites',
//         method: 'get'
//     }).then(response => {
//         console.log(response)
//         console.log(response.data)
//         console.log(response.data.content)
//         return response
//     })
// };


export const getWebsites = () => {
    return request({
        url: '/websites',
        method: 'get'
    })
};

export const addWebsite = (data) => {
    return request({
        url: '/websites',
        method: 'post',
        data: data
    })
};

export const updateWebsite = (data) => {
    return request({
        url: '/websites',
        method: 'put',
        data: data
    })
};


export const deleteWebsite = (paramSuffix) => {
    return request({
        url: '/websites' + paramSuffix,
        method: 'delete',
    })
};

// export const deleteWebsite = (params) => {
//     return request({
//         url: '/websites',
//         method: 'delete',
//         params: params
//     })
// };

// params: {
//     id: 123
// }
// export const addWebsites = (params) => {
//     return request({
//         url: '/websites',
//         method: 'get',
//         data: params
//     })
// };




