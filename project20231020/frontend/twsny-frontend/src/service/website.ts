// @ts-ignore
import { request } from '../request/request'

// export const getWebsites = (id: number) => {
//     const url = api.device + `/${id}/create-code`;
//     return serviceRequest.get(url, {
//         responseType: "blob",
//     });
// };

export const getWebsites = () => {
    request({
        url: '/websites',
        method: 'get'
    }).then(response => {
        console.log(response)
        return response
    })
};
