import {request} from "../request/request";

interface WebsiteTypeVM {
    itemKey: string
}

export const getWebsiteTypeList = (searchVM: WebsiteTypeVM) => {
    return request({
        url: '/systemConfig/',
        method: 'get',
        param: searchVM
    })
};
