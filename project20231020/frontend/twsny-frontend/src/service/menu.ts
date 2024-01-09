import { request } from '../request/request'
export const getMenuList = () => {
    return request({
        url: '/menu',
        method: 'get'
    })
};

export const getRootMenuList = () => {
    return request({
        url: '/menu/root',
        method: 'get'
    })
};