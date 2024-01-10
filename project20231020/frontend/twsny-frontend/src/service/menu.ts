import { request } from '../request/request'
export const getMenuList = () => {
    return request({
        url: '/menu',
        method: 'get'
    })
};

export const getMenuListByParentId = (parentId : number) => {
    return request({
        url: '/menu/byParent/' + parentId,
        method: 'get'
    })
};


export const getRootMenuList = () => {
    return request({
        url: '/menu/root',
        method: 'get'
    })
};