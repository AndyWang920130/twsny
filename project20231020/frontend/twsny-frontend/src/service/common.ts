import {request} from "../request/request";

export const uploadFile = (file: File) => {
    const formData = new FormData()
    formData.append('file', file)
    const url = '/upload/common'
    return request({
        url: url,
        method: 'post',
        data: formData
    })
};