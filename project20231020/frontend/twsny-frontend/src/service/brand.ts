import { request } from '../request/request'
export const getBrandList = () => {
    return request({
        url: '/brands',
        method: 'get'
    })
};